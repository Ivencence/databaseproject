package dao;

import db.DBConnection;
import model.Employee;

import java.sql.*;
import java.util.*;

public class EmployeeDAO {

    /** Returns all employees with their department name resolved. */
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = """
            SELECT e.*, d.name AS dept_name
            FROM Employees e
            LEFT JOIN Departments d ON e.dept_id = d.dept_id
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Employee getById(int empId) {
        String sql = """
            SELECT e.*, d.name AS dept_name
            FROM Employees e
            LEFT JOIN Departments d ON e.dept_id = d.dept_id
            WHERE e.emp_id = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public Employee getByUserId(int userId) {
        String sql = """
            SELECT e.*, d.name AS dept_name
            FROM Employees e
            LEFT JOIN Departments d ON e.dept_id = d.dept_id
            WHERE e.user_id = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee(
                rs.getInt("emp_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("email"),
                rs.getInt("dept_id")
        );
        try { e.setDeptName(rs.getString("dept_name")); } catch (Exception ignored) {}
        try { e.setPhoto(rs.getBytes("photo"));         } catch (Exception ignored) {}
        return e;
    }
}