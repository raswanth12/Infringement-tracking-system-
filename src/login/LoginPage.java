import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton studentLoginButton;
    private JButton teacherLoginButton;
    private JButton registerButton;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // Username label and field
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Username:"), c);

        usernameField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 0;
        add(usernameField, c);

        // Password label and field
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Password:"), c);

        passwordField = new JPasswordField(15);
        c.gridx = 1;
        c.gridy = 1;
        add(passwordField, c);

        // Student login button
        studentLoginButton = new JButton("Student Login");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        studentLoginButton.addActionListener(new StudentLoginListener());
        add(studentLoginButton, c);

        // Teacher login button
        teacherLoginButton = new JButton("Teacher Login");
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        teacherLoginButton.addActionListener(new TeacherLoginListener());
        add(teacherLoginButton, c);

        setVisible(true);
        // Registration button
        registerButton = new JButton("Register");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        registerButton.addActionListener(new RegisterListener());
        add(registerButton, c);

    }

    private class StudentLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (Login.authenticateUser("jdbc:mysql://localhost:3306/school", "root", "omen0405")) {
                JOptionPane.showMessageDialog(LoginPage.this, "Student login successful.");
                // Open StudentDashboard
                new StudentDashboard();
                dispose(); // Close LoginPage
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid student credentials.");
            }
        }
    }

    private class TeacherLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (Login.authenticateUser("jdbc:mysql://localhost:3306/school", username, password, password, password)) {
                JOptionPane.showMessageDialog(LoginPage.this, "Teacher login successful.");
                // Open TeacherDashboard
                new TeacherDashboard();
                dispose(); // Close LoginPage
            } else {
                JOptionPane.showMessageDialog(LoginPage.this, "Invalid teacher credentials.");
            }
        }
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // user to select (Student or Teacher)
            String[] userTypes = { "Student", "Teacher" };
            JComboBox<String> userTypeComboBox = new JComboBox<>(userTypes);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("User Type:"));
            panel.add(userTypeComboBox);

            int result = JOptionPane.showConfirmDialog(LoginPage.this, panel, "Register", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String userType = (String) userTypeComboBox.getSelectedItem();

                // Replace username, and password
                String databaseUrl = "jdbc:mysql://localhost:3306/school";
                String dbUsername = "root";
                String dbPassword = "omen0405";

                if (Login.registerUser(userType, username, password, databaseUrl, dbUsername, dbPassword)) {
                    JOptionPane.showMessageDialog(LoginPage.this, userType + " registration successful.");
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Registration failed.");
                }
            }
        }
    }

}
