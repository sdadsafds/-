package com.club.services;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            URL fxmlUrl = getClass().getResource("Login.fxml");
            // Загружаем файл Login.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/laba11/fxml/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
