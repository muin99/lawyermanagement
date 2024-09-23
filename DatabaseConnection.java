import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lawyermanagement", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
