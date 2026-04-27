package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TrainDAO — Data Access Object for train search operations.
 */
public class TrainDAO {

    /**
     * Search trains by from/to stations.
     */
    public List<Train> searchTrains(String fromStation, String toStation) {
        String sql = "SELECT * FROM trains WHERE (LOWER(from_station) LIKE LOWER(?) OR LOWER(from_code) = LOWER(?)) " +
                     "AND (LOWER(to_station) LIKE LOWER(?) OR LOWER(to_code) = LOWER(?)) ORDER BY departure_time ASC";
        List<Train> trains = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + fromStation + "%");
            ps.setString(2, fromStation);
            ps.setString(3, "%" + toStation + "%");
            ps.setString(4, toStation);
            rs = ps.executeQuery();

            while (rs.next()) {
                trains.add(mapTrain(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return trains;
    }

    /**
     * Get all trains.
     */
    public List<Train> getAllTrains() {
        String sql = "SELECT * FROM trains ORDER BY departure_time ASC";
        List<Train> trains = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                trains.add(mapTrain(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return trains;
    }

    /**
     * Get train by ID.
     */
    public Train getTrainById(int id) {
        String sql = "SELECT * FROM trains WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapTrain(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    private Train mapTrain(ResultSet rs) throws SQLException {
        Train t = new Train();
        t.setId(rs.getInt("id"));
        t.setTrainName(rs.getString("train_name"));
        t.setTrainNumber(rs.getString("train_number"));
        t.setTrainType(rs.getString("train_type"));
        t.setFromStation(rs.getString("from_station"));
        t.setToStation(rs.getString("to_station"));
        t.setFromCode(rs.getString("from_code"));
        t.setToCode(rs.getString("to_code"));
        t.setDepartureTime(rs.getString("departure_time"));
        t.setArrivalTime(rs.getString("arrival_time"));
        t.setDuration(rs.getString("duration"));
        t.setStops(rs.getInt("stops"));
        t.setRunsOn(rs.getString("runs_on"));

        // Handle nullable price fields
        double sl = rs.getDouble("price_sleeper");
        t.setPriceSleeper(rs.wasNull() ? null : sl);
        double p3a = rs.getDouble("price_3a");
        t.setPrice3A(rs.wasNull() ? null : p3a);
        double p2a = rs.getDouble("price_2a");
        t.setPrice2A(rs.wasNull() ? null : p2a);
        double p1a = rs.getDouble("price_1a");
        t.setPrice1A(rs.wasNull() ? null : p1a);

        t.setAvailableSeats(rs.getInt("available_seats"));
        return t;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
