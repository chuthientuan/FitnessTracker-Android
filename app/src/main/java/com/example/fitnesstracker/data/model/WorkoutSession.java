package com.example.fitnesstracker.data.model;

import java.util.List;

public class WorkoutSession {
    private String sessionId;
    private String uid;
    private String type; // "Running", "Cycling", etc.
    private double distanceKm;
    private int durationMinutes;
    private long startTime;
    private long endTime;
    private List<LatLng> route;

    public WorkoutSession(String sessionId, String uid, String type, double distanceKm, int durationMinutes, long startTime, long endTime, List<LatLng> route) {
        this.sessionId = sessionId;
        this.uid = uid;
        this.type = type;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.route = route;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<LatLng> getRoute() {
        return route;
    }

    public void setRoute(List<LatLng> route) {
        this.route = route;
    }
}
