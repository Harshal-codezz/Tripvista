package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.UserDAO;
import com.tripvista.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * RegisterServlet — Handles user registration via POST /api/register
 *
 * Request Body (JSON): { "fullName": "...", "email": "...", "password": "...", "phone": "..." }
 * Response (JSON):
 *   Success: { "success": true, "message": "..." }
 *   Failure: { "success": false, "message": "..." }
 */
public class RegisterServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final UserDAO userDAO = new UserDAO();

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

            String fullName = json.has("fullName") ? json.get("fullName").getAsString().trim() : "";
            String email = json.has("email") ? json.get("email").getAsString().trim() : "";
            String password = json.has("password") ? json.get("password").getAsString() : "";
            String phone = json.has("phone") ? json.get("phone").getAsString().trim() : "";

            // Validate input
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Name, email, and password are required.");
                out.print(gson.toJson(res));
                return;
            }

            // Check password length
            if (password.length() < 6) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Password must be at least 6 characters.");
                out.print(gson.toJson(res));
                return;
            }

            // Check if email already exists
            if (userDAO.emailExists(email)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "An account with this email already exists.");
                out.print(gson.toJson(res));
                return;
            }

            // Register user
            User user = new User(fullName, email, password, phone);
            boolean registered = userDAO.register(user);

            if (registered) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                JsonObject res = new JsonObject();
                res.addProperty("success", true);
                res.addProperty("message", "Account created successfully! Please sign in.");
                out.print(gson.toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Registration failed. Please try again.");
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
