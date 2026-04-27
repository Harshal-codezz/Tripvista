package com.tripvista.model;

/**
 * Booking Model — Represents a user's booking record.
 */
public class Booking {
    private int id;
    private int userId;
    private String bookingType;   // flight, hotel, train, bus, package
    private int referenceId;       // ID of the booked item
    private String bookingDate;
    private String travelDate;
    private int passengers;
    private double totalPrice;
    private String status;         // confirmed, cancelled, pending

    // Default constructor
    public Booking() {}

    // Parameterized constructor
    public Booking(int userId, String bookingType, int referenceId, String travelDate, int passengers, double totalPrice) {
        this.userId = userId;
        this.bookingType = bookingType;
        this.referenceId = referenceId;
        this.travelDate = travelDate;
        this.passengers = passengers;
        this.totalPrice = totalPrice;
        this.status = "confirmed";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getBookingType() { return bookingType; }
    public void setBookingType(String bookingType) { this.bookingType = bookingType; }

    public int getReferenceId() { return referenceId; }
    public void setReferenceId(int referenceId) { this.referenceId = referenceId; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }

    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
