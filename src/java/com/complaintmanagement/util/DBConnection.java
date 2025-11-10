package com.complaintmanagement.util;
import java.sql.*;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/complaintdb",
                    "root",
                    "veerendra"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
