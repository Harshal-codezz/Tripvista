package com.tripvista.model;

/**
 * HolidayPackage Model — Represents a travel package listing.
 */
public class HolidayPackage {
    private int id;
    private String destination;
    private String title;
    private int durationNights;
    private int durationDays;
    private double pricePerPerson;
    private double originalPrice;
    private String includes;
    private String imageUrl;
    private double rating;

    // Default constructor
    public HolidayPackage() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDurationNights() { return durationNights; }
    public void setDurationNights(int durationNights) { this.durationNights = durationNights; }

    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }

    public double getPricePerPerson() { return pricePerPerson; }
    public void setPricePerPerson(double pricePerPerson) { this.pricePerPerson = pricePerPerson; }

    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public String getIncludes() { return includes; }
    public void setIncludes(String includes) { this.includes = includes; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
