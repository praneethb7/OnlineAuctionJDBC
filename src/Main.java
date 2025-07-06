import dao.UserDAO;

public class Main {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        dao.printAllUsers();
    }
}
