package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection getConnection() {
        Connection conn = null;

        String url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/P1-ERS";
        String username = System.getenv("AWS_RDS_USERNAME");
        String password = System.getenv("AWS_RDS_PASSWORD");

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conn;
    }
}