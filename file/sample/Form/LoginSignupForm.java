package file.sample.Form;

import javax.swing.*;

import file.sample.panal.AdminPanel;
import file.sample.panal.StudentPanel;
import file.sample.panal.TeacherPanel;
import file.sample.user.DatabaseConnection;
import file.sample.user.StudentSignupForm;
import file.sample.user.TeacherSignupForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignupForm extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton studentSignupButton;
    private JButton teacherSignupButton;

    public LoginSignupForm() {
        setTitle("Login/Signup");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

private void initComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        usernameField.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameField, constraints);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setForeground(Color.BLACK);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(passwordField, constraints);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setForeground(Color.BLACK);
        loginButton.setBackground(Color.LIGHT_GRAY);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(loginButton, constraints);
        loginButton.addActionListener(this);

        // Student signup button
        studentSignupButton = new JButton("Student Signup");
        studentSignupButton.setForeground(Color.BLACK);
        studentSignupButton.setBackground(Color.LIGHT_GRAY);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(studentSignupButton, constraints);
        studentSignupButton.addActionListener(this);
        // Teacher signup button
        teacherSignupButton = new JButton("Teacher Signup");
        teacherSignupButton.setForeground(Color.BLACK);
        teacherSignupButton.setBackground(Color.LIGHT_GRAY);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;
        add(teacherSignupButton, constraints);
        teacherSignupButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            login();
        } else if (e.getSource() == studentSignupButton) {
            openStudentSignup();
        } else if (e.getSource() == teacherSignupButton) {
            openTeacherSignup();
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("Admin") && password.equals("ADMIN123")) {
            // Open the admin panel if default admin credentials are used
            new AdminPanel();
            dispose(); // Close the login form
        } else {
            // Check for student and teacher logins
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password); // You should hash the password before storing and comparing it
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    System.out.println("Logged in as " + role);
                    // You can open the appropriate panel based on the role (student, teacher, or
                    // admin)
                    if ("student".equals(role)) {
                        // Open the student panel
                        new StudentPanel(getDefaultCloseOperation());
                    } else if ("teacher".equals(role)) {
                        // Open the teacher panel
                        new TeacherPanel();
                    } else if ("admin".equals(role)) {
                        // Open the admin panel
                        new AdminPanel();
                    }
                    // Close the login form
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // private void login() {
    //     String username = usernameField.getText();
    //     String password = new String(passwordField.getPassword());

    //     try (Connection conn = DatabaseConnection.getConnection()) {
    //         String query = "SELECT * FROM users WHERE username = ? AND password = ?";
    //         PreparedStatement stmt = conn.prepareStatement(query);
    //         stmt.setString(1, username);
    //         stmt.setString(2, password); // You should hash the password before storing and comparing it
    //         ResultSet rs = stmt.executeQuery();

    //         if (rs.next()) {
    //             String role = rs.getString("role");
    //             System.out.println("Logged in as " + role);
    //             // You can open the appropriate panel based on the role (student, teacher, or
    //             // admin)
    //             if ("student".equals(role)) {
    //                 // Open the student panel
    //                 new StudentPanel(getDefaultCloseOperation());
    //             } else if ("teacher".equals(role)) {
    //                 // Open the teacher panel
    //                 new TeacherPanel();
    //             } else if ("admin".equals(role)) {
    //                 // Open the admin panel
    //                 new AdminPanel();
    //             }
    //             // Close the login form
    //             dispose();
    //         } else {
    //             JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error",
    //                     JOptionPane.ERROR_MESSAGE);
    //         }
    //     } catch (SQLException ex) {
    //         ex.printStackTrace();
    //     }
    // }

    private void openStudentSignup() {
        // Open the student signup form
        new StudentSignupForm();
    }

    private void openTeacherSignup() {
        // Open the teacher signup form
        new TeacherSignupForm();
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new LoginSignupForm());
}
}