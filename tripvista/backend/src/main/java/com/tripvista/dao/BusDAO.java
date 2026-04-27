package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.Bus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BusDAO — Data Access Object for bus search operations.
 */
public class BusDAO {

    /**
     * Search buses by from/to cities.
     */
    public List<Bus> searchBuses(String fromCity, String toCity) {
        String sql = "SELECT * FROM buses WHERE LOWER(from_city) = LOWER(?) AND LOWER(to_city) = LOWER(?) ORDER BY departure_time ASC";
        List<Bus> buses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, fromCity);
            ps.setString(2, toCity);
            rs = ps.executeQuery();

            while (rs.next()) {
                buses.add(mapBus(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return buses;
    }

    /**
     * Get all buses.
     */
    public List<Bus> getAllBuses() {
        String sql = "SELECT * FROM buses ORDER BY departure_time ASC";
        List<Bus> buses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                buses.add(mapBus(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return buses;
    }

    /**
     * Get bus by ID.
     */
    public Bus getBusById(int id) {
        String sql = "SELECT * FROM buses WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapBus(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    private Bus mapBus(ResultSet rs) throws SQLException {
        Bus b = new Bus();
        b.setId(rs.getInt("id"));
        b.setOperator(rs.getString("operator"));
        b.setBusType(rs.getString("bus_type"));
        b.setBusTypeCategory(rs.getString("bus_type_category"));
        b.setFromCity(rs.getString("from_city"));
        b.setToCity(rs.getString("to_city"));
        b.setDepartureTime(rs.getString("departure_time"));
        b.setArrivalTime(rs.getString("arrival_time"));
        b.setDuration(rs.getString("duration"));
        b.setPrice(rs.getDouble("price"));
        b.setRating(rs.getDouble("rating"));
        b.setTotalReviews(rs.getInt("total_reviews"));
        b.setAmenities(rs.getString("amenities"));
        b.setSeatsLeft(rs.getInt("seats_left"));
        return b;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
