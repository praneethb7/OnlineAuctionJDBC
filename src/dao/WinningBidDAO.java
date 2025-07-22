package dao;

import java.sql.*;
import model.WinningBid;
import utils.DBUtil;

public class WinningBidDAO {

    public boolean insertWinningBid(WinningBid win) {
        String query = """
            INSERT INTO winning_bids (item_id, bid_id, conversion_fee, transaction_fee, payment_status, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, win.getItemId());
            stmt.setInt(2, win.getBidId());
            stmt.setDouble(3, win.getConversionFee());
            stmt.setDouble(4, win.getTransactionFee());
            stmt.setString(5, win.getPaymentStatus());
            stmt.setTimestamp(6, Timestamp.valueOf(win.getCreatedAt()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getWinningBidId(int itemId) {
        String query = "SELECT bid_id FROM winning_bids WHERE item_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("bid_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
