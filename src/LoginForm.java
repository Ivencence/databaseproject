import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;

    public LoginForm() {

        setTitle("Architecture Firm Login");
        setSize(350,200);
        setLayout(new GridLayout(4,2,5,5));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");
        JButton btnCancel = new JButton("Cancel");

        add(btnLogin);
        add(btnRegister);
        add(btnCancel);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> new RegisterForm());
        btnCancel.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void login() {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, txtUsername.getText());
            ps.setString(2, new String(txtPassword.getPassword()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                new MainForm();
                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "To use the application, you must register in the system!");

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void clearFields(){

        txtUsername.setText("");
        txtPassword.setText("");

    }

}