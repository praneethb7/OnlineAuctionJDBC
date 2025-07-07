package dao;

import java.sql.*;
import model.Item;
import utils.DBUtil;

public class ItemDAO {
    public Item getItemById(int itemId){
        String query = "SELECT * FROM items WHERE item_id = ?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

               stmt.setInt(1, itemId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Item(
                rs.getInt("itemId"),
                rs.getString("item_name"),
                rs.getString("description"),
                rs.getDouble("start_price"),
                rs.getDouble("reserve_price"),
                rs.getInt("seller_id")
            );
                }
            } catch(SQLException e){}
        return null;
    }
}
