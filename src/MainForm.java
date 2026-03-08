import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    public MainForm(){

        setTitle("Architecture Firm System");
        setSize(350,250);
        setLayout(new GridLayout(5,1,5,5));
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Architecture Firm Management",SwingConstants.CENTER);

        JButton btnProjects = new JButton("Projects");
        JButton btnClients = new JButton("Clients");
        JButton btnEmployees = new JButton("Employees");
        JButton btnLogout = new JButton("Logout");

        add(title);
        add(btnProjects);
        add(btnClients);
        add(btnEmployees);
        add(btnLogout);

        btnLogout.addActionListener(e -> {

            new LoginForm();
            dispose();

        });

        setVisible(true);

    }

}