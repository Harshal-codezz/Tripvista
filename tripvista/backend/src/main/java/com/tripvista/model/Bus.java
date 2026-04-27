package com.tripvista.model;

/**
 * Bus Model — Represents a bus service listing.
 */
public class Bus {
    private int id;
    private String operator;
    private String busType;
    private String busTypeCategory;
    private String fromCity;
    private String toCity;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private double price;
    private double rating;
    private int totalReviews;
    private String amenities;
    private int seatsLeft;

    // Default constructor
    public Bus() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getBusType() { return busType; }
    public void setBusType(String busType) { this.busType = busType; }

    public String getBusTypeCategory() { return busTypeCategory; }
    public void setBusTypeCategory(String busTypeCategory) { this.busTypeCategory = busTypeCategory; }

    public String getFromCity() { return fromCity; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }

    public String getToCity() { return toCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getTotalReviews() { return totalReviews; }
    public void setTotalReviews(int totalReviews) { this.totalReviews = totalReviews; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public int getSeatsLeft() { return seatsLeft; }
    public void setSeatsLeft(int seatsLeft) { this.seatsLeft = seatsLeft; }
}
