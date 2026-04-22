package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;

    public LoginForm() {

        setTitle("Architecture System - Login");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        btnLogin.setBackground(new Color(140, 245, 81));
        btnLogin.setForeground(Color.WHITE);

        btnRegister.setBackground(new Color(70, 126, 249));
        btnRegister.setForeground(Color.WHITE);

        add(lblUser);
        add(txtUsername);

        add(lblPass);
        add(txtPassword);

        add(btnLogin);
        add(btnRegister);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> new RegisterForm());

        setVisible(true);
    }

    private void login() {

        String sql = "SELECT * FROM Users WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txtUsername.getText());
            ps.setString(2, new String(txtPassword.getPassword()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");
                String name = rs.getString("first_name");
                int userId = rs.getInt("id");

                new MainForm(role, name, userId);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Invalid login!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
