package dao;

import java.sql.*;
import java.time.LocalDateTime;
import model.Bid;
import utils.DBUtil;

public class BidDAO {

    public boolean placeBid(Bid bid) {
        // Ensure bid is greater than current highest and auction is open
        
        if (!isAuctionActive(bid.getItemId())) {
            System.out.println("Auction is not active.");
            return false;
        }

        double currentMax = getHighestBidAmount(bid.getItemId());

        if (bid.getAmount() <= currentMax) {
            System.out.println("Bid must be higher than current highest bid: ₹" + currentMax);
            return false;
        }

        String query = "INSERT INTO bids (item_id, bidder_id, amount, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bid.getItemId());
            stmt.setInt(2, bid.getBidderId());
            stmt.setDouble(3, bid.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(bid.getBidTime()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getHighestBidAmount(int itemId) {
        String query = "SELECT MAX(amount) AS max_amount FROM bids WHERE item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("max_amount");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean isAuctionActive(int itemId) {
        String query = "SELECT auction_start_time, auction_end_time FROM items WHERE item_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp start = rs.getTimestamp("auction_start_time");
                Timestamp end = rs.getTimestamp("auction_end_time");
                Timestamp now = Timestamp.valueOf(LocalDateTime.now());

                return now.after(start) && now.before(end);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
