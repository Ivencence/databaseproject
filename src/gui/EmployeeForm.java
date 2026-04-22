package gui;

import dao.EmployeeDAO;
import dao.ProjectDAO;
import model.Employee;
import model.Project;
import model.EmployeeTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeForm extends JFrame {

    String role;
    int userId;

    EmployeeDAO employeeDAO = new EmployeeDAO();
    ProjectDAO projectDAO = new ProjectDAO();

    public EmployeeForm(String role, int userId) {

        this.role = role;
        this.userId = userId;

        setTitle(role.equals("EMPLOYEE") ? "My Account & Projects" : "Employees");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JLabel header = new JLabel(
                role.equals("EMPLOYEE") ? "MY ACCOUNT & PROJECTS" : "EMPLOYEE MANAGEMENT",
                SwingConstants.CENTER
        );

        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setOpaque(true);
        header.setBackground(new Color(155, 89, 182));
        header.setForeground(Color.WHITE);

        add(header, BorderLayout.NORTH);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        if (role.equals("ADMIN")) {

            List<Employee> employees = employeeDAO.getAll();

            JTable table = new JTable(new EmployeeTableModel(employees));
            container.add(new JLabel("All Employees"));
            container.add(new JScrollPane(table));
        }

        else if (role.equals("EMPLOYEE")) {

            Employee me = employeeDAO.getById(userId);

            List<Employee> list = new ArrayList<>();
            if (me != null) list.add(me);

            JTable meTable = new JTable(new EmployeeTableModel(list));
            container.add(new JLabel("My Account"));
            container.add(new JScrollPane(meTable));

            container.add(Box.createVerticalStrut(20));
            container.add(new JLabel("My Projects"));

            List<Project> myProjects = projectDAO.getByEmployee(userId);

            String[] cols = {"ID", "Title", "Client ID"};
            Object[][] data = new Object[myProjects.size()][3];

            for (int i = 0; i < myProjects.size(); i++) {
                Project p = myProjects.get(i);
                data[i][0] = p.getId();
                data[i][1] = p.getTitle();
                data[i][2] = p.getClientId();
            }

            JTable projectTable = new JTable(data, cols);
            container.add(new JScrollPane(projectTable));
        }

        add(new JScrollPane(container), BorderLayout.CENTER);

        setVisible(true);
    }
}
