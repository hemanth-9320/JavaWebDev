package in.sp.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // Change this to your database URL
    private static final String USER = "system"; // Your database username
    private static final String PASSWORD = "sys"; // Your database password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Load Oracle JDBC Driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Failed to load Oracle JDBC driver.");
        }
    }
}
