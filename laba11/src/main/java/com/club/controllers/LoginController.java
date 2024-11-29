package com.club.controllers;

import com.club.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginStatusLabel;

    private final UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.isAdmin(username, password)) {
            loginStatusLabel.setText("Добро пожаловать, Администратор!");
            openAdminWindow();
        } else if (userService.isValidUser(username, password)) {
            int userId = userService.getUserId(username, password);
            double userBalance = userService.getUserBalance(userId);
            loginStatusLabel.setText("Добро пожаловать!");
            openGameSelectionWindow(userId, userBalance);
        } else {
            loginStatusLabel.setText("Неверные имя или пароль.");
        }
    }

    @FXML
    private void openGameSelectionWindow(int userId, double userBalance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/laba11/fxml/UserInterface.fxml"));
            Parent root = loader.load();

            UserInterfaceController userInterfaceController = loader.getController();
            userInterfaceController.initialize(userService, userId, userBalance); // Передаем баланс

            Stage stage = new Stage();
            stage.setTitle("Выбор игры");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openAdminWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/laba11/fxml/AdminPanel.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Панель администратора");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
