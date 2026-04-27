package com.tripvista.model;

/**
 * Train Model — Represents an Indian railway train.
 */
public class Train {
    private int id;
    private String trainName;
    private String trainNumber;
    private String trainType;
    private String fromStation;
    private String toStation;
    private String fromCode;
    private String toCode;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private int stops;
    private String runsOn;
    private Double priceSleeper;
    private Double price3A;
    private Double price2A;
    private Double price1A;
    private int availableSeats;

    // Default constructor
    public Train() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainType() { return trainType; }
    public void setTrainType(String trainType) { this.trainType = trainType; }

    public String getFromStation() { return fromStation; }
    public void setFromStation(String fromStation) { this.fromStation = fromStation; }

    public String getToStation() { return toStation; }
    public void setToStation(String toStation) { this.toStation = toStation; }

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

    public int getStops() { return stops; }
    public void setStops(int stops) { this.stops = stops; }

    public String getRunsOn() { return runsOn; }
    public void setRunsOn(String runsOn) { this.runsOn = runsOn; }

    public Double getPriceSleeper() { return priceSleeper; }
    public void setPriceSleeper(Double priceSleeper) { this.priceSleeper = priceSleeper; }

    public Double getPrice3A() { return price3A; }
    public void setPrice3A(Double price3A) { this.price3A = price3A; }

    public Double getPrice2A() { return price2A; }
    public void setPrice2A(Double price2A) { this.price2A = price2A; }

    public Double getPrice1A() { return price1A; }
    public void setPrice1A(Double price1A) { this.price1A = price1A; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
