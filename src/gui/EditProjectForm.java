package gui;

import dao.ProjectDAO;
import model.Project;

import javax.swing.*;
import java.awt.*;

public class EditProjectForm extends JFrame {

    private JTextField txtTitle;
    private JTextField txtLocation;
    private JTextField txtBudget;

    private Project project;
    private int userId;
    private ProjectDAO projectDAO = new ProjectDAO();

    public EditProjectForm(Project project, int userId) {

        this.project = project;
        this.userId = userId;

        setTitle("Edit Project");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(25, 25, 50));
        top.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel header = new JLabel("EDIT PROJECT", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 30));
        top.add(header, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        form.setBackground(Color.WHITE);

        txtTitle    = new JTextField(project.getTitle());
        txtLocation = new JTextField(project.getLocation() != null ? project.getLocation() : "");
        txtBudget   = new JTextField(String.valueOf(project.getBudget()));

        form.add(createLabel("Project Title:"));
        form.add(txtTitle);

        form.add(createLabel("Location:"));
        form.add(txtLocation);

        form.add(createLabel("Budget:"));
        form.add(txtBudget);

        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottom.setBackground(Color.WHITE);

        JButton btnSave   = createButton("Save Changes", new Color(46, 204, 113));
        JButton btnCancel = createButton("Cancel",       new Color(200, 60, 60));

        btnSave.addActionListener(e -> saveChanges());
        btnCancel.addActionListener(e -> {
            dispose();
            new ProjectTableForm("CLIENT", userId);
        });

        bottom.add(btnSave);
        bottom.add(btnCancel);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void saveChanges() {

        String title = txtTitle.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty!");
            return;
        }

        project.setTitle(title);
        project.setLocation(txtLocation.getText().trim());

        try {
            double budget = Double.parseDouble(txtBudget.getText().trim());
            project.setBudget(budget);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Budget must be a valid number!");
            return;
        }

        projectDAO.update(project);

        JOptionPane.showMessageDialog(this, "Project updated successfully!");
        dispose();
        new ProjectTableForm("CLIENT", userId);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return lbl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}