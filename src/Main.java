import dao.ItemDAO;
import dao.UserDAO;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        userDAO.printAllUsers();

        ItemDAO itemDAO = new ItemDAO();
        
    }
}
