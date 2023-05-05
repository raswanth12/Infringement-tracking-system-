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
        uploadButton.addActionListener((ActionListener) new UploadFileListener());
        add(uploadButton);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        setVisible(true);
    }

    private class UploadFileListener implements ActionListener {
        private ActionEvent e;

        @Override
        public void actionPerformed(ActionEvent e) {
            this.e = e;
            int result = fileChooser.showOpenDialog(StudentDashboard.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    String content = new String(Files.readAllBytes(selectedFile.toPath()));
                    boolean success = Login.uploadFile(/*studentId*/, selectedFile.getName(), content);
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

        /**
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }
}
