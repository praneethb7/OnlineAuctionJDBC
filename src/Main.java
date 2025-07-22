import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import dao.*;
import model.*;
import utils.DBUtil;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        ItemDAO itemDAO = new ItemDAO();
        BidDAO bidDAO = new BidDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        WinningBidDAO winningBidDAO = new WinningBidDAO();

        while (true) {
            System.out.println("\n--- Auction System ---");
            System.out.println("1. Register User");
            System.out.println("2. Add Auction Item");
            System.out.println("3. View Items by Seller");
            System.out.println("4. Place a Bid");
            System.out.println("5. Mark Winning Bid");
            System.out.println("6. Mark Payment Complete");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("First name: ");
                    String first = sc.nextLine();
                    System.out.print("Last name: ");
                    String last = sc.nextLine();

                    User user = new User(0, email, phone, first, last);
                    boolean registered = userDAO.registerUser(user);
                    System.out.println(registered ? "User registered." : "Failed to register.");
                }

                case 2 -> {
                    System.out.print("Item Name: ");
                    String name = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Start Price: ");
                    double start = sc.nextDouble();
                    System.out.print("Reserve Price: ");
                    double reserve = sc.nextDouble();
                    System.out.print("Seller ID: ");
                    int sellerId = sc.nextInt();
                    sc.nextLine();
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime end = now.plusMinutes(5);

                    Item item = new Item(0, name, desc, start, reserve, now, end, sellerId);
                    itemDAO.addItem(item);
                    System.out.println("Item listed.");
                }

                case 3 -> {
                    System.out.print("Seller ID: ");
                    int sellerId = sc.nextInt();
                    List<Item> items = itemDAO.getItemsBySellerId(sellerId);
                    for (Item i : items) {
                        System.out.println(i.getItemId() + ": " + i.getItemName() + " (₹" + i.getStartPrice() + ")");
                    }
                }

                case 4 -> {
                    System.out.print("Item ID: ");
                    int itemId = sc.nextInt();
                    System.out.print("Bidder ID: ");
                    int bidderId = sc.nextInt();
                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();
                    LocalDateTime now = LocalDateTime.now();

                    Bid bid = new Bid(0, itemId, bidderId, amount, now);
                    boolean placed = bidDAO.placeBid(bid);
                    System.out.println(placed ? "Bid placed." : "Failed to place bid.");
                }

                case 5 -> {
                    System.out.print("Item ID to close: ");
                    int itemId = sc.nextInt();
                    closeAuctionAndDeclareWinner(itemId);
                }

                case 6 -> {
                    System.out.print("Item ID to mark paid: ");
                    int itemId = sc.nextInt();
                    boolean paid = paymentDAO.markPaymentCompleted(itemId);
                    System.out.println(paid ? "Payment marked as completed." : "Failed to mark payment.");
                }

                case 7 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void closeAuctionAndDeclareWinner(int itemId) {
        String query = """
            SELECT b.bid_id, b.amount
            FROM bids b
            JOIN items i ON b.item_id = i.item_id
            WHERE i.item_id = ? AND b.amount >= i.reserve_price
            ORDER BY b.amount DESC, b.timestamp ASC
            LIMIT 1
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int bidId = rs.getInt("bid_id");

                WinningBid win = new WinningBid(
                    itemId, bidId,
                    5.0, 10.0,
                    "pending", LocalDateTime.now()
                );

                new WinningBidDAO().insertWinningBid(win);
                System.out.println("Winner saved to winning_bids for item: " + itemId);
            } else {
                System.out.println("No bid met reserve price for item: " + itemId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
