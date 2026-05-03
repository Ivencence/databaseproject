package gui;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClientTableForm extends JFrame {

    private final ClientDAO dao = new ClientDAO();

    public ClientTableForm(String role, int userId) {

        setTitle(role.equals("ADMIN") ? "Clients" : "My Profile");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createHeader(role.equals("ADMIN") ? "CLIENTS" : "MY PROFILE"),
                BorderLayout.NORTH);

        List<Client> clients;
        if (role.equals("ADMIN")) {
            clients = dao.getAll();
        } else {
            Client c = dao.getByUserId(userId);
            clients = (c == null) ? List.of() : List.of(c);
        }

        if (role.equals("CLIENT") && !clients.isEmpty()) {
            add(buildSingleCard(clients.get(0)), BorderLayout.CENTER);
        } else {
            add(buildCardList(clients, role, userId), BorderLayout.CENTER);
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 14));
        bottom.setBackground(Color.WHITE);

        JButton back = actionButton("Back", new Color(100, 100, 100));
        back.addActionListener(e -> { dispose(); new MainForm(role, "User", userId); });
        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        ClientTableForm.this,
                        "Return to main menu?", "Close",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    new MainForm(role, "User", userId);
                }
            }
        });

        setVisible(true);
    }

    private JPanel buildSingleCard(Client c) {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(460, 320));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel photo = new JLabel(ProjectTableForm.scaledPhoto(c.getPhoto(), 100, 100));
        photo.setAlignmentX(Component.CENTER_ALIGNMENT);
        photo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        card.add(photo);
        card.add(Box.createVerticalStrut(16));
        card.add(sep);
        card.add(Box.createVerticalStrut(14));
        card.add(cardRow("ID",    String.valueOf(c.getId())));
        card.add(Box.createVerticalStrut(8));
        card.add(cardRow("Name",  c.getName()));
        card.add(Box.createVerticalStrut(8));
        card.add(cardRow("Email", c.getEmail()));

        wrapper.add(card);
        return wrapper;
    }

    private JScrollPane buildCardList(List<Client> clients,
                                      String role, int userId) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        if (clients.isEmpty()) {
            JLabel empty = new JLabel("No clients found", SwingConstants.CENTER);
            empty.setFont(new Font("Segoe UI", Font.BOLD, 20));
            empty.setForeground(Color.GRAY);
            container.add(empty);
        }

        for (Client c : clients) {
            container.add(buildAdminCard(c, role, userId));
            container.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);
        return scroll;
    }

    private JPanel buildAdminCard(Client c, String role, int userId) {

        JPanel card = new JPanel(new BorderLayout(14, 0));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel photo = new JLabel(ProjectTableForm.scaledPhoto(c.getPhoto(), 65, 65));
        photo.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(photo, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(3, 1, 3, 3));
        info.setBackground(Color.WHITE);
        info.add(styledLabel("ID: "    + c.getId(),    Font.BOLD,  14));
        info.add(styledLabel("Name: "  + c.getName(),  Font.PLAIN, 14));
        info.add(styledLabel("Email: " + c.getEmail(), Font.PLAIN, 13));
        card.add(info, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(245, 245, 255));
                info.setBackground(new Color(245, 245, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                info.setBackground(Color.WHITE);
            }
        });

        return card;
    }

    private JPanel cardRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setPreferredSize(new Dimension(80, 24));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        return row;
    }

    private JLabel styledLabel(String text, int style, int size) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", style, size));
        return lbl;
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

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(150, 42));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}