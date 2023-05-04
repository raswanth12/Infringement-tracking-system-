package file.sample.panal;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sample.user.DatabaseConnection;

public class TeacherPanel extends JFrame implements ActionListener {
    private static final int List = 0;
    private JButton comparisonReportButton;
    private JButton aiReportButton;
    private int String;

    public TeacherPanel() {
        setTitle("Teacher Panel");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        comparisonReportButton = new JButton("Generate Comparison Report");
        comparisonReportButton.addActionListener(this);
        add(comparisonReportButton);

        aiReportButton = new JButton("Generate AI Report");
        aiReportButton.addActionListener(this);
        add(aiReportButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comparisonReportButton) {
            List<String> studentFiles = fetchStudentFiles();
            String comparisonReport = generateComparisonReport(studentFiles);
            JTextArea reportArea = new JTextArea(comparisonReport);
            JOptionPane.showMessageDialog(this, new JScrollPane(reportArea), "Comparison Report",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == aiReportButton) {
            List<String> studentFiles = fetchStudentFiles();
            String aiReport = generateAIReport(studentFiles);
            JTextArea reportArea = new JTextArea(aiReport);
            JOptionPane.showMessageDialog(this, new JScrollPane(reportArea), "AI Report",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<String> fetchStudentFiles() {
        List<String> studentFiles = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT content FROM student_files";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentFiles.add(rs.getString("content"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentFiles;
    }

    private String generateComparisonReport(List<String> studentFiles) {
        int sameContentCount = 0;

        for (int i = 0; i < studentFiles.size(); i++) {
            for (int j = i + 1; j < studentFiles.size(); j++) {
                if (studentFiles.get(i).equals(studentFiles.get(j))) {
                    sameContentCount++;
                }
            }
        }
        return "Comparison report: " + sameContentCount + " pairs of files have the same content.";
    }

    private String generateAIReport(List<String> studentFiles) {
        // TODO: Implement an AI check using the ChatGPT API and generate a report
        private String generateAIReport(List >= String> studentFiles) {
    StringBuilder aiReport = new StringBuilder();
    OkHttpClient client = new OkHttpClient();

    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String apiKey = "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"; // Replace this with your actual API key

    for (String studentFile : studentFiles) {
        JSONObject prompt = new JSONObject();
        prompt.put("text", "Analyze the following student file:\n" + studentFile);

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", prompt));

        JSONObject requestBody = new JSONObject();
        requestBody.put("messages", messages);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/engines/davinci-codex/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(requestBody.toString(), JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject jsonResponse = new JSONObject(response.body().string());
            String analysis = jsonResponse.getJSONObject("choices").getJSONArray("text").getString(0);
            aiReport.append("AI Analysis for student file:\n")
                    .append(studentFile)
                    .append("\n\n")
                    .append(analysis)
                    .append("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating AI report.";
        }
    }

    return aiReport.toString();
}
        return "AI report not yet implemented.";
    }
}
