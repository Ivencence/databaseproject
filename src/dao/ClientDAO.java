package dao; 
import db.DBConnection; 
import model.Client; 
import java.sql.*; 
import java.util.*; 
public class ClientDAO { 
  public List<Client> getAll() { 
    List<Client> list = new ArrayList<>(); 
    String sql = "SELECT * FROM Clients"; 
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) { 
        list.add(new Client( rs.getInt("client_id"), rs.getString("name"), rs.getString("email") )); 
      } 
    } 
      catch (Exception e) { e.printStackTrace(); } 
    return list; 
  } 
  public Client getById(int id) { 
    String sql = "SELECT * FROM Clients WHERE client_id = ?"; 
    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) { 
      ps.setInt(1, id); 
      ResultSet rs = ps.executeQuery(); 
      if (rs.next()) {
        return new Client( rs.getInt("client_id"), rs.getString("name"), rs.getString("email") ); 
      } 
    } 
      catch (Exception e) { e.printStackTrace(); } 
    return null; 
  } 
}
