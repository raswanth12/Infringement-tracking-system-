    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
import java.util.List;

    public class Login {
public static boolean authenticateUser(String databaseUrl, String dbUsername, String dbPassword, String username, String password) {
    // Set up the database connection
    try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
        // Prepare the SQL queries to fetch the user data from both tables
        String studentQuery = "SELECT * FROM students WHERE username = ? AND password = ?";
        String teacherQuery = "SELECT * FROM teachers WHERE username = ? AND password = ?";
        
        // Authenticate against the students table
        try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
            studentStatement.setString(1, username);
            studentStatement.setString(2, password);

            try (ResultSet studentResultSet = studentStatement.executeQuery()) {
                if (studentResultSet.next()) {
                    return true;
                }
            }
        }

        // Authenticate against the teachers table
        try (PreparedStatement teacherStatement = connection.prepareStatement(teacherQuery)) {
            teacherStatement.setString(1, username);
            teacherStatement.setString(2, password);

            try (ResultSet teacherResultSet = teacherStatement.executeQuery()) {
                if (teacherResultSet.next()) {
                    return true;
                }
            }
        }

    } catch (SQLException e) {
        System.err.println("Error connecting to the database: " + e.getMessage());
    }

    // If no result was found in either table, the user is not authenticated
    return false;
}

public static List<FileRecord> getUploadedFiles() {
    return null;
}

public static boolean uploadFile(String name, String content) {
    return false;
}

// Add this method to the Login class
public static boolean registerUser(String userType, String username, String password, String databaseUrl,
        String dbUsername, String dbPassword)
         {
    String insertQuery;

    if (userType.equalsIgnoreCase("student")) {
        insertQuery = "INSERT INTO students (username, password) VALUES (?, ?)";
    } else if (userType.equalsIgnoreCase("teacher")) {
        insertQuery = "INSERT INTO teachers (username, password) VALUES (?, ?)";
    } else {
        return false; // Invalid user type
    }

    try (Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword)) {
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        System.err.println("Error connecting to the database: " + e.getMessage());
    }

    return false;
}

}