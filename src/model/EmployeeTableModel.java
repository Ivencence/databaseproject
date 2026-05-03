package model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {

    private List<Employee> data;
    private final String[] cols = {"ID", "Name", "Age", "Email", "Department"};

    public EmployeeTableModel(List<Employee> data) { this.data = data; }

    public void setData(List<Employee> data) {
        this.data = data;
        fireTableDataChanged();
    }

    @Override public int getRowCount()    { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int col) { return cols[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Employee e = data.get(row);
        return switch (col) {
            case 0 -> e.getId();
            case 1 -> e.getName();
            case 2 -> e.getAge();
            case 3 -> e.getEmail();
            case 4 -> e.getDeptName();
            default -> null;
        };
    }
}