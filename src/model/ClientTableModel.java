package model; 
import javax.swing.table.AbstractTableModel; 
import java.util.List; 
public class ClientTableModel extends AbstractTableModel { 
  private List<Client> data; 
  private String[] cols = {"ID", "Name", "Email", "Phone"}; 
  public ClientTableModel(List<Client> data) { this.data = data; }
  public void setData(List<Client> data) { this.data = data; } 
  @Override public int getRowCount() { return data.size(); } 
  @Override public int getColumnCount() { return cols.length; } 
  @Override public Object getValueAt(int row, int col) { 
    Client c = data.get(row); 
    return switch (col) { case 0 -> c.getId(); case 1 -> c.getName(); 
      case 2 -> c.getEmail(); case 3 -> c.getPhone(); default -> null; 
    }; 
  } @Override public String getColumnName(int col) { return cols[col]; } 
}
