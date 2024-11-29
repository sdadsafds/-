package com.club.controllers;

import com.club.models.User;
import com.club.models.Computer;
import com.club.services.UserService;
import com.club.services.ComputerService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    // Поля для работы с пользователями
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, Double> balanceColumn;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField amountField;

    @FXML
    private Label statusLabel;

    private final UserService userService = new UserService();

    // Поля для работы с компьютерами
    @FXML
    private TableView<Computer> computersTable;

    @FXML
    private TableColumn<Computer, Integer> computerIdColumn;

    @FXML
    private TableColumn<Computer, String> specificationsColumn;

    @FXML
    private TableColumn<Computer, String> statusColumn;

    @FXML
    private TextField computerIdField;

    @FXML
    private TextField specificationsField;

    @FXML
    private TextField computerStatusField;

    private final ComputerService computerService = new ComputerService();

    @FXML
    public void initialize() {
        // Инициализация колонок таблицы с данными пользователей
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Инициализация колонок таблицы с данными о компьютерах
        computerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        specificationsColumn.setCellValueFactory(new PropertyValueFactory<>("specifications"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Загрузка пользователей и компьютеров при старте
        loadUsers();
        loadComputers();
    }

    // Методы для работы с пользователями

    // Метод для загрузки пользователей в таблицу
    private void loadUsers() {
        ObservableList<User> users = userService.getAllUsers();
        usersTable.setItems(users);
    }

    @FXML
    private void handleAddBalance() {
        try {
            // Получаем ID пользователя и сумму для добавления
            int userId = Integer.parseInt(userIdField.getText());
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                statusLabel.setText("Сумма должна быть положительной.");
                return;
            }

            // Добавляем баланс пользователю
            userService.addBalance(userId, amount);
            statusLabel.setText("Баланс добавлен.");
            loadUsers();  // Обновляем таблицу с пользователями
        } catch (NumberFormatException e) {
            statusLabel.setText("Пожалуйста, введите корректные данные.");
        }
    }

    @FXML
    private void handleDeductBalance() {
        try {
            // Получаем ID пользователя и сумму для списания
            int userId = Integer.parseInt(userIdField.getText());
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                statusLabel.setText("Сумма должна быть положительной.");
                return;
            }

            // Проверяем, достаточно ли средств на балансе
            double currentBalance = userService.getUserBalance(userId);
            if (currentBalance >= amount) {
                userService.deductBalance(userId, amount);
                statusLabel.setText("Баланс списан.");
                loadUsers();  // Обновляем таблицу с пользователями
            } else {
                statusLabel.setText("Недостаточно средств для списания.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Пожалуйста, введите корректные данные.");
        }
    }

    // Методы для работы с компьютерами

    // Метод для загрузки компьютеров в таблицу
    private void loadComputers() {
        ObservableList<Computer> computers = computerService.getAllComputers();
        computersTable.setItems(computers);
    }

    @FXML
    private void openAddComputerForm() {
        try {
            // Загрузка FXML файла для формы добавления компьютера
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/laba11/fxml/ComputerManagementController.fxml"));
            Scene scene = new Scene(loader.load());

            // Создание нового окна для формы добавления компьютера
            Stage stage = new Stage();
            stage.setTitle("Add New Computer");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Ошибка при открытии формы добавления компьютера.");
        }
    }

}
