package dao;

import java.sql.*;
import model.AuditLog;
import utils.DBUtil;

public class AuditLogDAO {

    public void logAction(AuditLog log) {
        String query = """
            INSERT INTO audit_logs (user_id, item_id, action_type, action_title, details, ip_address, user_agent, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, log.getUserId());
            if (log.getItemId() != null) {
                stmt.setInt(2, log.getItemId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, log.getActionType());
            stmt.setString(4, log.getActionTitle());
            stmt.setString(5, log.getDetailsJson());
            stmt.setString(6, log.getIpAddress());
            stmt.setString(7, log.getUserAgent());
            stmt.setTimestamp(8, Timestamp.valueOf(log.getCreatedAt()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
