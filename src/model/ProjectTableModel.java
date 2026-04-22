package model; 
import javax.swing.table.AbstractTableModel; 
import java.util.ArrayList; 
import java.util.List; 
public class ProjectTableModel extends AbstractTableModel { 
  private List<Project> data = new ArrayList<>(); 
  private final String[] cols = {"ID", "Title", "Client ID"}; 
  public ProjectTableModel(List<Project> data) { 
    if (data != null) { this.data = data; } 
  } 
  public void setData(List<Project> data) { 
    this.data = (data != null) ? data : new ArrayList<>(); 
    fireTableDataChanged(); 
  } 
  @Override public int getRowCount() { return data != null ? data.size() : 0; } 
  @Override public int getColumnCount() { return cols.length; } 
  @Override public Object getValueAt(int row, int col) {
    if (data == null || data.isEmpty()) 
      return null;
    Project p = data.get(row); 
    return switch (col) { 
      case 0 -> p.getId(); 
      case 1 -> p.getTitle(); 
      case 2 -> p.getClientId(); 
      default -> null; 
    }; 
  } 
  @Override public String getColumnName(int col) { return cols[col]; } 
}
