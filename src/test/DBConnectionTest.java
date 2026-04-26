package test;

import db.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {

    @Test
    void testConnection() {
        Connection conn = DBConnection.getConnection();
        assertNotNull(conn, "Connection should not be null");
    }
}