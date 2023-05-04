package Project.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {

    public static void someMethod() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                String query = "SELECT * FROM some_table";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Process the result set
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        someMethod();
    }
}
