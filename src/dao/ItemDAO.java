package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                    rs.getTimestamp("auction_start_time").toLocalDateTime(),
                    rs.getTimestamp("auction_end_time").toLocalDateTime(),
                    rs.getInt("seller_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addItem(Item item) {
        String query = "INSERT INTO items (item_name, description, start_price, reserve_price, auction_start_time, auction_end_time, seller_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getStartPrice());
            stmt.setDouble(4, item.getReservePrice());
            stmt.setTimestamp(5, Timestamp.valueOf(item.getAuctionStartTime()));
            stmt.setTimestamp(6, Timestamp.valueOf(item.getAuctionEndTime()));
            stmt.setInt(7, item.getSellerId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getItemsBySellerId(int sellerId) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE seller_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("description"),
                    rs.getDouble("start_price"),
                    rs.getDouble("reserve_price"),
                    rs.getTimestamp("auction_start_time").toLocalDateTime(),
                    rs.getTimestamp("auction_end_time").toLocalDateTime(),
                    rs.getInt("seller_id")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<Item> getActiveAuctions() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE auction_start_time <= ? AND auction_end_time >= ?";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, now);
            stmt.setTimestamp(2, now);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("description"),
                    rs.getDouble("start_price"),
                    rs.getDouble("reserve_price"),
                    rs.getTimestamp("auction_start_time").toLocalDateTime(),
                    rs.getTimestamp("auction_end_time").toLocalDateTime(),
                    rs.getInt("seller_id")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
