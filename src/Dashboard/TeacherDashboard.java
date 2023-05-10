import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;

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

    private Object compareSelectedFiles() {
        return null;
    }

    private void checkAiGenerated() {
        // Your implementation
    }

    private void refreshFileList() {
        List<String> fileNames = fetchFileNames();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String fileName : fileNames) {
            model.addElement(fileName);
        }
        fileList.setModel(model);
    }

    private List<String> fetchFileNames() {
        // Replace this method with your implementation to fetch the file names from the
        // database or any other source
        List<String> fileNames = new ArrayList<>();
        fileNames.add("file1.txt");
        fileNames.add("file2.txt");
        fileNames.add("file3.txt");
        return fileNames;
    }

    public static class FileRecord {
        private String filename;
        private String content;

        public FileRecord(String filename, String content) {
            this.filename = filename;
            this.content = content;
        }

        public String getFilename() {
            return filename;
        }

        public String getContent() {
            return content;
        }
    }

    public static class Login {
        public static List<FileRecord> getUploadedFiles() {
            List<FileRecord> uploadedFiles = new ArrayList<>();
            // Add dummy files
            uploadedFiles.add(new FileRecord("file1.txt", "File 1 content"));
            uploadedFiles.add(new FileRecord("file2.txt", "File 2 content"));
            uploadedFiles.add(new FileRecord("file3.txt", "File 3 content"));

            return uploadedFiles;
        }
    }

    public JList<String> getFileList() {
        return fileList;
    }

    public void setFileList(JList<String> fileList) {
        this.fileList = fileList;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public void setRefreshButton(JButton refreshButton) {
        this.refreshButton = refreshButton;
    }

    public JButton getCompareButton() {
        return compareButton;
    }

    public void setCompareButton(JButton compareButton) {
        this.compareButton = compareButton;
    }

    public JButton getCheckAiGeneratedButton() {
        return checkAiGeneratedButton;
    }

    public void setCheckAiGeneratedButton(JButton checkAiGeneratedButton) {
        this.checkAiGeneratedButton = checkAiGeneratedButton;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fileList == null) ? 0 : fileList.hashCode());
        result = prime * result + ((refreshButton == null) ? 0 : refreshButton.hashCode());
        result = prime * result + ((compareButton == null) ? 0 : compareButton.hashCode());
        result = prime * result + ((checkAiGeneratedButton == null) ? 0 : checkAiGeneratedButton.hashCode());
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
        TeacherDashboard other = (TeacherDashboard) obj;
        if (fileList == null) {
            if (other.fileList != null)
                return false;
        } else if (!fileList.equals(other.fileList))
            return false;
        if (refreshButton == null) {
            if (other.refreshButton != null)
                return false;
        } else if (!refreshButton.equals(other.refreshButton))
            return false;
        if (compareButton == null) {
            if (other.compareButton != null)
                return false;
        } else if (!compareButton.equals(other.compareButton))
            return false;
        if (checkAiGeneratedButton == null) {
            if (other.checkAiGeneratedButton != null)
                return false;
        } else if (!checkAiGeneratedButton.equals(other.checkAiGeneratedButton))
            return false;
        return true;
    }
}
