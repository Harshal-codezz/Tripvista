package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.BookingDAO;
import com.tripvista.model.Booking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * BookingServlet — Handles booking creation and retrieval
 *
 * POST /api/bookings — Create a new booking (requires login)
 * GET  /api/bookings — Get bookings for logged-in user
 */
public class BookingServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * GET — Retrieve bookings for the logged-in user.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Please login to view bookings.");
                out.print(gson.toJson(res));
                return;
            }

            int userId = (int) session.getAttribute("userId");
            List<Booking> bookings = bookingDAO.getBookingsByUser(userId);

            JsonObject res = new JsonObject();
            res.addProperty("success", true);
            res.addProperty("count", bookings.size());
            res.add("bookings", gson.toJsonTree(bookings));
            out.print(gson.toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Error fetching bookings: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }

    /**
     * POST — Create a new booking.
     * Request Body (JSON): { "bookingType": "flight", "referenceId": 1, "travelDate": "2026-03-25", "passengers": 1, "totalPrice": 3249.00 }
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Please login to make a booking.");
                out.print(gson.toJson(res));
                return;
            }

            int userId = (int) session.getAttribute("userId");

            // Read JSON body
            BufferedReader reader = request.getReader();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            String bookingType = json.has("bookingType") ? json.get("bookingType").getAsString() : "";
            int referenceId = json.has("referenceId") ? json.get("referenceId").getAsInt() : 0;
            String travelDate = json.has("travelDate") ? json.get("travelDate").getAsString() : "";
            int passengers = json.has("passengers") ? json.get("passengers").getAsInt() : 1;
            double totalPrice = json.has("totalPrice") ? json.get("totalPrice").getAsDouble() : 0;

            // Validate
            if (bookingType.isEmpty() || referenceId == 0 || travelDate.isEmpty() || totalPrice <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Invalid booking data. All fields are required.");
                out.print(gson.toJson(res));
                return;
            }

            // Create booking
            Booking booking = new Booking(userId, bookingType, referenceId, travelDate, passengers, totalPrice);
            int bookingId = bookingDAO.createBooking(booking);

            if (bookingId > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                JsonObject res = new JsonObject();
                res.addProperty("success", true);
                res.addProperty("message", "Booking confirmed successfully!");
                res.addProperty("bookingId", bookingId);
                out.print(gson.toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Booking failed. Please try again.");
                out.print(gson.toJson(res));
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Server error: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }
}
