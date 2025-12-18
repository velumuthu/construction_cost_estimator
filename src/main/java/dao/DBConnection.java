package dao;

import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String URL = "jdbc:mysql://localhost:3306/construction_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String USER = "root";
    private static String PASSWORD = ""; // XAMPP default has no password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    // Load DB config from resources/db.properties if available
    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                URL = props.getProperty("db.url", URL);
                USER = props.getProperty("db.user", USER);
                PASSWORD = props.getProperty("db.password", PASSWORD);
            }
        } catch (Exception ex) {
            System.out.println("Could not load db.properties, using defaults.");
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database Connected Successfully!");
        } catch (SQLException e) {
            System.out.println("❌ DB Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}
