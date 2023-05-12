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
    private JButton generateAiTextButton;

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

        // "Check AI Generated" button
        checkAiGeneratedButton = new JButton("Check AI Generated");
        checkAiGeneratedButton.addActionListener(e -> checkAiGenerated());
        buttonPanel.add(checkAiGeneratedButton);
        

        refreshFileList();

        setVisible(true);
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    private void compareSelectedFiles() {
        List<String> selectedFiles = fileList.getSelectedValuesList();

        if (selectedFiles.size() != 2) {
            JOptionPane.showMessageDialog(this, "Please select exactly two files to compare.");
            return;
        }

        try {
            FileRecord file1 = getFileContent(selectedFiles.get(0));
            FileRecord file2 = getFileContent(selectedFiles.get(1));

            if (file1.getContent().equals(file2.getContent())) {
                JOptionPane.showMessageDialog(this, "The selected files are identical.");
            } else {
                JOptionPane.showMessageDialog(this, "The selected files are not identical.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error comparing files: " + e.getMessage());
        }
    }

    private void checkAiGenerated() {
        String selectedFile = fileList.getSelectedValue();

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a file to check.");
            return;
        }

        try {
            FileRecord file = getFileContent(selectedFile);
            String content = file.getContent();

            RequestBody body = RequestBody.create(JSON, gson.toJson(Collections.singletonMap("content", content)));
            Request request = new Request.Builder()
                    .url("https://hypothetical-api.com/check-ai-generated")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                java.lang.reflect.Type type = new TypeToken<Map<String, Boolean>>() {
                }.getType();
                Map<String, Boolean> map = gson.fromJson(response.body().string(), type);
                Boolean isAiGenerated = map.get("isAiGenerated");

                if (isAiGenerated == null) {
                    throw new IOException("Invalid response from server");
                }

                JOptionPane.showMessageDialog(this,
                        "The selected file is " + (isAiGenerated ? "" : "not ") + "AI-generated.");

            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking file: " + e.getMessage());
        }
    }

    private FileRecord getFileContent(String fileName) throws IOException {
        Request request = new Request.Builder()
                .url("https://hypothetical-api.com/files/" + fileName)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return gson.fromJson(response.body().string(), FileRecord.class);
        }
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
        
        Request request = new Request.Builder()
                .url("https://hypothetical-api.com/files")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Assume the API returns a JSON array of file names
            java.lang.reflect.Type listType = new TypeToken<List<String>>() {
            }.getType();
            Object fileNames = gson.fromJson(response.body().charStream(), listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            uploadedFiles.add(new FileRecord("file1.txt", "Project/ai-1.txt"));
            uploadedFiles.add(new FileRecord("file2.txt", "Project/ai.txt"));
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



// import java.awt.BorderLayout;
// import java.util.*;
// import javax.swing.*;
// import okhttp3.*;
// import com.google.gson.Gson;
// import com.google.gson.reflect.TypeToken;
// import java.io.IOException;
// import java.lang.reflect.Type;

// public class TeacherDashboard extends JFrame {
//     private JList<String> fileList;
//     private JButton refreshButton;
//     private JButton compareButton;
//     private JButton checkAiGeneratedButton;

//     private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//     private OkHttpClient client = new OkHttpClient();
//     private Gson gson = new Gson();

//     public TeacherDashboard() {
//         setTitle("Teacher Dashboard");
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setSize(400, 300);
//         setLayout(new BorderLayout());

//         refreshButton = new JButton("Refresh");
//         refreshButton.addActionListener(e -> refreshFileList());
//         add(refreshButton, BorderLayout.NORTH);

//         fileList = new JList<>();
//         add(new JScrollPane(fileList), BorderLayout.CENTER);

//         JPanel buttonPanel = new JPanel();
//         add(buttonPanel, BorderLayout.SOUTH);

//         compareButton = new JButton("Compare Files");
//         compareButton.addActionListener(e -> compareSelectedFiles());
//         buttonPanel.add(compareButton);

//         checkAiGeneratedButton = new JButton("Check AI Generated");
//         checkAiGeneratedButton.addActionListener(e -> checkAiGenerated());
//         buttonPanel.add(checkAiGeneratedButton);

//         refreshFileList();

//         setVisible(true);
//     }

//     private Object refreshFileList() {
//         return null;
//     }

//     /**
//      * 
//      */
//     private void compareSelectedFiles() {
//         List<String> selectedFiles = fileList.getSelectedValuesList();

//         if (selectedFiles.size() != 2) {
//             JOptionPane.showMessageDialog(this, "Please select exactly two files to compare.");
//             return;
//         }

//         FileRecord file1 = getFileContent(selectedFiles.get(0));
//         FileRecord file2 = getFileContent(selectedFiles.get(1));

//         if (file1.getContent().equals(file2.getContent())) {
//             JOptionPane.showMessageDialog(this, "The selected files are identical.");
//         } else {
//             JOptionPane.showMessageDialog(this, "The selected files are not identical.");
//         }
//     }

//     private FileRecord getFileContent(String string) {
//         return null;
//     }

//     /**
//      * 
//      */
//     private void checkAiGenerated() {
//         String selectedFile = fileList.getSelectedValue();

//         if (selectedFile == null) {
//             JOptionPane.showMessageDialog(this, "Please select a file to check.");
//             return;
//         }

//         try {
//             FileRecord file = getFileContent(selectedFile);
//             String content = file.getContent();

//             RequestBody body = RequestBody.create(JSON, gson.toJson(Collections.singletonMap("content", content)));
//             Request request = new Request.Builder()
//                     .url("https://hypothetical-api.com/check-ai-generated")
//                     .post(body)
//                     .build();

//             try (Response response = client.newCall(request).execute()) {
//                 if (!response.isSuccessful()) {
//                     throw new IOException("Unexpected code " + response);
//                 }

//                 java.lang.reflect.Type type = new TypeToken<Map<String, Boolean>>() {}.getType();
//                 final Map<String, Boolean> map = gson.fromJson(response.body().string(), type);
//                 Boolean isAiGenerated = map.get("isAiGenerated");

//                 if (isAiGenerated == null) {
//                     throw new IOException("Invalid response from server");
//                 }

//                 JOptionPane.showMessageDialog(this,
//                         "The selected file is " + (isAiGenerated ? "" : "not ") + "AI-generated.");

//             }

//         } catch (IOException e) {
//             JOptionPane.showMessageDialog(this, "Error checking file: " + e.getMessage());
//         }
        
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new TeacherDashboard());
//         try {
//     // Assume "file" is a File object
//     BufferedReader reader = new BufferedReader(new FileReader(file));
//     String line;
//     while ((line = reader.readLine()) != null) {
//         // process the line
//     }
//     reader.close();
// } catch (FileNotFoundException e) {
//     JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
// } catch (IOException e) {
//     JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
// }

