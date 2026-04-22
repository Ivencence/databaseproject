package dao; 
import db.DBConnection; 
import java.sql.*; 
public class UserDAO { 
  public boolean login(String username, String password) { 
    String sql = "SELECT * FROM users WHERE username=? AND password=?"; 
    try(Connection conn = DBConnection.getConnection(); 
        PreparedStatement ps = conn.prepareStatement(sql)){ 
      ps.setString(1, username); 
      ps.setString(2, password); 
      ResultSet rs = ps.executeQuery(); 
      return rs.next(); 
    }
      catch(Exception e){ e.printStackTrace(); } 
    return false; 
  } 
}
