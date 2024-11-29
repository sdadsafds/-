package com.club.services;

import com.club.database.DatabaseConnection;
import com.club.models.Computer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ComputerService {
    // Подключение к базе данных
    private static final String URL = "jdbc:mysql://10.207.144.159:3306/user013_db1?characterEncoding=utf8"; // Замените на свой URL базы данных
    private static final String USER = "user013_user1"; // Замените на имя пользователя вашей базы данных
    private static final String PASSWORD = "m_ahGhi0"; // Замените на пароль вашей базы данных

    // Метод для получения всех компьютеров из базы данных
    public ObservableList<Computer> getAllComputers() {
        ObservableList<Computer> computers = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM Компьютеры"; // Замените на имя своей таблицы
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("iD");
                    String specifications = resultSet.getString("Характеристики");
                    String status = resultSet.getString("Статус");

                    computers.add(new Computer(id, specifications, status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return computers;
    }

    // Добавление нового компьютера в базу данных
    public void addComputer(String specifications, String status) {
        String sql = "INSERT INTO Компьютеры (Характеристики, Статус) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, specifications);
            preparedStatement.setString(2, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Обновление статуса компьютера
    public void updateComputerStatus(int computerId, String status) {
        String sql = "UPDATE Компьютеры SET Статус = ? WHERE ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, computerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления характеристик и статуса компьютера
    public void updateComputer(int id, String specifications, String status) {
        String sql = "UPDATE Компьютеры SET Характеристики = ?, Статус = ? WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, specifications);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
