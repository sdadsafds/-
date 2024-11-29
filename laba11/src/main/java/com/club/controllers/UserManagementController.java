package com.club.controllers;

import com.club.database.DatabaseConnection;
import com.club.models.User;
import com.club.services.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManagementController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> contactColumn;

    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private TableColumn<User, Double> balanceColumn;

    @FXML
    private TextField balanceField;

    private final UserService userService = new UserService();

    // Метод для инициализации
    public void initialize() {
        // Настройка колонок для отображения данных
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Загрузить данные в таблицу
        ObservableList<User> users = userService.getAllUsers();
        usersTable.setItems(users);

        // Заполнение ComboBox со статусами
        statusComboBox.getItems().addAll("Пользователь", "Администратор");
    }

    @FXML
    public void addUser(String name, String contact, String status, double balance) {
        String sql = "INSERT INTO Пользователи (Имя, Контактные_данные, Статус, Баланс) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contact);
            preparedStatement.setString(3, status);
            preparedStatement.setDouble(4, balance);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem(); // Получаем выбранного пользователя
        if (selectedUser != null) {
            int userId = selectedUser.getId(); // Получаем ID выбранного пользователя
            userService.deleteUser(userId); // Вызываем метод удаления
            statusLabel.setText("Пользователь удален.");
            usersTable.setItems(userService.getAllUsers());  // Обновляем список пользователей
        } else {
            statusLabel.setText("Выберите пользователя для удаления.");
        }
    }




    @FXML
    private void updateBalance() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem(); // Получаем выбранного пользователя
        if (selectedUser != null) {
            try {
                double newBalance = Double.parseDouble(balanceField.getText());  // Чтение нового баланса
                userService.updateBalance(selectedUser.getId(), newBalance);  // Обновляем баланс
                statusLabel.setText("Баланс обновлен.");
                usersTable.setItems(userService.getAllUsers());  // Обновляем список пользователей
            } catch (NumberFormatException e) {
                statusLabel.setText("Введите правильное значение баланса.");
            }
        } else {
            statusLabel.setText("Выберите пользователя для обновления баланса.");
        }
    }


    @FXML
    private void addBalance() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                double amountToAdd = Double.parseDouble(balanceField.getText());
                userService.addBalance(selectedUser.getId(), amountToAdd);
                statusLabel.setText("Средства добавлены на баланс.");
                usersTable.setItems(userService.getAllUsers());
            } catch (NumberFormatException e) {
                statusLabel.setText("Введите правильное значение для добавления средств.");
            }
        } else {
            statusLabel.setText("Выберите пользователя для добавления средств.");
        }
    }

    @FXML
    private void deductBalance() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                double amountToDeduct = Double.parseDouble(balanceField.getText());
                userService.deductBalance(selectedUser.getId(), amountToDeduct);
                statusLabel.setText("Средства списаны с баланса.");
                usersTable.setItems(userService.getAllUsers());
            } catch (NumberFormatException e) {
                statusLabel.setText("Введите правильное значение для списания средств.");
            }
        } else {
            statusLabel.setText("Выберите пользователя для списания средств.");
        }
    }
}
