package dao;

import db.DBConnection;
import model.Department;

import java.sql.*;
import java.util.*;

public class DepartmentDAO implements CRUD<Department> {

    @Override
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM Departments";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Department getById(int id) {
        String sql = "SELECT * FROM Departments WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void insert(Department d) {
        String sql = "INSERT INTO Departments (name, location) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getLocation() != null ? d.getLocation() : "");
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void update(Department d) {
        String sql = "UPDATE Departments SET name=?, location=? WHERE dept_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getLocation() != null ? d.getLocation() : "");
            ps.setInt(3, d.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Departments WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Department mapRow(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("dept_id"),
                rs.getString("name"),
                rs.getString("location")
        );
    }
}