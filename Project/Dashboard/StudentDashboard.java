import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StudentDashboard extends JFrame {
    private JButton uploadButton;
    private JFileChooser fileChooser;

    public StudentDashboard() {
        setTitle("Student Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout());

        uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(new UploadFileListener());
        add(uploadButton);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        setVisible(true);
    }

    private class UploadFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = fileChooser.showOpenDialog(StudentDashboard.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    String content = new String(Files.readAllBytes(selectedFile.toPath()));
                    // Make sure to replace "studentId" with the actual studentId
                    int studentId = 1; // Or whatever the actual studentId is
                    boolean success = Login.uploadFile(studentId, selectedFile.getName(), content);
                    if (success) {
                        JOptionPane.showMessageDialog(StudentDashboard.this, "File uploaded successfully.");
                    } else {
                        JOptionPane.showMessageDialog(StudentDashboard.this, "File upload failed.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Other methods and code...


    //     private class UploadFileListener implements ActionListener {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     int result = fileChooser.showOpenDialog(StudentDashboard.this);
    //     if (result == JFileChooser.APPROVE_OPTION) {
    //         File selectedFile = fileChooser.getSelectedFile();
    //         try {
    //             String content = new String(Files.readAllBytes(selectedFile.toPath()));
    //             boolean success = Login.uploadFile(/*studentId*/, selectedFile.getName(), content);
    //             if (success) {
    //                 JOptionPane.showMessageDialog(StudentDashboard.this, "File uploaded successfully.");
    //             } else {
    //                 JOptionPane.showMessageDialog(StudentDashboard.this, "File upload failed.");
    //             }
    //         } catch (IOException ex) {
    //             ex.printStackTrace();
    //         }
    //     }
    // }
    //     }

        /**
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    

    public JButton getUploadButton() {
        return uploadButton;
    }

    public void setUploadButton(JButton uploadButton) {
        this.uploadButton = uploadButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uploadButton == null) ? 0 : uploadButton.hashCode());
        result = prime * result + ((fileChooser == null) ? 0 : fileChooser.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentDashboard other = (StudentDashboard) obj;
        if (uploadButton == null) {
            if (other.uploadButton != null)
                return false;
        } else if (!uploadButton.equals(other.uploadButton))
            return false;
        if (fileChooser == null) {
            if (other.fileChooser != null)
                return false;
        } else if (!fileChooser.equals(other.fileChooser))
            return false;
        return true;
    }
}


