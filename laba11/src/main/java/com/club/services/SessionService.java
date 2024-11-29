package com.club.services;

import java.util.HashMap;
import java.util.Map;

public class SessionService {

    private final Map<Integer, GameSession> activeSessions = new HashMap<>();

    public void startSession(int userId, String computer, String startTime) {
        GameSession newSession = new GameSession(userId, computer, startTime);
        activeSessions.put(userId, newSession);
        System.out.println("Сессия начала: " + newSession);
    }

    public void endSession(int userId, String endTime, double cost) {
        GameSession session = activeSessions.get(userId);
        if (session != null) {
            session.setEndTime(endTime);
            session.setCost(cost);
            System.out.println("Сессия закончена: " + session);
        } else {
            System.out.println("Сессия не найдена для пользователя " + userId);
        }
    }

    public GameSession getSessionDetails(int userId) {
        return activeSessions.get(userId);
    }

    public class GameSession {
        private final int userId;
        private final String computer;
        private final String startTime;
        private String endTime;
        private double cost;

        public GameSession(int userId, String computer, String startTime) {
            this.userId = userId;
            this.computer = computer;
            this.startTime = startTime;
            this.cost = 0.0;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Session[userId=" + userId + ", computer=" + computer + ", startTime=" + startTime + ", endTime=" + endTime + ", cost=" + cost + "]";
        }
    }
}
