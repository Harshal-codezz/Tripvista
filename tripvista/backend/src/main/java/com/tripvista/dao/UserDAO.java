package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.User;

import java.sql.*;

/**
 * UserDAO — Data Access Object for user authentication and registration.
 */
public class UserDAO {

    /**
     * Register a new user.
     * @return true if registration successful, false if email already exists
     */
    public boolean register(User user) {
        String sql = "INSERT INTO users (full_name, email, password, phone) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Email already exists
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    /**
     * Authenticate a user by email and password.
     * @return User object if credentials are valid, null otherwise
     */
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCreatedAt(rs.getString("created_at"));
                // Don't return password
                return user;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(conn, ps, rs);
        }
    }

    /**
     * Get user by ID.
     */
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCreatedAt(rs.getString("created_at"));
                return user;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(conn, ps, rs);
        }
    }

    /**
     * Check if an email is already registered.
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, rs);
        }
    }

    // Helper method to close resources
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
