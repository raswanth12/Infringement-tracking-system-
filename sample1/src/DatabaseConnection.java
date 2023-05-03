package sample1.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school?useSSL=false";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "omen0405";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static boolean authenticateUser(String username, String password, String userType) {
        String query = String.format("SELECT * FROM %s WHERE username=? AND password=?", userType.toLowerCase());

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveFile(String studentUsername, String fileName, String content) {
        String query = "INSERT INTO files (student_id, filename, content) SELECT id, ?, ? FROM students WHERE username=?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, fileName);
            statement.setString(2, content);
            statement.setString(3, studentUsername);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
