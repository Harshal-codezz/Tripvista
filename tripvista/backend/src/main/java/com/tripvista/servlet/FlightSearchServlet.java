package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.FlightDAO;
import com.tripvista.model.Flight;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * FlightSearchServlet — Handles GET /api/flights?from=Delhi&to=Mumbai
 *
 * Query Params: from (city), to (city)
 * If no params, returns all flights.
 */
public class FlightSearchServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final FlightDAO flightDAO = new FlightDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String from = request.getParameter("from");
            String to = request.getParameter("to");

            List<Flight> flights;

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                flights = flightDAO.searchFlights(from, to);
            } else {
                flights = flightDAO.getAllFlights();
            }

            JsonObject res = new JsonObject();
            res.addProperty("success", true);
            res.addProperty("count", flights.size());
            res.add("flights", gson.toJsonTree(flights));
            out.print(gson.toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Error fetching flights: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }
}
