package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.Newsletter;

import java.sql.*;

/**
 * NewsletterDAO — Data Access Object for newsletter subscription.
 */
public class NewsletterDAO {

    /**
     * Subscribe an email to the newsletter.
     * @return true if subscribed successfully, false if already subscribed
     */
    public boolean subscribe(String email) {
        String sql = "INSERT INTO newsletter (email) VALUES (?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Email already subscribed
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    /**
     * Check if an email is already subscribed.
     */
    public boolean isSubscribed(String email) {
        String sql = "SELECT COUNT(*) FROM newsletter WHERE email = ?";
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

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
