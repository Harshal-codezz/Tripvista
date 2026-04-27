package com.tripvista.model;

/**
 * Newsletter Model — Represents a newsletter subscriber.
 */
public class Newsletter {
    private int id;
    private String email;
    private String subscribedAt;

    // Default constructor
    public Newsletter() {}

    // Parameterized constructor
    public Newsletter(String email) {
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubscribedAt() { return subscribedAt; }
    public void setSubscribedAt(String subscribedAt) { this.subscribedAt = subscribedAt; }
}
