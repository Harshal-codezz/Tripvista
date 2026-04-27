package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.PackageDAO;
import com.tripvista.model.HolidayPackage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * PackageSearchServlet — Handles GET /api/packages?destination=Goa
 *
 * Query Params: destination
 * If no params, returns all packages.
 */
public class PackageSearchServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final PackageDAO packageDAO = new PackageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String destination = request.getParameter("destination");

            List<HolidayPackage> packages;

            if (destination != null && !destination.isEmpty()) {
                packages = packageDAO.searchPackages(destination);
            } else {
                packages = packageDAO.getAllPackages();
            }

            JsonObject res = new JsonObject();
            res.addProperty("success", true);
            res.addProperty("count", packages.size());
            res.add("packages", gson.toJsonTree(packages));
            out.print(gson.toJson(res));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject res = new JsonObject();
            res.addProperty("success", false);
            res.addProperty("message", "Error fetching packages: " + e.getMessage());
            out.print(gson.toJson(res));
        }
    }
}
