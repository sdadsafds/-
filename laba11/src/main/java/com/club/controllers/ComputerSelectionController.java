package com.club.controllers;

import com.club.services.ComputerService;
import com.club.services.SessionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import com.club.models.Computer;

import java.util.List;

public class ComputerSelectionController {

    @FXML
    private ListView<String> computerListView; // исправлено название переменной

    @FXML
    private Label statusLabel;

    private final ComputerService computerService = new ComputerService();
    private final SessionService sessionService = new SessionService();

    @FXML
    private void initialize() {
        List<Computer> computers = computerService.getAllComputers();

        // Преобразование объектов Computer в строки
        List<String> computerDescriptions = computers.stream()
                .map(computer -> "ID: " + computer.getId() + ", Характеристики: " + computer.getSpecifications() + ", Статус: " + computer.getStatus())
                .toList();

        // Добавление строк в ObservableList
        ObservableList<String> observableComputers = FXCollections.observableArrayList();
        observableComputers.addAll(computerDescriptions);

        // Привязка списка к ListView
        computerListView.setItems(observableComputers); // исправлено название переменной
    }

    @FXML
    private void startSession() {
        String selectedComputer = computerListView.getSelectionModel().getSelectedItem();

        if (selectedComputer != null) {
            // Начать игровую сессию на выбранном компьютере
            sessionService.startSession(1, selectedComputer, "2024-11-08 15:00:00");

            statusLabel.setText("Сессия началась на компьютере: " + selectedComputer);
            showAlert("Игровая сессия началась", "Вы начали игру на компьютере: " + selectedComputer, AlertType.INFORMATION);
        } else {
            showAlert("Ошибка", "Выберите компьютер для начала сессии", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);  // исправлено
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
