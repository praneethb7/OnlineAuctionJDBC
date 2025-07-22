package dao;

import java.sql.*;
import model.Item;
import utils.DBUtil;

public class ItemDAO {
    public Item getItemById(int itemId) {
        String query = "SELECT * FROM items WHERE item_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Item(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getDouble("start_price"),
                        rs.getDouble("reserve_price"),
                        rs.getInt("seller_id"),
                        rs.getTimestamp("auction_start_time").toLocalDateTime(),
                        rs.getTimestamp("auction_end_time").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Optional: helpful for debugging
        }
        return null;
    }

    public void addItem(Item item) {
        String query = "INSERT INTO items (item_name, description, start_price, reserve_price, seller_id, auction_start_time, auction_end_time) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getStartPrice());
            stmt.setDouble(4, item.getReservePrice());
            stmt.setInt(5, item.getSellerId());
            stmt.setTimestamp(6, Timestamp.valueOf(item.getAuctionStartTime()));
            stmt.setTimestamp(7, Timestamp.valueOf(item.getAuctionEndTime()));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Item added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
