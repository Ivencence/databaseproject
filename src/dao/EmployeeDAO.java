package dao; 
import db.DBConnection; 
import model.Employee; 
import java.sql.*; 
import java.util.*; 
public class EmployeeDAO { 
  public List<Employee> getAll() {
    List<Employee> list = new ArrayList<>(); 
    String sql = "SELECT * FROM Employees"; 
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) { 
      while (rs.next()) { 
        list.add(new Employee( rs.getInt("emp_id"), rs.getString("name"), rs.getInt("age"), rs.getString("email"), rs.getInt("dept_id") )); 
      } 
    } 
      catch (Exception e) { 
        e.printStackTrace(); 
      } 
    return list; 
  } 
  public Employee getById(int empId) { 
    String sql = "SELECT * FROM Employees WHERE emp_id = ?"; 
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) { 
      ps.setInt(1, empId);
      ResultSet rs = ps.executeQuery(); 
      if (rs.next()) { 
        return new Employee( rs.getInt("emp_id"), rs.getString("name"), rs.getInt("age"), rs.getString("email"), rs.getInt("dept_id") ); 
      } 
    } 
      catch (Exception e) { e.printStackTrace(); } 
    return null; 
  } 
}
