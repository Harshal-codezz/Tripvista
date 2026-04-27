package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.HotelDAO;
import com.tripvista.model.Hotel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * HotelSearchServlet — Handles GET /api/hotels?city=Goa
 *
 * Query Params: city
 * If no params, returns all hotels.
 */
public class HotelSearchServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final HotelDAO hotelDAO = new HotelDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String city = request.getParameter("city");

            List<Hotel> hotels;

            if (city != null && !city.isEmpty()) {
                hotels = hotelDAO.searchHotels(city);
            } else {
                hotels = hotelDAO.getAllHotels();
            }

            JsonObject res = new JsonObject();
            res.addProperty("success", true);
            res.addProperty("count", hotels.size());
            res.add("hotels", gson.toJsonTree(hotels));
            out.print(gson.toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Error fetching hotels: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }
}
