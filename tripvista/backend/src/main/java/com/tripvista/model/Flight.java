package com.tripvista.model;

/**
 * Flight Model — Represents a flight record.
 */
public class Flight {
    private int id;
    private String airline;
    private String flightNumber;
    private String fromCity;
    private String toCity;
    private String fromCode;
    private String toCode;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String stops;
    private double price;
    private int availableSeats;

    // Default constructor
    public Flight() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getFromCity() { return fromCity; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }

    public String getToCity() { return toCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }

    public String getFromCode() { return fromCode; }
    public void setFromCode(String fromCode) { this.fromCode = fromCode; }

    public String getToCode() { return toCode; }
    public void setToCode(String toCode) { this.toCode = toCode; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getStops() { return stops; }
    public void setStops(String stops) { this.stops = stops; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
