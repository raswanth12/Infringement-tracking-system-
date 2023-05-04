package file.sample1.src.sample;
import java.sql.*;

public class Login {

    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        // create connection to MySQL database
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // create statement for querying the database
            try (Statement stmt = conn.createStatement()) {
                // create tables if they don't exist
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS students (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL)");
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS teachers (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL)");
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS admins (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL)");

                // create admin account if it doesn't exist
                ResultSet rs = stmt.executeQuery("SELECT * FROM admins");
                if (!rs.next()) {
                    stmt.executeUpdate("INSERT INTO admins (username, password) VALUES ('admin', 'admin')");
                }

                // show login or sign up page
                showLoginPage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showLoginPage() {
        // show login page UI
        // Get the entered username and password
        String username = "admin"; // example value
        String password = "admin"; // example value

        // Validate the login credentials
        String role = validateLogin(username, password);

        if (role != null) {
            System.out.println("Login successful! Role: " + role);
            // Proceed with the respective role's functionality
        } else {
            System.out.println("Invalid username or password");
            // Show an error message or perform other actions for invalid login
        }
    }

    private static String validateLogin(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String role = null;

            // Check if the user exists in the students table
            String query = "SELECT * FROM students WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    role = "student";
                }
            }

            if (role == null) {
                // Check if the user exists in the teachers table
                query = "SELECT * FROM teachers WHERE username = ? AND password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        role = "teacher";
                    }
                }
            }

            if (role == null) {
                // Check if the user exists in the admins table
                query = "SELECT * FROM admins WHERE username = ? AND password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        role = "admin";
                    }
                }
            }

            return role;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void showSignUpPage(String role) {
        // show sign up page UI for the specified role
        // Get the entered username and password
        String username = "new_user"; // example value
        String password = "new_password"; // example value

        // Create a new user account
        signUp(username, password, role);
    }

    private static void login(String username, String password) {
        // validate user credentials and log in
        String role = validateLogin(username, password);

        if (role != null) {
            System.out.println("Login successful! Role: " + role);
            // Proceed with the respective role's functionality
        } else {
            System.out.println("Invalid username or password");
            // Show an error message or perform other actions for invalid login
        }
    }

    private static void signUp(String username, String password, String role) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String tableName;
            switch (role) {
                case "student":
                    tableName = "students";
                    break;
                case "teacher":
                    tableName = "teachers";
                    break;
                case "admin":
                    tableName = "admins";
                    break;
                default:
                    return;
            }

            // Check if the username is already taken
            String query = "SELECT * FROM " + tableName + " WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Username already exists. Please choose a different username.");
                    return;
                }
            }

            // Insert the new user account into the respective table
            query = "INSERT INTO " + tableName + " (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
            }

            System.out.println("User account created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
