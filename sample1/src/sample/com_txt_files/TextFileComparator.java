package sample1.src.sample.com_txt_files;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextFileComparator {

    private JFrame frame;
    private JPanel panel;
    private JTextArea textArea;
    private JButton uploadButton;
    private JButton compareButton;

    private List<File> uploadedFiles = new ArrayList<>();

    private String dbUrl = "jdbc:mysql://localhost:3306/text_files_db";
    private String dbUser = "root";
    private String dbPassword = "password";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TextFileComparator window = new TextFileComparator();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TextFileComparator() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Text File Comparator");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBounds(0, 0, 584, 361);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("Uploaded Files:");
        label.setBounds(10, 10, 100, 20);
        panel.add(label);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 40, 350, 280);
        panel.add(scrollPane);

        uploadButton = new JButton("Upload Files");
        uploadButton.setBounds(400, 40, 150, 30);
        panel.add(uploadButton);

        compareButton = new JButton("Compare Files");
        compareButton.setBounds(400, 80, 150, 30);
        panel.add(compareButton);

        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadFiles();
            }
        });

        compareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compareFiles();
            }
        });
    }

    private void uploadFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                uploadedFiles.add(file);
                textArea.append(file.getName() + "\n");
            }
        }
    }

    private void compareFiles() {
        List<String> sameFiles = new ArrayList<>();
       for (int j = i + 1; j < uploadedFiles.size(); j++) {
            File file1 = uploadedFiles.get(i);
            File file2 = uploadedFiles.get(j);
            try {
                String content1 = readFileContent(file1);
                String content2 = readFileContent(file2);
                if (areFilesSame(content1, content2)) {
                    sameFiles.add(file1.getName() + " and " + file2.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    if (!sameFiles.isEmpty()) {
        List<String> sameFiles;
        saveReport(sameFiles);
        JOptionPane.showMessageDialog(frame, "Report generated successfully!");
    } else {
        JOptionPane.showMessageDialog(frame, "No matching files found!");
    }
}

private String readFileContent(File file) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
    }
    br.close();
    return sb.toString();
}

private boolean areFilesSame(String content1, String content2) {
    // Use a string comparison algorithm to determine if the files are the same
    // For example, use Levenshtein distance or Jaro-Winkler distance
    // For simplicity, let's assume the contents of the files should match exactly
    return content1.equals(content2);
}

private void saveReport(List<String> sameFiles) {
    String reportFileName = "matching_files.txt";
    try {
        File reportFile = new File(reportFileName);
        FileWriter writer = new FileWriter(reportFile);
        for (String files : sameFiles) {
            writer.write(files + "\n");
        }
        writer.close();

        // Save the report file to the database
        saveReportToDatabase(reportFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void saveReportToDatabase(File reportFile) {
    try {
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        String sql = "INSERT INTO reports (report_name, report_data) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, reportFile.getName());
        statement.setBinaryStream(2, reportFile.toURI().toURL().openStream(), (int) reportFile.length());
        statement.executeUpdate();
        statement.close();
        conn.close();
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}