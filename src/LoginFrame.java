package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.panel.AdminPanel;
import src.panel.StudentPanel;
import src.panel.TeacherPanel;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeBox;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("User Type:"), gbc);
        String[] userTypes = { "Student", "Teacher", "Admin" };
        userTypeBox = new JComboBox<>(userTypes);
        gbc.gridx = 1;
        add(userTypeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton, gbc);

        gbc.gridy = 4;
        JButton signupButton = new JButton("Sign up");
        signupButton.addActionListener(e -> handleSignup());
        add(signupButton, gbc);

        setLocationRelativeTo(null);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeBox.getSelectedItem();

        boolean isAuthenticated = DatabaseConnection.authenticateUser(username, password, userType);

        if (isAuthenticated) {
            JFrame mainFrame = new JFrame("Main Panel");
            mainFrame.setSize(800, 600);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);

            if ("Student".equals(userType)) {
                mainFrame.add(new StudentPanel(username));
            } else if ("Teacher".equals(userType)) {
                mainFrame.add(new TeacherPanel());
            } else if ("Admin".equals(userType)) {
                mainFrame.add(new AdminPanel());
            }

            mainFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignup() {
        // TODO: Implement sign up logic
    }
}
