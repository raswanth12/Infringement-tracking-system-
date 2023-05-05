import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;

public class TeacherDashboard extends JFrame {
    private JList<String> fileList;
    private JButton refreshButton;
    private JButton compareButton;
    private JButton checkAiGeneratedButton;

    public TeacherDashboard() {
        setTitle("Teacher Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshFileList());
        add(refreshButton, BorderLayout.NORTH);

        fileList = new JList<>();
        add(new JScrollPane(fileList), BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        compareButton = new JButton("Compare Files");
        compareButton.addActionListener(e -> compareSelectedFiles());
        buttonPanel.add(compareButton);

        // Add the new "Check AI Generated" button
        checkAiGeneratedButton = new JButton("Check AI Generated");
        checkAiGeneratedButton.addActionListener(e -> checkAiGenerated());
        buttonPanel.add(checkAiGeneratedButton);

        refreshFileList();

        setVisible(true);
    }

    // ... (existing methods)

    private Object compareSelectedFiles() {
        return null;
    }

    private void checkAiGenerated() {
        // Get the list of selected file names
        List<String> selectedFiles = fileList.getSelectedValuesList();

        // Make sure at least 1 file is selected
        if (selectedFiles.size() < 1) {
            JOptionPane.showMessageDialog(this, "Please select at least one file to check for AI-generated content.");
            return;
        }

        // Call the Login.getUploadedFiles() method to retrieve the list of uploaded
        // files from the database
        List<FileRecord> uploadedFiles = (List<FileRecord>) Login.getUploadedFiles();

        // Create a map to hold the contents of the selected files
        Map<String, String> selectedFileContents = new HashMap<>();

        // Add the contents of each selected file to the map
        for (FileRecord file : uploadedFiles) {
            if (selectedFiles.contains(file.getFilename())) {
                selectedFileContents.put((String) file.getFilename(), file.getContent());
            }
        }

        // Implement the logic for checking if the text is AI-generated
        // For example, you can use an external library, API or develop your own
        // algorithm to detect AI-generated text

        // Build a message with the AI-generated check results
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("AI-Generated Text Check Results:\n\n");

        for (String file : selectedFiles) {
            // Check if the text is AI-generated
            // Replace the line below with your implementation
            boolean isAiGenerated = false; // Dummy value

            resultMessage.append(String.format("%s: %s\n", file, isAiGenerated ? "AI-generated" : "Not AI-generated"));
        }

        // Show the AI-generated text check results in a dialog box
        JOptionPane.showMessageDialog(this, resultMessage.toString());
    }
}
