package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingDAO — Data Access Object for booking CRUD operations.
 */
public class BookingDAO {

    /**
     * Create a new booking.
     * @return the generated booking ID, or -1 on failure
     */
    public int createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, booking_type, reference_id, travel_date, passengers, total_price, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, booking.getUserId());
            ps.setString(2, booking.getBookingType());
            ps.setInt(3, booking.getReferenceId());
            ps.setString(4, booking.getTravelDate());
            ps.setInt(5, booking.getPassengers());
            ps.setDouble(6, booking.getTotalPrice());
            ps.setString(7, booking.getStatus() != null ? booking.getStatus() : "confirmed");

            int rows = ps.executeUpdate();
            if (rows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeResources(conn, ps, rs);
        }
    }

    /**
     * Get all bookings for a specific user.
     */
    public List<Booking> getBookingsByUser(int userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_date DESC";
        List<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(mapBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return bookings;
    }

    /**
     * Cancel a booking (set status to 'cancelled').
     * @return true if cancellation successful
     */
    public boolean cancelBooking(int bookingId, int userId) {
        String sql = "UPDATE bookings SET status = 'cancelled' WHERE id = ? AND user_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    /**
     * Get a single booking by ID.
     */
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapBooking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    private Booking mapBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setBookingType(rs.getString("booking_type"));
        b.setReferenceId(rs.getInt("reference_id"));
        b.setBookingDate(rs.getString("booking_date"));
        b.setTravelDate(rs.getString("travel_date"));
        b.setPassengers(rs.getInt("passengers"));
        b.setTotalPrice(rs.getDouble("total_price"));
        b.setStatus(rs.getString("status"));
        return b;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
