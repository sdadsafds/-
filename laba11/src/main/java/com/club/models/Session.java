package com.club.models;

import java.sql.Timestamp;

public class Session {
    private int sessionId;
    private int userId;
    private int computerId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double cost;

    public Session(int sessionId, int userId, int computerId, Timestamp startTime, Timestamp endTime, double cost) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.computerId = computerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
    }

    // Getters
    public int getSessionId() {
        return sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getComputerId() {
        return computerId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public double getCost() {
        return cost;
    }
}
