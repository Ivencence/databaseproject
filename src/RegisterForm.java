import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class RegisterForm extends JFrame {

    JTextField txtUsername, txtFirst, txtLast, txtAge, txtAddress, txtDOB;
    JPasswordField txtPassword;

    byte[] photoBytes;

    public RegisterForm(){

        setTitle("User Registration");
        setSize(400,450);
        setLayout(new GridLayout(9,2,5,5));
        setLocationRelativeTo(null);

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtFirst = new JTextField();
        txtLast = new JTextField();
        txtDOB = new JTextField();
        txtAge = new JTextField();
        txtAddress = new JTextField();

        JButton btnPhoto = new JButton("Upload Photo");
        JButton btnSave = new JButton("Save");
        JButton btnBack = new JButton("Back");

        add(new JLabel("Username")); add(txtUsername);
        add(new JLabel("Password")); add(txtPassword);
        add(new JLabel("First Name")); add(txtFirst);
        add(new JLabel("Last Name")); add(txtLast);
        add(new JLabel("Date of Birth")); add(txtDOB);
        add(new JLabel("Age")); add(txtAge);
        add(new JLabel("Address")); add(txtAddress);
        add(btnPhoto);
        add(new JLabel(""));
        add(btnSave);
        add(btnBack);

        btnPhoto.addActionListener(e -> uploadPhoto());
        btnSave.addActionListener(e -> saveUser());
        btnBack.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void uploadPhoto(){

        try{

            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(this);

            File file = chooser.getSelectedFile();

            FileInputStream fis = new FileInputStream(file);
            photoBytes = fis.readAllBytes();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void saveUser(){

        String sql = "INSERT INTO users(username,password,firstName,lastName,dateOfBirth,age,address,photo) VALUES(?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, txtUsername.getText());
            ps.setString(2, new String(txtPassword.getPassword()));
            ps.setString(3, txtFirst.getText());
            ps.setString(4, txtLast.getText());
            ps.setString(5, txtDOB.getText());
            ps.setInt(6, Integer.parseInt(txtAge.getText()));
            ps.setString(7, txtAddress.getText());
            ps.setBytes(8, photoBytes);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"User registered successfully!");

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}