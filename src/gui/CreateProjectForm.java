package gui;

import dao.ProjectDAO;
import dao.ClientDAO;
import model.Project;
import model.Client;
import util.Validator;

import javax.swing.*;
import java.awt.*;

public class CreateProjectForm extends JFrame {

    private JTextField txtTitle;
    private JTextField txtLocation;
    private JTextField txtBudget;

    private final int userId;
    private final ProjectDAO projectDAO = new ProjectDAO();
    private final ClientDAO  clientDAO  = new ClientDAO();

    public CreateProjectForm(int userId) {

        this.userId = userId;

        setTitle("Create Project");
        setSize(560, 360);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(25, 25, 50));
        top.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel header = new JLabel("CREATE PROJECT", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        top.add(header);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(28, 50, 28, 50));
        form.setBackground(Color.WHITE);

        txtTitle    = new JTextField();
        txtLocation = new JTextField();
        txtBudget   = new JTextField();

        form.add(createLabel("Project Title *")); form.add(txtTitle);
        form.add(createLabel("Location"));        form.add(txtLocation);
        form.add(createLabel("Budget *"));        form.add(txtBudget);
        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 14));
        bottom.setBackground(Color.WHITE);

        JButton btnCreate = createButton("Create", new Color(46, 204, 113));
        JButton btnCancel = createButton("Cancel", new Color(200, 60, 60));

        btnCreate.addActionListener(e -> createProject());
        btnCancel.addActionListener(e -> dispose());

        bottom.add(btnCreate);
        bottom.add(btnCancel);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createProject() {

        String title    = txtTitle.getText().trim();
        String location = txtLocation.getText().trim();
        String budgetTx = txtBudget.getText().trim();

        if (Validator.isEmpty(title)) {
            JOptionPane.showMessageDialog(this, "Project title is required.");
            txtTitle.requestFocus();
            return;
        }
        if (!Validator.isValidDouble(budgetTx)) {
            JOptionPane.showMessageDialog(this, "Budget must be a valid number.");
            txtBudget.requestFocus();
            return;
        }

        Client client = clientDAO.getByUserId(userId);
        if (client == null) {
            JOptionPane.showMessageDialog(this, "Client account not found.");
            return;
        }

        Project p = new Project();
        p.setTitle(title);
        p.setLocation(location);
        p.setBudget(Double.parseDouble(budgetTx));
        p.setClientId(client.getId());

        projectDAO.insert(p);
        JOptionPane.showMessageDialog(this, "Project created successfully!");
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
        btn.setPreferredSize(new Dimension(150, 44));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}