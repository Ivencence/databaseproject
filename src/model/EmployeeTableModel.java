package model; 
import javax.swing.table.AbstractTableModel; 
import java.util.List; 
public class EmployeeTableModel extends AbstractTableModel { 
  private List<Employee> data; 
  private String[] cols = {"ID", "Name", "Age", "Email"}; 
  public EmployeeTableModel(List<Employee> data) { this.data = data; } 
  public void setData(List<Employee> data) { this.data = data; } 
  @Override public int getRowCount() { return data.size(); } 
  @Override public int getColumnCount() { return cols.length; } 
  @Override public Object getValueAt(int row, int col) { 
    Employee e = data.get(row);
    return switch (col) { 
      case 0 -> e.getId(); 
      case 1 -> e.getName(); 
      case 2 -> e.getAge(); 
      case 3 -> e.getEmail(); 
      default -> null; 
    }; 
  } 
  @Override public String getColumnName(int col) { return cols[col]; } 
}
