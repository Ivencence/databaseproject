package gui;

import dao.EmployeeDAO;
import dao.ProjectDAO;
import model.Employee;
import model.Project;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignProjectForm extends JFrame {

    private JComboBox<String> employeeBox;
    private JComboBox<String> projectBox;

    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private ProjectDAO projectDAO = new ProjectDAO();

    private List<Employee> employees;
    private List<Project> projects;

    public AssignProjectForm() {

        setTitle("Assign Projects");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("ASSIGN PROJECTS", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setOpaque(true);
        header.setBackground(new Color(25, 25, 50));
        header.setForeground(Color.WHITE);

        add(header, BorderLayout.NORTH);

        employees = employeeDAO.getAll();
        projects = projectDAO.getAll();

        employeeBox = new JComboBox<>();
        projectBox = new JComboBox<>();

        for (Employee e : employees) {
            employeeBox.addItem(e.getId() + " - " + e.getName());
        }

        for (Project p : projects) {
            projectBox.addItem(p.getId() + " - " + p.getTitle());
        }

        JPanel form = new JPanel(new GridLayout(4, 1, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Select Employee:"));
        form.add(employeeBox);

        form.add(new JLabel("Select Project:"));
        form.add(projectBox);

        add(form, BorderLayout.CENTER);

        JButton assignBtn = new JButton("Assign");

        assignBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        assignBtn.setBackground(new Color(46, 204, 113));
        assignBtn.setForeground(Color.WHITE);

        assignBtn.addActionListener(e -> assign());

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(assignBtn);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void assign() {

        int empIndex = employeeBox.getSelectedIndex();
        int projIndex = projectBox.getSelectedIndex();

        if (empIndex == -1 || projIndex == -1) {
            JOptionPane.showMessageDialog(this, "Select both employee and project!");
            return;
        }

        int employeeId = employees.get(empIndex).getId();
        int projectId = projects.get(projIndex).getId();

        projectDAO.assignProjectToEmployee(projectId, employeeId);

        JOptionPane.showMessageDialog(this, "Assignment successful!");

        dispose();
    }
}
