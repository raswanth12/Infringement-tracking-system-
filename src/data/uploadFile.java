package src.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class uploadFile{
public static boolean uploadFile(int studentId, String filename, String content) {
    String query = "INSERT INTO uploaded_files (student_id, filename, content) VALUES (?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, studentId);
        statement.setString(2, filename);
        statement.setString(3, content);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}}
