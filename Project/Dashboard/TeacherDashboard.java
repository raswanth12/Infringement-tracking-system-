import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class TeacherDashboard extends JFrame {
    private JList<String> fileList;
    private JButton refreshButton;
    private JButton compareButton;

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

        compareButton = new JButton("Compare Files");
        compareButton.addActionListener(e -> compareSelectedFiles());
        add(compareButton, BorderLayout.SOUTH);

        refreshFileList();

        setVisible(true);
    }

    private void refreshFileList() {
        // Call the Login.getUploadedFiles() method to retrieve the list of uploaded
        // files from the database
        List<FileRecord> uploadedFiles = (List<FileRecord>) Login.getUploadedFiles();

        // Create a DefaultListModel to hold the file names
        DefaultListModel<String> model = new DefaultListModel<>();

        // Add each file name to the model
        for (FileRecord file : uploadedFiles) {
            model.addElement((String) file.getFilename());
        }

        // Set the JList's model to the DefaultListModel
        fileList.setModel(model);
    }

    private void compareSelectedFiles() {
        // Get the list of selected file names
        List<String> selectedFiles = fileList.getSelectedValuesList();

        // Make sure at least 2 files are selected
        if (selectedFiles.size() < 2) {
            JOptionPane.showMessageDialog(this, "Please select at least two files to compare.");
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

        // Use the Jaccard Similarity algorithm to compare the selected files
        LongestCommonSubsequenceDistance lcsDistance = new LongestCommonSubsequenceDistance();

        // Build a message with the plagiarism check results
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("Plagiarism Check Results:\n\n");

        for (String file1 : selectedFiles) {
            for (String file2 : selectedFiles) {
                if (!file1.equals(file2)) {
                    Object levenshteinDistance;
                    int distance = levenshteinDistance.apply(selectedFileContents.get(file1), selectedFileContents.get(file2));
                    double similarity = 1.0 - ((double) distance) / Math.max(selectedFileContents.get(file1).length(),
                            selectedFileContents.get(file2).length());

                    resultMessage.append(String.format("%s vs %s: %.2f\n", file1, file2, similarity));
                }
            }
        }

        // Show the plagiarism check results in a dialog box
        JOptionPane.showMessageDialog(this, resultMessage.toString());
                // Show the plagiarism check results in a dialog box
        JOptionPane.showMessageDialog(this, resultMessage.toString());
    
    }
            }
    

        