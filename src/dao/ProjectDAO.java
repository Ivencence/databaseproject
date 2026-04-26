package dao;

import db.DBConnection;
import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public List<Project> getAll() {

        List<Project> list = new ArrayList<>();

        String sql = "SELECT * FROM Projects";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Project p = new Project();
                p.setId(rs.getInt("pr_id"));
                p.setTitle(rs.getString("title"));
                p.setClientId(rs.getInt("client_id"));

                // optional fields (safe even if null in DB)
                try { p.setLocation(rs.getString("location")); } catch (Exception ignored) {}
                try { p.setBudget(rs.getDouble("budget")); } catch (Exception ignored) {}

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Project> getByEmployee(int empId) {

        List<Project> list = new ArrayList<>();

        String sql =
                "SELECT p.* FROM Projects p " +
                        "JOIN Project_Employees pe ON p.pr_id = pe.pr_id " +
                        "WHERE pe.emp_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, empId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Project p = new Project();
                p.setId(rs.getInt("pr_id"));
                p.setTitle(rs.getString("title"));
                p.setClientId(rs.getInt("client_id"));

                try { p.setLocation(rs.getString("location")); } catch (Exception ignored) {}
                try { p.setBudget(rs.getDouble("budget")); } catch (Exception ignored) {}

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Project> getByClient(int clientId) {

        List<Project> list = new ArrayList<>();

        String sql = "SELECT * FROM Projects WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Project p = new Project();
                p.setId(rs.getInt("pr_id"));
                p.setTitle(rs.getString("title"));
                p.setClientId(rs.getInt("client_id"));

                try { p.setLocation(rs.getString("location")); } catch (Exception ignored) {}
                try { p.setBudget(rs.getDouble("budget")); } catch (Exception ignored) {}

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public void insert(Project p) {

        String sql = "INSERT INTO Projects (title, client_id, location, budget) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTitle());
            ps.setInt(2, p.getClientId());

            ps.setString(3, p.getLocation() != null ? p.getLocation() : "");
            ps.setDouble(4, p.getBudget());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignProjectToEmployee(int projectId, int employeeId) {

        String sql = "INSERT INTO Project_Employees (pr_id, emp_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}