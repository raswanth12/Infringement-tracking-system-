package sample.user;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherSignupForm extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signupButton;

    public TeacherSignupForm() {
        setTitle("Teacher Signup");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

private void initComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(usernameField, constraints);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(passwordField, constraints);

        // Signup button
        signupButton = new JButton("Signup");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(signupButton, constraints);
        signupButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            signup("teacher");
        }
    }

    private void signup(String role) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // You should hash the password before storing it
            stmt.setString(3, role);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error signing up. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
