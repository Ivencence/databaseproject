package gui;

import dao.EmployeeDAO;
import model.Employee;
import model.EmployeeTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableForm extends JFrame {

    private final EmployeeDAO dao = new EmployeeDAO();
    private JTable table;
    private List<Employee> employees;

    private final JLabel photoLabel  = new JLabel();
    private final JLabel detailName  = new JLabel(" ");
    private final JLabel detailEmail = new JLabel(" ");
    private final JLabel detailDept  = new JLabel(" ");
    private final JLabel detailAge   = new JLabel(" ");
    private final JLabel statusBar   = new JLabel("Ready");

    public EmployeeTableForm(String role, int userId) {

        setTitle(role.equals("EMPLOYEE") ? "My Account" : "Employees");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createHeader(role.equals("EMPLOYEE") ? "MY ACCOUNT" : "EMPLOYEES"),
                BorderLayout.NORTH);

        employees = new ArrayList<>();

        if (role.equals("ADMIN")) {
            employees = dao.getAll();
            add(buildAdminView(role, userId), BorderLayout.CENTER);
        } else {
            Employee me = dao.getByUserId(userId);
            if (me != null) employees.add(me);
            add(buildEmployeeCard(me), BorderLayout.CENTER);
        }

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buildBottomPanel(role, userId), BorderLayout.CENTER);
        southPanel.add(statusBar, BorderLayout.SOUTH);
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 12, 3, 12));
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.setForeground(Color.GRAY);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        EmployeeTableForm.this,
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


    private JPanel buildEmployeeCard(Employee e) {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        if (e == null) {
            wrapper.add(new JLabel("No employee data found."));
            return wrapper;
        }

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(500, 400));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel photo = new JLabel(ProjectTableForm.scaledPhoto(e.getPhoto(), 100, 100));
        photo.setAlignmentX(Component.CENTER_ALIGNMENT);
        photo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        card.add(photo);
        card.add(Box.createVerticalStrut(16));
        card.add(sep);
        card.add(Box.createVerticalStrut(14));
        card.add(cardRow("Name",       e.getName()));
        card.add(Box.createVerticalStrut(8));
        card.add(cardRow("Email",      e.getEmail()));
        card.add(Box.createVerticalStrut(8));
        card.add(cardRow("Department", e.getDeptName()));
        card.add(Box.createVerticalStrut(8));
        card.add(cardRow("Age",        String.valueOf(e.getAge())));

        wrapper.add(card);
        return wrapper;
    }

    private JPanel cardRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setPreferredSize(new Dimension(110, 24));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        return row;
    }

    private JSplitPane buildAdminView(String role, int userId) {

        table = new JTable(new EmployeeTableModel(employees));
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) updateDetailPanel();
                    }
                });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE)
                    deleteSelected(role, userId);
                statusBar.setText("Key: " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) updateDetailPanel();
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(row, row);
                    showContextMenu(e.getComponent(), e.getX(), e.getY(), role, userId);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                statusBar.setText("Double-click a row to see details · Right-click for options");
            }
            @Override
            public void mouseExited(MouseEvent e) { statusBar.setText("Ready"); }
        });

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(table),
                buildDetailPanel()
        );
        split.setResizeWeight(0.70);
        split.setBorder(null);
        return split;
    }

    private JPanel buildDetailPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        photoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        photoLabel.setIcon(ProjectTableForm.scaledPhoto(null, 110, 110));
        photoLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        photoLabel.setMaximumSize(new Dimension(110, 110));

        JLabel title = new JLabel("Employee Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        for (JLabel lbl : new JLabel[]{detailName, detailEmail, detailDept, detailAge}) {
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        }

        panel.add(photoLabel);
        panel.add(title);
        panel.add(sep);
        panel.add(Box.createVerticalStrut(10));
        panel.add(detailName);
        panel.add(detailEmail);
        panel.add(detailDept);
        panel.add(detailAge);
        return panel;
    }

    private void updateDetailPanel() {
        int row = table.getSelectedRow();
        if (row == -1 || row >= employees.size()) return;
        Employee e = employees.get(row);
        detailName.setText("Name: "       + e.getName());
        detailEmail.setText("Email: "     + e.getEmail());
        detailDept.setText("Department: " + e.getDeptName());
        detailAge.setText("Age: "         + e.getAge());
        photoLabel.setIcon(ProjectTableForm.scaledPhoto(e.getPhoto(), 110, 110));
    }


    private void deleteSelected(String role, int userId) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee first.");
            return;
        }
        Employee e = employees.get(row);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + e.getName() + "\"? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            try (var conn = db.DBConnection.getConnection();
                 var ps = conn.prepareStatement(
                         "DELETE FROM Employees WHERE emp_id = ?")) {
                ps.setInt(1, e.getId());
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
            employees.remove(row);
            ((EmployeeTableModel) table.getModel()).setData(employees);
            detailName.setText(" "); detailEmail.setText(" ");
            detailDept.setText(" "); detailAge.setText(" ");
            photoLabel.setIcon(ProjectTableForm.scaledPhoto(null, 110, 110));
            statusBar.setText("Employee deleted.");
        }
    }

    private void showContextMenu(Component comp, int x, int y,
                                 String role, int userId) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem viewItem   = new JMenuItem("View Details");
        JMenuItem deleteItem = new JMenuItem("Delete");
        viewItem.addActionListener(e -> updateDetailPanel());
        deleteItem.addActionListener(e -> deleteSelected(role, userId));
        menu.add(viewItem);
        menu.addSeparator();
        menu.add(deleteItem);
        menu.show(comp, x, y);
    }

    private JPanel buildBottomPanel(String role, int userId) {
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        bottom.setBackground(Color.WHITE);

        JButton btnBack = actionButton("Back", new Color(100, 100, 100));
        btnBack.addActionListener(e -> { dispose(); new MainForm(role, "User", userId); });
        bottom.add(btnBack);

        if (role.equals("ADMIN")) {
            JButton btnDelete = actionButton("Delete Selected", new Color(200, 60, 60));
            btnDelete.addActionListener(e -> deleteSelected(role, userId));
            bottom.add(btnDelete);
        }
        return bottom;
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
        btn.setPreferredSize(new Dimension(170, 42));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}