package gui;

import db.DBConnection;
import util.Validator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class RegisterForm extends JFrame {

    JTextField     txtUsername, txtFirst, txtLast, txtAge, txtDOB;
    JPasswordField txtPassword;
    JComboBox<String> roleBox;
    byte[] photoBytes;

    public RegisterForm() {

        setTitle("User Registration");
        setSize(520, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(25, 25, 50));
        top.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        JLabel header = new JLabel("REGISTER", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        top.add(header);
        add(top, BorderLayout.NORTH);

        // Form grid
        JPanel form = new JPanel(new GridLayout(8, 2, 12, 12));
        form.setBorder(BorderFactory.createEmptyBorder(25, 45, 15, 45));
        form.setBackground(Color.WHITE);

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtFirst    = new JTextField();
        txtLast     = new JTextField();
        txtDOB      = new JTextField();
        txtAge      = new JTextField();

        // (e) ChangeListener on JComboBox — updates a hint label when role changes
        roleBox = new JComboBox<>(new String[]{"EMPLOYEE", "CLIENT"});
        JLabel roleHint = new JLabel("Can view assigned projects");
        roleHint.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        roleHint.setForeground(new Color(120, 120, 120));

        roleBox.addActionListener(e -> {
            String selected = (String) roleBox.getSelectedItem();
            if ("CLIENT".equals(selected)) {
                roleHint.setText("Can create and edit own projects");
            } else {
                roleHint.setText("Can view assigned projects");
            }
        });

        form.add(lbl("Username *"));      form.add(txtUsername);
        form.add(lbl("Password *"));      form.add(txtPassword);
        form.add(lbl("First Name *"));    form.add(txtFirst);
        form.add(lbl("Last Name *"));     form.add(txtLast);
        form.add(lbl("Date of Birth"));   form.add(txtDOB);
        form.add(lbl("Age *"));           form.add(txtAge);
        form.add(lbl("Role *"));          form.add(roleBox);
        form.add(lbl(""));                form.add(roleHint);

        add(form, BorderLayout.CENTER);

        // Buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 14));
        bottom.setBackground(Color.WHITE);

        JButton btnPhoto  = createButton("Upload Photo", new Color(155, 89, 182));
        JButton btnSave   = createButton("Save",         new Color(46, 204, 113));
        JButton btnCancel = createButton("Cancel",       new Color(200, 60, 60));

        btnPhoto.addActionListener(e -> uploadPhoto());
        btnSave.addActionListener(e -> saveUser());
        btnCancel.addActionListener(e -> dispose());

        bottom.add(btnPhoto);
        bottom.add(btnSave);
        bottom.add(btnCancel);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void uploadPhoto() {
        try {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try (FileInputStream fis = new FileInputStream(file)) {
                    photoBytes = fis.readAllBytes();
                    JOptionPane.showMessageDialog(this, "Photo uploaded successfully.");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void saveUser() {

        if (Validator.isEmpty(txtUsername.getText())) {
            JOptionPane.showMessageDialog(this, "Username is required.");
            txtUsername.requestFocus(); return;
        }
        if (txtPassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Password is required.");
            txtPassword.requestFocus(); return;
        }
        if (Validator.isEmpty(txtFirst.getText())) {
            JOptionPane.showMessageDialog(this, "First name is required.");
            txtFirst.requestFocus(); return;
        }
        if (Validator.isEmpty(txtLast.getText())) {
            JOptionPane.showMessageDialog(this, "Last name is required.");
            txtLast.requestFocus(); return;
        }
        if (!Validator.isValidInt(txtAge.getText())) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.");
            txtAge.requestFocus(); return;
        }

        String role = (String) roleBox.getSelectedItem();

        String userSql = """
            INSERT INTO Users(username, password, first_name, last_name, email,
                              date_of_birth, role, photo)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(userSql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, txtUsername.getText().trim());
            ps.setString(2, new String(txtPassword.getPassword()));
            ps.setString(3, txtFirst.getText().trim());
            ps.setString(4, txtLast.getText().trim());
            ps.setString(5, txtUsername.getText().trim() + "@mail.com");
            ps.setString(6, Validator.isEmpty(txtDOB.getText()) ? null : txtDOB.getText().trim());
            ps.setString(7, role);
            ps.setBytes(8, photoBytes);
            ps.executeUpdate();

            // Get the new user's generated ID
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int newUserId = keys.getInt(1);
                insertRoleRecord(conn, role, newUserId,
                        txtFirst.getText().trim() + " " + txtLast.getText().trim(),
                        txtUsername.getText().trim() + "@mail.com");
            }

            JOptionPane.showMessageDialog(this, "Registered successfully! You can now log in.");
            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registration failed: " + e.getMessage());
        }
    }

    private void insertRoleRecord(Connection conn, String role,
                                  int userId, String name, String email)
            throws SQLException {
        if ("EMPLOYEE".equals(role)) {
            String sql = "INSERT INTO Employees(name, age, email, dept_id, user_id) VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setInt(2, Validator.isValidInt(txtAge.getText())
                        ? Integer.parseInt(txtAge.getText().trim()) : 18);
                ps.setString(3, email);
                ps.setInt(4, 1); // default to first department
                ps.setInt(5, userId);
                ps.executeUpdate();
            }
        } else if ("CLIENT".equals(role)) {
            String sql = "INSERT INTO Clients(name, email, user_id) VALUES(?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setInt(3, userId);
                ps.executeUpdate();
            }
        }
    }

    private JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return l;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(140, 42));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}