//  }

//     public JButton getGenerateAiTextButton() {
//         return generateAiTextButton;
//     }

//     public void setGenerateAiTextButton(JButton generateAiTextButton) {
//         this.generateAiTextButton = generateAiTextButton;
//     }

//     @Override
//     public int hashCode() {
//         final int prime = 31;
//         int result = super.hashCode();
//         result = prime * result + ((checkAiGeneratedButton == null) ? 0 : checkAiGeneratedButton.hashCode());
//         result = prime * result + ((compareButton == null) ? 0 : compareButton.hashCode());
//         result = prime * result + ((fileList == null) ? 0 : fileList.hashCode());
//         result = prime * result + ((refreshButton == null) ? 0 : refreshButton.hashCode());
//         result = prime * result + ((generateAiTextButton == null) ? 0 : generateAiTextButton.hashCode());
//         return result;
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj)
//             return true;
//         if (obj == null)
//             return false;
//         if (getClass() != obj.getClass())
//             return false;
//         TeacherDashboard other = (TeacherDashboard) obj;
//         if (checkAiGeneratedButton == null) {
//             if (other.checkAiGeneratedButton != null)
//                 return false;
//         } else if (!checkAiGeneratedButton.equals(other.checkAiGeneratedButton))
//             return false;
//         if (compareButton == null) {
//             if (other.compareButton != null)
//                 return false;
//         } else if (!compareButton.equals(other.compareButton))
//             return false;
//         if (fileList == null) {
//             if (other.fileList != null)
//                 return false;
//         } else if (!fileList.equals(other.fileList))
//             return false;
//         if (refreshButton == null) {
//             if (other.refreshButton != null)
//                 return false;
//         } else if (!refreshButton.equals(other.refreshButton))
//             return false;
//         if (generateAiTextButton == null) {
//             if (other.generateAiTextButton != null)
//                 return false;
//         } else if (!generateAiTextButton.equals(other.generateAiTextButton))
//             return false;
//         return true;
//     }
// }
// }