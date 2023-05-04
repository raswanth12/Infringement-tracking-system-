package file.sample1.src.sample.data;

import java.sql.*;

public class Main {
    // public static void main(String[] args) {
    //     try {
    //         String url = "jdbc:mysql://localhost/dbname";
    //         String user = "java_gui";
    //         String password = "omen0405";
    //         Connection conn = DriverManager.getConnection(url, user, password);
    //         System.out.println("Connected to database!");
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Error connecting to the database", e);
    //     }
    //     Statement stmt = conn.createStatement();
    //     ResultSet rs = stmt.executeQuery("SELECT * FROM users");
    //     while (rs.next()) {
    //         int id = rs.getInt("id");
    //         String username = rs.getString("username");
    //         String password = rs.getString("password");
    //         System.out.println(id + ", " + username + ", " + password);
    //     }
    // }
    // }


    public static void main(String[] args) {
    try {
        String url = "jdbc:mysql://localhost/dbname";
        String user = "java_gui";
        String password = "omen0405";
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to database!");
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("raswanth");
            String passwordValue = rs.getString("raswanth12");
            System.out.println(id + ", " + username + ", " + passwordValue);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error connecting to the database", e);
    }
}}


