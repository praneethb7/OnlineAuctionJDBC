package dao;

import java.sql.*;
import utils.DBUtil;

public class PaymentDAO {

    public boolean markPaymentCompleted(int itemId) {
        String query = "UPDATE winning_bids SET payment_status = 'paid' WHERE item_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPaymentStatus(int itemId) {
        String query = "SELECT payment_status FROM winning_bids WHERE item_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("payment_status");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "unknown";
    }
}
