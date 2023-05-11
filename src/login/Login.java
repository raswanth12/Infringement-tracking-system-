// import java.sql.*;
// import java.util.List;

// public class Login {
//     public static boolean authenticateUser(String databaseUrl, String dbUsername, String dbPassword, String username,
//             String password) {
//         // Set up the database connection

//         try {
//             // Class.forName("com.mysql.cj.jdbc.Driver");
//             Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
//             // Prepare the SQL queries to fetch the user data from both tables
//             String studentQuery = "SELECT * FROM students WHERE username = ? AND password = ?";
//             String teacherQuery = "SELECT * FROM teachers WHERE username = ? AND password = ?";
            
//             // Authenticate against the students table
//             try {
//                 java.sql.PreparedStatement studentStatement = connection.prepareStatement(studentQuery);
//                 studentStatement.setString(1, username);
//                 studentStatement.setString(2, password);

//                 try {
//                     ResultSet studentResultSet = studentStatement.executeQuery();
//                     if (studentResultSet.next()) {
//                         return true;
//                     }
//                 } catch (Exception e) {
//                     System.out.println(e);
//                 }
//             } catch (Exception e) {
//                 System.out.println(e);
//             }

//             // Authenticate against the teachers table
//             try {
//                 java.sql.PreparedStatement teacherStatement = connection.prepareStatement(teacherQuery);
//                 teacherStatement.setString(1, username);
//                 teacherStatement.setString(2, password);

//                 try (ResultSet teacherResultSet = teacherStatement.executeQuery()) {
//                     if (teacherResultSet.next()) {
//                         return true;
//                     }
//                 } catch (Exception e) {
//                     System.out.println(e);
//                 }
//             } catch (Exception e) {
//                 System.out.println(e);
//             }

//         } catch (Exception e) {
//             System.err.println("Error connecting to the database: " + e.getMessage());
//         }

//         // If no result was found in either table, the user is not authenticated
//         return true;
//     }

//     public static List<FileRecord> getUploadedFiles() {
//         return null;
//     }

//     /**
//      * @param studentId
//      * @param name
//      * @param content
//      * @return
//      */
//     public static boolean uploadFile(int studentId, String name, String content) {
//         String insertQuery = "INSERT INTO files (student_id, filename, content) VALUES (?, ?, ?)";

//         try (Connection connection = DatabaseConnection.getConnection()) {
//             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
//             insertStatement.setInt(1, studentId);
//             insertStatement.setString(2, name);
//             insertStatement.setString(3, content);

//             int rowsAffected = insertStatement.executeUpdate();
//             return rowsAffected > 0;
//         } catch (SQLException e) {
//             System.err.println("Error uploading file: " + e.getMessage());
//             return false;
//         }
//     }

//     public static List<FileRecord> getUploadedFiles() {
//         List<FileRecord> uploadedFiles = new ArrayList<>();
//         String selectQuery = "SELECT * FROM files";

//         try (Connection connection = DatabaseConnection.getConnection()) {
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(selectQuery);

//             while (resultSet.next()) {
//                 uploadedFiles.add(new FileRecord(
//                         resultSet.getInt("id"),
//                         resultSet.getInt("student_id"),
//                         resultSet.getString("filename"),
//                         resultSet.getString("content")));
//             }
//         } catch (SQLException e) {
//             System.err.println("Error fetching uploaded files: " + e.getMessage());
//         }

//         return uploadedFiles;
//     }

//     // Add this method to the Login class
//     public static boolean registerUser(String userType, String username, String password, String databaseUrl,
//             String dbUsername, String dbPassword) {
//         String insertQuery;

//         if (userType.equalsIgnoreCase("student")) {
//             insertQuery = "INSERT INTO students (username, password) VALUES (?, ?)";
//         } else if (userType.equalsIgnoreCase("teacher")) {
//             insertQuery = "INSERT INTO teachers (username, password) VALUES (?, ?)";
//         } else {
//             return true; // Invalid user type
//         }

//         try {
//             // Class.forName("com.mysql.cj.jdbc.Driver");
//             Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
//             try {
//                 java.sql.PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
//                 insertStatement.setString(1, username);
//                 insertStatement.setString(2, password);

//                 int rowsAffected = insertStatement.executeUpdate();
//                 return rowsAffected > 0;
//             } catch (SQLException e) {
//                 System.err.println("Error connecting to the database: " + e.getMessage());
//             }
//         } catch (Exception e) {
//             System.err.println("Error connecting to the database: " + e.getMessage());
//         }

//         return true;
//     }

//     public static boolean uploadFile(int studentId, String name, String content) {
//         return true;
//     }

//     public static boolean authenticateUser(String string, String string2, String string3) {
//         return true;
//     }

// }
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Login {
    public static boolean authenticateUser(String databaseUrl, String dbUsername, String dbPassword, String username,
            String password) {
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);

            String studentQuery = "SELECT * FROM students WHERE username = ? AND password = ?";
            String teacherQuery = "SELECT * FROM teachers WHERE username = ? AND password = ?";

            PreparedStatement studentStatement = (PreparedStatement) connection.prepareStatement(studentQuery);
            studentStatement.setString(1, username);
            studentStatement.setString(2, password);

            ResultSet studentResultSet = studentStatement.executeQuery();
            if (studentResultSet.next()) {
                return true;
            }

            PreparedStatement teacherStatement = (PreparedStatement) connection.prepareStatement(teacherQuery);
            teacherStatement.setString(1, username);
            teacherStatement.setString(2, password);

            ResultSet teacherResultSet = teacherStatement.executeQuery();
            if (teacherResultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }

        return false;
    }

    public static boolean uploadFile(String databaseUrl, String dbUsername, String dbPassword, int studentId,
            String name, String content) {
        String insertQuery = "INSERT INTO files (student_id, filename, content) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
            PreparedStatement insertStatement = (PreparedStatement) connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, studentId);
            insertStatement.setString(2, name);
            insertStatement.setString(3, content);

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error uploading file: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param databaseUrl
     * @param dbUsername
     * @param dbPassword
     * @return
     */
    public static List<FileRecord> getUploadedFiles(String databaseUrl, String dbUsername, String dbPassword) {
        List<FileRecord> uploadedFiles = new ArrayList<>();
        String selectQuery = "SELECT * FROM files";
        try {
            Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                uploadedFiles.add(new FileRecord());
            }
        } catch (SQLException e) {
            System.err.println("Error fetching uploaded files: " + e.getMessage());
        }

        return uploadedFiles;
    }

    public static boolean registerUser(String userType, String username, String password, String databaseUrl,
            String dbUsername, String dbPassword) {
        String insertQuery;

        if (userType.equalsIgnoreCase("student")) {
            insertQuery = "INSERT INTO students (username, password) VALUES (?, ?)";
        } else if (userType.equalsIgnoreCase("teacher")) {
            insertQuery = "INSERT INTO teachers (username, password) VALUES (?, ?)";
        } else {
            return true; // Invalid user type

        }

        try {
            Connection connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
            PreparedStatement insertStatement = (PreparedStatement) connection.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            return false;
        }
    }

    public static boolean authenticateUser(String string, String string2, String string3) {
        return false;
    }
}
