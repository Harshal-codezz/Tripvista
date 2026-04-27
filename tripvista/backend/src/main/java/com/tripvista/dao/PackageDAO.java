package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.HolidayPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageDAO — Data Access Object for holiday package search operations.
 */
public class PackageDAO {

    /**
     * Search packages by destination.
     */
    public List<HolidayPackage> searchPackages(String destination) {
        String sql = "SELECT * FROM packages WHERE LOWER(destination) LIKE LOWER(?) ORDER BY rating DESC";
        List<HolidayPackage> packages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + destination + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                packages.add(mapPackage(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return packages;
    }

    /**
     * Get all packages.
     */
    public List<HolidayPackage> getAllPackages() {
        String sql = "SELECT * FROM packages ORDER BY rating DESC";
        List<HolidayPackage> packages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                packages.add(mapPackage(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return packages;
    }

    /**
     * Get package by ID.
     */
    public HolidayPackage getPackageById(int id) {
        String sql = "SELECT * FROM packages WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapPackage(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    private HolidayPackage mapPackage(ResultSet rs) throws SQLException {
        HolidayPackage p = new HolidayPackage();
        p.setId(rs.getInt("id"));
        p.setDestination(rs.getString("destination"));
        p.setTitle(rs.getString("title"));
        p.setDurationNights(rs.getInt("duration_nights"));
        p.setDurationDays(rs.getInt("duration_days"));
        p.setPricePerPerson(rs.getDouble("price_per_person"));
        p.setOriginalPrice(rs.getDouble("original_price"));
        p.setIncludes(rs.getString("includes"));
        p.setImageUrl(rs.getString("image_url"));
        p.setRating(rs.getDouble("rating"));
        return p;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
