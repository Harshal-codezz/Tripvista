package com.tripvista.model;

/**
 * Hotel Model — Represents a hotel/property listing.
 */
public class Hotel {
    private int id;
    private String name;
    private String city;
    private String location;
    private int starRating;
    private double ratingScore;
    private String ratingText;
    private double pricePerNight;
    private double originalPrice;
    private String amenities;
    private String imageUrl;

    // Default constructor
    public Hotel() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getStarRating() { return starRating; }
    public void setStarRating(int starRating) { this.starRating = starRating; }

    public double getRatingScore() { return ratingScore; }
    public void setRatingScore(double ratingScore) { this.ratingScore = ratingScore; }

    public String getRatingText() { return ratingText; }
    public void setRatingText(String ratingText) { this.ratingText = ratingText; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
