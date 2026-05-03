package gui;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.awt.*;

public class ClientForm extends JFrame {

    private ClientDAO dao = new ClientDAO();

    public ClientForm(String role, int userId) {

        setTitle("My Profile");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel top = createHeader("MY PROFILE");

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        Client client = dao.getByUserId(userId);

        if (client != null) {

            JPanel card = createCard(
                    "ID: " + client.getId(),
                    "Name: " + client.getName(),
                    "Email: " + client.getEmail()
            );

            JButton edit = createButton("Edit Profile", new Color(52, 152, 219));

            edit.setAlignmentX(Component.CENTER_ALIGNMENT);
            edit.setMaximumSize(new Dimension(200, 45));

            edit.addActionListener(e ->
                    JOptionPane.showMessageDialog(this, "Edit functionality coming soon!")
            );

            card.add(Box.createVerticalStrut(15));
            card.add(edit);

            container.add(card);
        }

        else {
            JLabel empty = new JLabel("No client data found", SwingConstants.CENTER);
            empty.setFont(new Font("Segoe UI", Font.BOLD, 20));
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
            container.add(empty);
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);

        JButton back = createButton("Back", new Color(200, 60, 60));
        back.addActionListener(e -> {
            dispose();
            new MainForm(role, "User", userId);
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(back);

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
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        for (String line : lines) {
            JLabel lbl = new JLabel(line);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(lbl);
        }

        return card;
    }

    private JButton createButton(String text, Color bg) {

        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        return btn;
    }
}