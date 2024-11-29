package com.club.services;

import com.club.database.DatabaseConnection;
import com.club.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.sql.*;

public class UserService {

    // Проверка пользователя
    public boolean isValidUser(String username, String password) {
        String sql = "SELECT * FROM Пользователи WHERE Имя = ? AND Пароль = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();  // Если пользователь найден, возвращаем true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Проверка администратора
    public boolean isAdmin(String username, String password) {
        String sql = "SELECT * FROM Пользователи WHERE Имя = ? AND Пароль = ? AND Статус = 'Администратор'";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();  // Если администратор найден, возвращаем true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Получение ID пользователя по имени и паролю
    public int getUserId(String username, String password) {
        String sql = "SELECT ID FROM Пользователи WHERE Имя = ? AND Пароль = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID"); // Возвращаем ID пользователя
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если пользователь не найден, возвращаем -1
    }

    // Получение текущего баланса пользователя
    public double getUserBalance(int userId) {
        String query = "SELECT Баланс FROM Пользователи WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("Баланс");
                System.out.println("Получен баланс для пользователя " + userId + ": " + balance);  // Логирование
                return balance;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;  // Вернем 0, если баланс не найден
    }


    // Обновление баланса пользователя в базе данных
    public void updateBalance(int userId, double newBalance) {
        String sql = "UPDATE Пользователи SET Баланс = ? WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, newBalance); // Устанавливаем новый баланс
            preparedStatement.setInt(2, userId); // Устанавливаем ID пользователя
            preparedStatement.executeUpdate(); // Выполняем обновление
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Обновление интерфейса с текущим балансом пользователя
    public void updateUserBalanceDisplay(int userId, Label balanceLabel) {
        double balance = getUserBalance(userId);
        balanceLabel.setText("Баланс: " + balance + " руб.");
    }


    // Проверка, является ли пользователь администратором по ID
    public boolean isAdminById(int userId) {
        String sql = "SELECT Статус FROM Пользователи WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String status = resultSet.getString("Статус");
                return "Администратор".equals(status);  // Если статус "Администратор", возвращаем true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Если статус не "Администратор", возвращаем false
    }

    // Проверка, есть ли достаточно средств для запуска игры
    public boolean canPlayGame(int userId, double gameCost) {
        double currentBalance = getUserBalance(userId);
        return currentBalance >= gameCost;
    }

    // Списание средств с баланса пользователя
    public void deductBalance(int userId, double amount) {
        String sql = "UPDATE Пользователи SET Баланс = Баланс - ? WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление средств на баланс пользователя
    public void addBalance(int userId, double amount) {
        String sql = "UPDATE Пользователи SET Баланс = Баланс + ? WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление пользователя из базы данных
    public void deleteUser(int userId) {
        String sql = "DELETE FROM Пользователи WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate(); // Выполняем запрос на удаление
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получение всех пользователей из базы данных
    public ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Пользователи";  // Запрос для получения всех пользователей
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("ID"), resultSet.getString("Имя"),
                        resultSet.getString("Контактные_данные"), resultSet.getString("Статус"),
                        resultSet.getDouble("Баланс"));  // Получаем данные о пользователе
                users.add(user);  // Добавляем пользователя в список
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;  // Возвращаем список пользователей
    }
}
