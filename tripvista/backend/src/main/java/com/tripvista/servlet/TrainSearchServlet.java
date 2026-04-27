package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.TrainDAO;
import com.tripvista.model.Train;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * TrainSearchServlet — Handles GET /api/trains?from=Delhi&to=Mumbai
 *
 * Query Params: from (station/code), to (station/code)
 * If no params, returns all trains.
 */
public class TrainSearchServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final TrainDAO trainDAO = new TrainDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String from = request.getParameter("from");
            String to = request.getParameter("to");

            List<Train> trains;

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                trains = trainDAO.searchTrains(from, to);
            } else {
                trains = trainDAO.getAllTrains();
            }

            JsonObject res = new JsonObject();
            res.addProperty("success", true);
            res.addProperty("count", trains.size());
            res.add("trains", gson.toJsonTree(trains));
            out.print(gson.toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Error fetching trains: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }
}
