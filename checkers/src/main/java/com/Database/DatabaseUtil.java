package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String url = "jdbc:mysql://localhost:3306/checkersDB";
    private static final String usr = "root";
    private static final String pass = System.getenv("CHECKERS_DB_PASS");

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, usr, pass);
    }
    
    public static void main(String[] args) {
        String sql = "INSERT INTO Player VALUES (1, 'Guest', 'password')";
        
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();) {
            int ret = stmt.executeUpdate(sql);
            System.out.println("Return value is : " + ret);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}