package file.sample1.src.sample.login_file.java;
    import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class LoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JComboBox<String> userTypeComboBox;
    private Connection conn;

    public LoginGUI() {
        // Create the JFrame
        super("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        String[] userTypes = {"Student", "Teacher", "Admin"};
        userTypeComboBox = new JComboBox<String>(userTypes);

        // Add the components to the JFrame
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel("User Type:"));
        panel.add(userTypeComboBox);
        panel.add(new JLabel(""));
        panel.add(loginButton);
        add(panel);

        //  Connect to the MySQL database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

         // Display the JFrame
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeComboBox.getSelectedItem();

        // Check the login credentials
        if (userType.equals("Student")) {
            if (isValidStudentLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Welcome, student " + username + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } else if (userType.equals("Teacher")) {
            if (isValidTeacherLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Welcome, teacher " + username + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } else if (userType.equals("Admin")) {
            if (isValidAdminLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Welcome, admin " + username + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        }
    }

    private boolean isValidStudentLogin(String username, String password) {
        // Check the student login credentials
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE username=? AND password=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean isValidTeacherLogin(String username, String password) {
        // Check the teacher login credentials
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teachers WHERE username=? AND password=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
	                return true;
} 
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return false;
}

private boolean isValidAdminLogin(String username, String password) {
    // Check the admin login credentials
    if (username.equals("Admin") && password.equals("adminaccess1"))
    if (username.equals("Admin2") && password.equals("adminaccess2"))
     {
        return true;
    }
    return false;
}

public static void main(String[] args) {
    // Create the LoginGUI object
    new LoginGUI();
}}
