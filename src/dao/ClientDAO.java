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
package dao;

import db.DBConnection;
import model.Client;

import java.sql.*;
import java.util.*;

public class ClientDAO implements CRUD<Client> {

    public List<Client> getAll() {

        List<Client> list = new ArrayList<>();

        String sql = "SELECT * FROM Clients";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Client getById(int id) {

        String sql = "SELECT * FROM Clients WHERE client_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insert(Client c) throws Exception {
        String sql = "INSERT INTO Clients VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getEmail());

            ps.executeUpdate();
        }
    }

    public void update(Client c) throws Exception {
        String sql = "UPDATE Clients SET name=?, email=? WHERE client_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setInt(3, c.getId());

            ps.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Clients WHERE client_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
