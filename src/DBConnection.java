import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/architecture_db",
                    "root",
                    "My$ql_$erveR@2024"
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}