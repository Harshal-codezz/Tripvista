package com.tripvista.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility
 * Provides a singleton-style method to get MySQL JDBC connections.
 *
 * IMPORTANT: Update the DB_USER and DB_PASSWORD below to match your MySQL setup.
 */
public class DBConnection {

    // ---- MySQL Configuration ----
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tripvista_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";  // Change this to your MySQL password

    // Load MySQL JDBC Driver once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    /**
     * Get a new database connection.
     * Each call returns a new connection — caller is responsible for closing it.
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Close a connection safely (null-safe).
     *
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
