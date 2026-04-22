package dao; 
import db.DBConnection; 
import model.Project; 
import java.sql.*; 
import java.util.*; 
public class ProjectDAO { 
  public List<Project> getAll() {
    List<Project> list = new ArrayList<>();
    String sql = "SELECT * FROM Projects"; 
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) { 
      while (rs.next()) { 
        list.add(new Project( rs.getInt("pr_id"), rs.getString("title"), rs.getInt("client_id") )); }
    } 
      catch (Exception e) { e.printStackTrace(); } return list; } 
  public List<Project> getByClient(int clientId) { 
    List<Project> list = new ArrayList<>(); 
    String sql = "SELECT * FROM Projects WHERE client_id = ?"; 
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) { 
      ps.setInt(1, clientId);
      ResultSet rs = ps.executeQuery(); 
      while (rs.next()) { 
        list.add(new Project( rs.getInt("pr_id"), rs.getString("title"), rs.getInt("client_id") )); 
      } 
    } 
      catch (Exception e) { e.printStackTrace(); } 
    return list; 
  } 
  public List<Project> getByEmployee(int empId) { 
    List<Project> list = new ArrayList<>(); 
    String sql = "SELECT p.* FROM Projects p " + "JOIN Project_Employees pe ON p.pr_id = pe.pr_id " + "WHERE pe.emp_id = ?"; 
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)
        ) { 
      ps.setInt(1, empId); 
      ResultSet rs = ps.executeQuery(); 
      while (rs.next()) { 
        list.add(new Project( rs.getInt("pr_id"), rs.getString("title"), rs.getInt("client_id") ));
      } 
    } 
      catch (Exception e) { e.printStackTrace();} 
    return list; 
  } 
}
