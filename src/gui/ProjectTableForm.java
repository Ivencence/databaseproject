package gui;

import dao.ClientDAO;
import dao.EmployeeDAO;
import dao.ProjectDAO;
import model.Client;
import model.Employee;
import model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ProjectTableForm extends JFrame {

    private final ProjectDAO  projectDAO  = new ProjectDAO();
    private final ClientDAO   clientDAO   = new ClientDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public ProjectTableForm(String role, int userId) {

        setTitle(role.equals("ADMIN") ? "Projects" : "My Projects");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createHeader(role.equals("ADMIN") ? "PROJECTS" : "MY PROJECTS"), BorderLayout.NORTH);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        List<Project> projects = loadProjects(role, userId);

        if (projects.isEmpty()) {
            JLabel empty = new JLabel("No projects found", SwingConstants.CENTER);
            empty.setFont(new Font("Segoe UI", Font.BOLD, 20));
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
            container.add(empty);
        }

        for (Project p : projects) {
            container.add(createProjectCard(p, role, userId));
            container.add(Box.createVerticalStrut(12));
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
        add(createBottomPanel(role, userId), BorderLayout.SOUTH);

        setVisible(true);
    }

    private List<Project> loadProjects(String role, int userId) {
        return switch (role) {
            case "ADMIN" -> projectDAO.getAll();
            case "EMPLOYEE" -> {
                Employee e = employeeDAO.getByUserId(userId);
                yield e != null ? projectDAO.getByEmployee(e.getId()) : List.of();
            }
            case "CLIENT" -> {
                Client c = clientDAO.getByUserId(userId);
                yield c != null ? projectDAO.getByClient(c.getId()) : List.of();
            }
            default -> List.of();
        };
    }

    private JPanel createProjectCard(Project p, String role, int userId) {

        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(80, 80));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        photoLabel.setIcon(buildPlaceholderIcon(80, 80));
        card.add(photoLabel, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(4, 1, 2, 2));
        info.setBackground(Color.WHITE);
        info.add(styledLabel("ID: "       + p.getId(),       Font.BOLD,  15));
        info.add(styledLabel("Title: "    + p.getTitle(),    Font.PLAIN, 14));
        info.add(styledLabel("Location: " + (p.getLocation() != null ? p.getLocation() : "—"), Font.PLAIN, 13));
        info.add(styledLabel("Budget: €"  + p.getBudget(),   Font.PLAIN, 13));
        card.add(info, BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(0, 1, 0, 8));
        actions.setBackground(Color.WHITE);

        if (role.equals("CLIENT")) {
            JButton editBtn = actionButton("Edit", new Color(52, 152, 219));
            editBtn.addActionListener(e -> { dispose(); new EditProjectForm(p, userId); });
            actions.add(editBtn);
        }

        if (role.equals("ADMIN")) {
            JButton deleteBtn = actionButton("Delete", new Color(200, 60, 60));
            deleteBtn.addActionListener(e -> deleteProject(p, role, userId));
            actions.add(deleteBtn);
        }

        if (actions.getComponentCount() > 0)
            card.add(actions, BorderLayout.EAST);
        card.add(actions, BorderLayout.EAST);

        return card;
    }

    private void deleteProject(Project p, String role, int userId) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete project \"" + p.getTitle() + "\"?\nThis cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            projectDAO.delete(p.getId());
            JOptionPane.showMessageDialog(this, "Project deleted successfully.");
            dispose();
            new ProjectTableForm(role, userId);
        }
    }

    private JPanel createBottomPanel(String role, int userId) {
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 14));
        bottom.setBackground(Color.WHITE);

        JButton back = actionButton("Back", new Color(100, 100, 100));
        back.addActionListener(e -> { dispose(); new MainForm(role, "User", userId); });
        bottom.add(back);

        if (role.equals("CLIENT")) {
            JButton createBtn = actionButton("Create Project", new Color(46, 204, 113));
            createBtn.addActionListener(e -> { dispose(); new CreateProjectForm(userId); });
            bottom.add(createBtn);
        }

        return bottom;
    }

    public static ImageIcon scaledPhoto(byte[] data, int w, int h) {
        if (data == null || data.length == 0) return buildPlaceholderIcon(w, h);
        try {
            ImageIcon raw = new ImageIcon(data);
            Image scaled  = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return buildPlaceholderIcon(w, h);
        }
    }

    private static ImageIcon buildPlaceholderIcon(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(220, 220, 220));
        g.fillRect(0, 0, w, h);
        g.setColor(new Color(160, 160, 160));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        FontMetrics fm = g.getFontMetrics();
        String txt = "No Photo";
        g.drawString(txt, (w - fm.stringWidth(txt)) / 2, h / 2 + fm.getAscent() / 2);
        g.dispose();
        return new ImageIcon(img);
    }

    private JLabel styledLabel(String text, int style, int size) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", style, size));
        return lbl;
    }

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 36));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private JPanel createHeader(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(25, 25, 50));
        p.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 34));
        p.add(lbl);
        return p;
    }
}