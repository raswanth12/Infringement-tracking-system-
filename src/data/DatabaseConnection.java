
package src.data;

import java.sql.*;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school"; 
                                                                                
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "omen0405"; 

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }

        return connection;
    }
}
