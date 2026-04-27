package com.tripvista.dao;

import com.tripvista.config.DBConnection;
import com.tripvista.model.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HotelDAO — Data Access Object for hotel search operations.
 */
public class HotelDAO {

    /**
     * Search hotels by city.
     */
    public List<Hotel> searchHotels(String city) {
        String sql = "SELECT * FROM hotels WHERE LOWER(city) = LOWER(?) ORDER BY rating_score DESC";
        List<Hotel> hotels = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, city);
            rs = ps.executeQuery();

            while (rs.next()) {
                hotels.add(mapHotel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return hotels;
    }

    /**
     * Get all hotels.
     */
    public List<Hotel> getAllHotels() {
        String sql = "SELECT * FROM hotels ORDER BY rating_score DESC";
        List<Hotel> hotels = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                hotels.add(mapHotel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return hotels;
    }

    /**
     * Get hotel by ID.
     */
    public Hotel getHotelById(int id) {
        String sql = "SELECT * FROM hotels WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapHotel(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    private Hotel mapHotel(ResultSet rs) throws SQLException {
        Hotel h = new Hotel();
        h.setId(rs.getInt("id"));
        h.setName(rs.getString("name"));
        h.setCity(rs.getString("city"));
        h.setLocation(rs.getString("location"));
        h.setStarRating(rs.getInt("star_rating"));
        h.setRatingScore(rs.getDouble("rating_score"));
        h.setRatingText(rs.getString("rating_text"));
        h.setPricePerNight(rs.getDouble("price_per_night"));
        h.setOriginalPrice(rs.getDouble("original_price"));
        h.setAmenities(rs.getString("amenities"));
        h.setImageUrl(rs.getString("image_url"));
        return h;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
