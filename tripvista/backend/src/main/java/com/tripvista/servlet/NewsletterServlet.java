package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.NewsletterDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * NewsletterServlet — Handles POST /api/newsletter
 *
 * Request Body (JSON): { "email": "user@example.com" }
 * Response (JSON):
 *   Success: { "success": true, "message": "..." }
 *   Already subscribed: { "success": false, "message": "..." }
 */
public class NewsletterServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final NewsletterDAO newsletterDAO = new NewsletterDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Read JSON body
            BufferedReader reader = request.getReader();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            String email = json.has("email") ? json.get("email").getAsString().trim() : "";

            // Validate email
            if (email.isEmpty() || !email.contains("@")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Please enter a valid email address.");
                out.print(gson.toJson(res));
                return;
            }

            // Check if already subscribed
            if (newsletterDAO.isSubscribed(email)) {
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "This email is already subscribed!");
                out.print(gson.toJson(res));
                return;
            }

            // Subscribe
            boolean subscribed = newsletterDAO.subscribe(email);

            if (subscribed) {
                JsonObject res = new JsonObject();
                res.addProperty("success", true);
                res.addProperty("message", "Successfully subscribed to newsletter!");
                out.print(gson.toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Subscription failed. Please try again.");
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
