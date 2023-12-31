package com.surfscribebackend.surfscribe.backend.model;

public class GeoCoordinates {
    private double lat;
    private double lon;

    public GeoCoordinates() {
    }

    public GeoCoordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "GeoCoordinates{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
