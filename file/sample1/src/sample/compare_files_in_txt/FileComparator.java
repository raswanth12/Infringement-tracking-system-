package file.sample1.src.sample.compare_files_in_txt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileComparator {
    public static void main(String[] args) {
        List<String> fileContents = new ArrayList<>();
        Map<String, String> fileMap = new HashMap<>();

        // Assuming you have a list of uploaded file paths in 'uploadedFilePaths'
        String[] uploadedFilePaths = { "compare_files_in_txt\\New folder"};/* Add file paths here */
        for (String filePath : uploadedFilePaths) {
            String fileContent = readFileContent(filePath);
            fileContents.add(fileContent);
            fileMap.put(filePath, fileContent);
        }

        // Compare the files for similarities
        // You can use algorithms like Levenshtein Distance, fingerprinting, etc.
        // to compare the file contents and determine if they are copied or not

        // Generate report and save it to a .txt file
        String reportContent = generateReport(fileMap);
        saveReportToFile(reportContent, "report.txt");

        // Save .txt files to the MySQL database
        saveFilesToDatabase(fileContents);
    }

    private static String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static String generateReport(Map<String, String> fileMap) {
        // Generate the report content based on the file map
        StringBuilder report = new StringBuilder();
        // Iterate over the file map and check for copied content
        for (Map.Entry<String, String> entry : fileMap.entrySet()) {
            String filePath = entry.getKey();
            String fileContent = entry.getValue();
            // Compare 'fileContent' with other file contents and determine if it is copied
            // Append the details to the report StringBuilder accordingly
        }
        return report.toString();
    }

    private static void saveReportToFile(String reportContent, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(reportContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFilesToDatabase(List<String> fileContents) {
        String dbUrl = "jdbc:mysql://localhost/dbname";
        String username = "username";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            for (String fileContent : fileContents) {
                String sql = "INSERT INTO files (file_content) VALUES (?)";
                PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
                statement.setString(1, fileContent);
                statement.executeUpdate();
            }
            System.out.println("Files saved to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
