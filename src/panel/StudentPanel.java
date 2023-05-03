package src.panel;

import javax.swing.*;

import src.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StudentPanel extends JPanel {
    private String studentUsername;

    public StudentPanel(String studentUsername) {
        this.studentUsername = studentUsername;
        setLayout(new BorderLayout());

        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        String content = new String(Files.readAllBytes(selectedFile.toPath()), StandardCharsets.UTF_8);
                        boolean isSaved = DatabaseConnection.saveFile(studentUsername, selectedFile.getName(), content);
                        if (isSaved) {
                            JOptionPane.showMessageDialog(null, "File uploaded successfully!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to upload the file!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error reading the file!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        add(uploadButton, BorderLayout.CENTER);
    }
}
