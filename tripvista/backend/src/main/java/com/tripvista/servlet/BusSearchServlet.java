package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.BusDAO;
import com.tripvista.model.Bus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * BusSearchServlet — Handles GET /api/buses?from=Pune&to=Mumbai
 *
 * Query Params: from (city), to (city)
 * If no params, returns all buses.
 */
public class BusSearchServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final BusDAO busDAO = new BusDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String from = request.getParameter("from");
            String to = request.getParameter("to");

            List<Bus> buses;

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                buses = busDAO.searchBuses(from, to);
            } else {
                buses = busDAO.getAllBuses();
            }

            JsonObject res = new JsonObject();
            res.addProperty("success", true);
            res.addProperty("count", buses.size());
            res.add("buses", gson.toJsonTree(buses));
            out.print(gson.toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Error fetching buses: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }
}
