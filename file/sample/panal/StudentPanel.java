package file.sample.panal;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import file.sample.user.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentPanel extends JFrame implements ActionListener {
    private JButton uploadButton;
    private int studentId;

    public StudentPanel(int studentId) {
        this.studentId = studentId;

        setTitle("Student Panel");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        uploadButton = new JButton("Upload .txt file");
        uploadButton.addActionListener(this);
        add(uploadButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    String content = new String(Files.readAllBytes(selectedFile.toPath()));
                    saveFileToDatabase(content);
                    JOptionPane.showMessageDialog(this, "File uploaded successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error reading file. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void saveFileToDatabase(String content) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO student_files (student_id, content) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setString(2, content);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error uploading file. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
