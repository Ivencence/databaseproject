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

        setTitle("Assign Project to Employee");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("ASSIGN PROJECT", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setOpaque(true);
        header.setBackground(new Color(25, 25, 50));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        employees = employeeDAO.getAll();
        projects = projectDAO.getAll();

        employeeBox = new JComboBox<>();
        projectBox = new JComboBox<>();

        for (Employee e : employees) {
            employeeBox.addItem(e.getId() + " – " + e.getName());
        }
        for (Project p : projects) {
            projectBox.addItem(p.getId() + " – " + p.getTitle());
        }

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 15));
        form.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        form.setBackground(Color.WHITE);

        JLabel lblEmp = new JLabel("Employee:");
        lblEmp.setFont(new Font("Segoe UI", Font.BOLD, 15));
        JLabel lblProj = new JLabel("Project:");
        lblProj.setFont(new Font("Segoe UI", Font.BOLD, 15));

        form.add(lblEmp);
        form.add(employeeBox);
        form.add(lblProj);
        form.add(projectBox);
        form.add(new JLabel(""));
        form.add(new JLabel(""));

        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottom.setBackground(Color.WHITE);

        JButton assignBtn = createButton("Assign", new Color(46, 204, 113));
        JButton cancelBtn = createButton("Cancel", new Color(200, 60, 60));

        assignBtn.addActionListener(e -> assign());
        cancelBtn.addActionListener(e -> dispose());

        bottom.add(assignBtn);
        bottom.add(cancelBtn);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void assign() {

        int empIndex = employeeBox.getSelectedIndex();
        int projIndex = projectBox.getSelectedIndex();

        if (empIndex == -1 || projIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select both an employee and a project.");
            return;
        }

        int employeeId = employees.get(empIndex).getId();
        int projectId  = projects.get(projIndex).getId();

        if (projectDAO.isAlreadyAssigned(projectId, employeeId)) {
            JOptionPane.showMessageDialog(this,
                    "This employee is already assigned to that project!",
                    "Already Assigned", JOptionPane.WARNING_MESSAGE);
            return;
        }

        projectDAO.assignProjectToEmployee(projectId, employeeId);
        JOptionPane.showMessageDialog(this, "Assignment successful!");
        dispose();
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(140, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}