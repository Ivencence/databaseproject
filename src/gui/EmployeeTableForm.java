package gui;

import dao.EmployeeDAO;
import model.Employee;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableForm extends JFrame {

    private EmployeeDAO dao = new EmployeeDAO();

    public EmployeeTableForm(String role, int userId) {

        setTitle(role.equals("EMPLOYEE") ? "My Account" : "Employees");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel top = createHeader(
                role.equals("EMPLOYEE") ? "MY ACCOUNT" : "EMPLOYEES"
        );

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        List<Employee> employees = new ArrayList<>();

        if (role.equals("ADMIN")) {
            employees = dao.getAll();
        } else if (role.equals("EMPLOYEE")) {
            Employee e = dao.getById(userId);
            if (e != null) employees.add(e);
        }

        for (Employee e : employees) {
            container.add(createCard(
                    "ID: " + e.getId(),
                    "Name: " + e.getName(),
                    "Email: " + e.getEmail(),
                    "Department: " + e.getDeptId()
            ));
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        JButton back = createBack(role, userId);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createHeader(String title) {

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(25, 25, 50));

        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 36));

        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private JPanel createCard(String... lines) {

        JPanel card = new JPanel();
        card.setLayout(new GridLayout(lines.length, 1, 5, 5));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        for (String line : lines) {
            JLabel lbl = new JLabel(line);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            card.add(lbl);
        }

        return card;
    }

    private JButton createBack(String role, int userId) {

        JButton b = new JButton("Back");
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));

        b.addActionListener(e -> {
            dispose();
            new MainForm(role, "User", userId);
        });

        return b;
    }
}
