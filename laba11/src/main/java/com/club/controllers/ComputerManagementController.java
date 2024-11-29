package com.club.controllers;

import com.club.services.ComputerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class ComputerManagementController {

    @FXML
    private TextField specificationsField;

    @FXML
    private TextField statusField;

    @FXML
    private Label statusLabel;

    private final ComputerService computerService = new ComputerService();

    @FXML
    private void addComputer() {
        String specifications = specificationsField.getText();
        String status = statusField.getText();

        if (!specifications.isEmpty() && !status.isEmpty()) {
            computerService.addComputer(specifications, status);
            statusLabel.setText("Компьютер добавлен.");
        } else {
            statusLabel.setText("Заполните все поля.");
        }
    }
}
