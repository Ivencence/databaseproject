package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {

    JTextField     txtUsername;
    JPasswordField txtPassword;

    public LoginForm() {

        setTitle("Architecture System - Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(25, 25, 50));
        top.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));
        JLabel title = new JLabel("ARCHITECTURE SYSTEM", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        top.add(title);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 12, 12));
        form.setBorder(BorderFactory.createEmptyBorder(25, 40, 15, 40));
        form.setBackground(Color.WHITE);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        form.add(lblUser); form.add(txtUsername);
        form.add(lblPass); form.add(txtPassword);
        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        bottom.setBackground(Color.WHITE);

        JButton btnLogin    = createButton("Login",    new Color(46, 204, 113));
        JButton btnRegister = createButton("Register", new Color(52, 152, 219));
        JButton btnCancel   = createButton("Cancel",   new Color(200, 60, 60));

        bottom.add(btnLogin);
        bottom.add(btnRegister);
        bottom.add(btnCancel);
        add(bottom, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> new RegisterForm());
        btnCancel.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) login();
            }
        });

        txtUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                txtUsername.setBackground(new Color(240, 248, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                txtUsername.setBackground(Color.WHITE);
            }
        });

        setVisible(true);
    }

    private void login() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter username and password.");
            return;
        }

        String sql = """
            SELECT u.*,
                CASE
                    WHEN a.user_id IS NOT NULL THEN 'ADMIN'
                    WHEN m.user_id IS NOT NULL THEN 'MANAGER'
                    WHEN e.user_id IS NOT NULL THEN 'EMPLOYEE'
                    WHEN c.user_id IS NOT NULL THEN 'CLIENT'
                    ELSE 'UNKNOWN'
                END AS role
            FROM Users u
            LEFT JOIN Admins    a ON u.id = a.user_id
            LEFT JOIN Managers  m ON u.id = m.user_id
            LEFT JOIN Employees e ON u.id = e.user_id
            LEFT JOIN Clients   c ON u.id = c.user_id
            WHERE u.username=? AND u.password=?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role   = rs.getString("role");
                String name   = rs.getString("first_name");
                int    userId = rs.getInt("id");
                new MainForm(role, name, userId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "To use the application, you must register in the system.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 42));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}