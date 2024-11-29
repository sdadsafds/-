package com.club.controllers;

import com.club.services.SessionService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SessionStatisticsController {

    @FXML
    private TextField userIdField;

    @FXML
    private Label sessionDetailsLabel;

    private final SessionService sessionService = new SessionService();

    @FXML
    private void showSessionDetails() {
        int userId = Integer.parseInt(userIdField.getText());
        SessionService.GameSession session = sessionService.getSessionDetails(userId);

        if (session != null) {
            sessionDetailsLabel.setText("Детали сессии: " + session);
        } else {
            sessionDetailsLabel.setText("Сессия не найдена для данного пользователя.");
        }
    }
}
