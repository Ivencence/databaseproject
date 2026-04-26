package gui;

import dao.ProjectDAO;
import dao.EmployeeDAO;
import dao.ClientDAO;
import model.Project;
import model.Employee;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProjectTableForm extends JFrame {

    private ProjectDAO dao = new ProjectDAO();

    public ProjectTableForm(String role, int userId) {

        setTitle(role.equals("ADMIN") ? "Projects" : "My Projects");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        JPanel top = createHeader(
                role.equals("ADMIN") ? "PROJECTS" : "MY PROJECTS"
        );

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        List<Project> projects = java.util.Collections.emptyList();

        if (role.equals("ADMIN")) {
            projects = dao.getAll();
        }

        else if (role.equals("EMPLOYEE")) {

            Employee e = new EmployeeDAO().getByUserId(userId);

            if (e != null) {
                projects = dao.getByEmployee(e.getId());
            }
        }

        else if (role.equals("CLIENT")) {

            Client c = new ClientDAO().getByUserId(userId);

            if (c != null) {
                projects = dao.getByClient(c.getId());
            }
        }

        if (projects.isEmpty()) {
            JLabel empty = new JLabel("No projects found", SwingConstants.CENTER);
            empty.setFont(new Font("Segoe UI", Font.BOLD, 20));
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
            container.add(empty);
        }

        for (Project p : projects) {
            container.add(createCard(
                    "ID: " + p.getId(),
                    "Title: " + p.getTitle(),
                    "Client ID: " + p.getClientId()
            ));
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottom.setBackground(Color.WHITE);

        JButton back = createButton("Back", new Color(200, 60, 60));

        back.addActionListener(e -> {
            dispose();
            new MainForm(role, "User", userId);
        });

        bottom.add(back);

        if (role.equals("CLIENT")) {

            JButton createBtn = createButton("Create Project", new Color(46, 204, 113));

            createBtn.addActionListener(e -> {
                new CreateProjectForm(userId);
                dispose();
            });

            bottom.add(createBtn);
        }

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

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

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

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

    private JButton createButton(String text, Color bg) {

        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        return btn;
    }
}