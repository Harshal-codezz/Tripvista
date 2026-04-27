package com.tripvista.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tripvista.dao.UserDAO;
import com.tripvista.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * LoginServlet — Handles user login via POST /api/login
 *
 * Request Body (JSON): { "email": "...", "password": "..." }
 * Response (JSON):
 *   Success: { "success": true, "message": "...", "user": { ... } }
 *   Failure: { "success": false, "message": "..." }
 */
public class LoginServlet extends HttpServlet {

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

            String email = json.has("email") ? json.get("email").getAsString().trim() : "";
            String password = json.has("password") ? json.get("password").getAsString() : "";

            // Validate input
            if (email.isEmpty() || password.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Email and password are required.");
                out.print(gson.toJson(res));
                return;
            }

            // Authenticate
            User user = userDAO.login(email, password);

            if (user != null) {
                // Create session
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getFullName());
                session.setAttribute("userEmail", user.getEmail());

                JsonObject res = new JsonObject();
                res.addProperty("success", true);
                res.addProperty("message", "Login successful!");
                res.add("user", gson.toJsonTree(user));
                out.print(gson.toJson(res));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonObject res = new JsonObject();
                res.addProperty("success", false);
                res.addProperty("message", "Invalid email or password.");
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
