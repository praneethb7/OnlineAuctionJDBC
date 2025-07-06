package dao;

import java.sql.*;
import utils.DBUtil;

public class UserDAO {
    public void printAllUsers(){
        String query = "SELECT * FROM users";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    System.out.println("User: "+rs.getString("email"));
                }
            } catch(SQLException e){}
    }
}
