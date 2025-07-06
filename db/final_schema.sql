CREATE DATABASE auction_db;
USE auction_db;

-- -------------------------
-- 1. Users Table
-- -------------------------
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    kyc_status ENUM('pending', 'verified', 'rejected') DEFAULT 'pending',
    kyc_verified_at DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------
-- 2. Wallets Table
-- -------------------------
CREATE TABLE wallets (
    wallet_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    balance DECIMAL(12,2) DEFAULT 0.00,
    status ENUM('active', 'blocked', 'suspended') DEFAULT 'active',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- -------------------------
-- 3. Bank Accounts Table
-- -------------------------
CREATE TABLE bank_accounts (
    bank_account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    account_number VARCHAR(30) NOT NULL,
    ifsc_code VARCHAR(15),
    bank_name VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- -------------------------
-- 4. Auctions Table
-- -------------------------
CREATE TABLE auctions (
    auction_id INT AUTO_INCREMENT PRIMARY KEY,
    auction_name VARCHAR(255),
    start_time DATETIME,
    end_time DATETIME,
    status ENUM('scheduled', 'ongoing', 'completed') DEFAULT 'scheduled',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_active_auctions (end_time, status)  -- ✅ To quickly find auctions closing soon and still active
);

-- -------------------------
-- 5. Items Table
-- -------------------------
CREATE TABLE items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255),
    description TEXT,
    start_price DECIMAL(12,2),
    reserve_price DECIMAL(12,2),
    seller_id INT,
    auction_id INT,
    status ENUM('unsold', 'sold') DEFAULT 'unsold',
    listing_fee DECIMAL(12,2),
    res_hide_fee DECIMAL(12,2),
    min_bid_increment DECIMAL(12,2) DEFAULT 1.00,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(user_id),
    FOREIGN KEY (auction_id) REFERENCES auctions(auction_id)
);

-- -------------------------
-- 6. Bids Table
-- -------------------------
CREATE TABLE bids (
    bid_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    bidder_id INT,
    amount DECIMAL(12,2),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (bidder_id) REFERENCES users(user_id),
    INDEX idx_highest_bid_per_item (item_id, timestamp DESC), -- ✅ For quickly fetching current highest bid
    INDEX idx_user_bids (bidder_id, timestamp)              -- ✅ For user’s total bids in a period
);

-- -------------------------
-- 7. Winning Bids Table
-- -------------------------
CREATE TABLE winning_bids (
    win_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT UNIQUE,
    bid_id INT,
    conversion_fee DECIMAL(12,2),
    transaction_fee DECIMAL(12,2),
    payment_status ENUM('pending', 'paid', 'failed') DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (bid_id) REFERENCES bids(bid_id),
    INDEX idx_winner_auction (item_id)  -- ✅ For querying winners per item/auction
);

-- -------------------------
-- 8. Wallet Transactions
-- -------------------------
CREATE TABLE wallet_transactions (
    wallet_transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    from_wallet_id INT,
    to_wallet_id INT,
    amount DECIMAL(12,2),
    transaction_type ENUM('charges', 'transfer', 'refund') NOT NULL,
    transaction_status ENUM('pending', 'completed', 'failed') DEFAULT 'completed',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_wallet_id) REFERENCES wallets(wallet_id),
    FOREIGN KEY (to_wallet_id) REFERENCES wallets(wallet_id)
);

-- -------------------------
-- 9. Account Transactions
-- -------------------------
CREATE TABLE account_transactions (
    account_transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_id INT,
    bank_account_id INT,
    amount DECIMAL(12,2),
    transaction_type ENUM('withdrawal', 'deposit') NOT NULL,
    transaction_status ENUM('pending', 'completed', 'failed') DEFAULT 'completed',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wallet_id) REFERENCES wallets(wallet_id),
    FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(bank_account_id)
);

-- -------------------------
-- 10. Wallet Transaction Breakdown
-- -------------------------
CREATE TABLE wallet_transaction_breakdowns (
    breakdown_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_transaction_id INT,
    amount DECIMAL(12,2),
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wallet_transaction_id) REFERENCES wallet_transactions(wallet_transaction_id)
);

-- -------------------------
-- 11. Audit Log Table
-- -------------------------
CREATE TABLE audit_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    
    user_id INT NOT NULL,
    item_id INT,                          -- Optional: For actions related to an item
    auction_id INT,                       -- Optional: For actions related to an auction
    
    action_type ENUM(
        'login', 'logout',
        'place_bid', 'update_bid', 'cancel_bid',
        'add_item', 'update_item', 'delete_item',
        'start_auction', 'end_auction',
        'kyc_upload', 'kyc_verification',
        'wallet_transaction', 'account_transaction',
        'error', 'system_event'
    ) NOT NULL,
    
    action_title VARCHAR(255) NOT NULL,   -- A short label like "Placed Bid", "KYC Rejected"
    details JSON,                         -- Flexible structure for extra metadata
    
    ip_address VARCHAR(45),              
    user_agent TEXT,                      -- Browser / device metadata
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    FOREIGN KEY (auction_id) REFERENCES auctions(auction_id)
);
-- -------------------------
-- 12. Auto-Bid Table
-- -------------------------
CREATE TABLE auto_bids (
    auto_bid_id INT AUTO_INCREMENT PRIMARY KEY,
    bidder_id INT NOT NULL,
    item_id INT NOT NULL,
    max_amount DECIMAL(12,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bidder_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    UNIQUE (bidder_id, item_id) -- only one auto-bid per item per user
);
