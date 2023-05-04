// package sample.panal;

// import javax.swing.*;

// import sample.data.DatabaseConnection;

// import java.awt.*;
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// public class AdminPanel extends JFrame {
//     private JList<String> userList;
//     private JTextArea fileContentArea;

//     public AdminPanel() {
//         setTitle("Admin Panel");
//         setLayout(new BorderLayout());
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         initComponents();
//         pack();
//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     private void initComponents() {
//         userList = new JList<>(fetchUserList());
//         userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//         userList.addListSelectionListener(e -> {
//             if (!e.getValueIsAdjusting()) {
//                 int selectedIndex = userList.getSelectedIndex();
//                 if (selectedIndex != -1) {
//                     String selectedUser = userList.getSelectedValue();
//                     String[] userParts = selectedUser.split(" - ");
//                     if ("student".equalsIgnoreCase(userParts[1])) {
//                         int studentId = Integer.parseInt(userParts[0]);
//                         fetchStudentFile(studentId);
//                     } else {
//                         fileContentArea.setText("");
//                     }
//                 }
//             }
//         });

//         JScrollPane userScrollPane = new JScrollPane(userList);
//         userScrollPane.setPreferredSize(new Dimension(200, 200));
//         add(userScrollPane, BorderLayout.WEST);

//         fileContentArea = new JTextArea();
//         fileContentArea.setEditable(false);
//         JScrollPane fileContentScrollPane = new JScrollPane(fileContentArea);
//         add(fileContentScrollPane, BorderLayout.CENTER);
//     }

//     private String[] fetchUserList() {
//         List<String> userList = new ArrayList<>();

//         try (Connection conn = DatabaseConnection.getConnection()) {
//             String query = "SELECT id, username, role FROM users";
//             PreparedStatement stmt = conn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery();

//             while (rs.next()) {
//                 userList.add(rs.getInt("id") + " - " + rs.getString("role") + ": " + rs.getString("username"));
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }

//         return userList.toArray(new String[0]);
//     }

//     private void fetchStudentFile(int studentId) {
//         try (Connection conn = DatabaseConnection.getConnection()) {
//             String query = "SELECT content FROM student_files WHERE student_id = ?";
//             PreparedStatement stmt = conn.prepareStatement(query);
//             stmt.setInt(1, studentId);
//             ResultSet rs = stmt.executeQuery();

//             if (rs.next()) {
//                 fileContentArea.setText(rs.getString("content"));
//             } else {
//                 fileContentArea.setText("No file found.");
//             }
//         } catch (SQLException ex) {
//             ex.printStackTrace();
//         }
//     }
// }

package file.sample.panal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame implements ActionListener {

    private JButton manageStudentsButton;
    private JButton manageTeachersButton;
    private JButton viewReportsButton;

    public AdminPanel() {
        setTitle("Admin Panel");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        // Manage Students button
        manageStudentsButton = new JButton("Manage Students");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(manageStudentsButton, constraints);
        manageStudentsButton.addActionListener(this);

        // Manage Teachers button
        manageTeachersButton = new JButton("Manage Teachers");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(manageTeachersButton, constraints);
        manageTeachersButton.addActionListener(this);

        // View Reports button
        viewReportsButton = new JButton("View Reports");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        add(viewReportsButton, constraints);
        viewReportsButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageStudentsButton) {
            // Implement the functionality for managing students
            System.out.println("Manage Students button clicked");
        } else if (e.getSource() == manageTeachersButton) {
            // Implement the functionality for managing teachers
            System.out.println("Manage Teachers button clicked");
        } else if (e.getSource() == viewReportsButton) {
            // Implement the functionality for viewing reports
            System.out.println("View Reports button clicked");
        }
    }
}
