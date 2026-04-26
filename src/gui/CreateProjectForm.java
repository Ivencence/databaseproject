package gui;

import dao.ProjectDAO;
import dao.ClientDAO;
import model.Project;
import model.Client;

import javax.swing.*;
import java.awt.*;

public class CreateProjectForm extends JFrame {

    private JTextField txtTitle;
    private JTextField txtLocation;
    private JTextField txtBudget;

    private int userId;

    private ProjectDAO projectDAO = new ProjectDAO();
    private ClientDAO clientDAO = new ClientDAO();

    public CreateProjectForm(int userId) {

        this.userId = userId;

        setTitle("Create Project");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(25, 25, 50));

        JLabel title = new JLabel("CREATE PROJECT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));

        top.add(title, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(4, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        form.setBackground(Color.WHITE);

        txtTitle = new JTextField();
        txtLocation = new JTextField();
        txtBudget = new JTextField();

        form.add(createLabel("Project Title:"));
        form.add(txtTitle);

        form.add(createLabel("Location:"));
        form.add(txtLocation);

        form.add(createLabel("Budget:"));
        form.add(txtBudget);

        form.add(new JLabel(""));
        form.add(new JLabel(""));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottom.setBackground(Color.WHITE);

        JButton btnCreate = createButton("Create", new Color(46, 204, 113));
        JButton btnCancel = createButton("Cancel", new Color(200, 60, 60));

        btnCreate.addActionListener(e -> createProject());
        btnCancel.addActionListener(e -> dispose());

        bottom.add(btnCreate);
        bottom.add(btnCancel);

        add(top, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createProject() {

        if (txtTitle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required!");
            return;
        }

        Client client = clientDAO.getByUserId(userId);

        if (client == null) {
            JOptionPane.showMessageDialog(this, "Client not found!");
            return;
        }

        Project p = new Project();
        p.setTitle(txtTitle.getText());
        p.setClientId(client.getId());

        try {
            double budget = Double.parseDouble(txtBudget.getText());
        } catch (Exception ignored) {}


        projectDAO.insert(p);

        JOptionPane.showMessageDialog(this, "Project created successfully!");

        dispose();
        new ProjectTableForm("CLIENT", userId);
    }
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return lbl;
    }

    private JButton createButton(String text, Color bg) {

        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        return btn;
    }
}
