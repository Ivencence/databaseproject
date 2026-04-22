package dao;

import db.DBConnection;

import java.security.MessageDigest;
import java.sql.*;

public class UserDAO {

    public boolean login(String username, String password) {

        String sql = "SELECT password FROM Users WHERE username=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dbPass = rs.getString("password");
                return dbPass.equals(hash(password));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String hash(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));

            return sb.toString();

        } catch (Exception e) {
            return password;
        }
    }
}
