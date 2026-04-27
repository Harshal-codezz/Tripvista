package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FlightDAO — Data Access Object for flight search operations.
 */
public class FlightDAO {

    /**
     * Search flights by from/to cities.
     */
    public List<Flight> searchFlights(String fromCity, String toCity) {
        String sql = "SELECT * FROM flights WHERE LOWER(from_city) = LOWER(?) AND LOWER(to_city) = LOWER(?) ORDER BY price ASC";
        return executeSearch(sql, fromCity, toCity);
    }

    /**
     * Get all flights.
     */
    public List<Flight> getAllFlights() {
        String sql = "SELECT * FROM flights ORDER BY price ASC";
        List<Flight> flights = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                flights.add(mapFlight(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return flights;
    }

    /**
     * Get a single flight by ID.
     */
    public Flight getFlightById(int id) {
        String sql = "SELECT * FROM flights WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapFlight(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    // Private helper: execute a search with two city parameters
    private List<Flight> executeSearch(String sql, String param1, String param2) {
        List<Flight> flights = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, param1);
            ps.setString(2, param2);
            rs = ps.executeQuery();

            while (rs.next()) {
                flights.add(mapFlight(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return flights;
    }

    // Map ResultSet row to Flight object
    private Flight mapFlight(ResultSet rs) throws SQLException {
        Flight f = new Flight();
        f.setId(rs.getInt("id"));
        f.setAirline(rs.getString("airline"));
        f.setFlightNumber(rs.getString("flight_number"));
        f.setFromCity(rs.getString("from_city"));
        f.setToCity(rs.getString("to_city"));
        f.setFromCode(rs.getString("from_code"));
        f.setToCode(rs.getString("to_code"));
        f.setDepartureTime(rs.getString("departure_time"));
        f.setArrivalTime(rs.getString("arrival_time"));
        f.setDuration(rs.getString("duration"));
        f.setStops(rs.getString("stops"));
        f.setPrice(rs.getDouble("price"));
        f.setAvailableSeats(rs.getInt("available_seats"));
        return f;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
