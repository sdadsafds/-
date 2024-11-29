package com.club.controllers;

import com.club.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserInterfaceController {

    private UserService userService;
    private int userId;

    @FXML
    private ListView<Game> gameListView;
    @FXML
    private Button applyFilterButton;
    @FXML
    private Button resetFilterButton;  // Добавляем кнопку сброса фильтра
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private ComboBox<Integer> ratingComboBox;
    @FXML
    private TextField filterTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private Label balanceLabel;

    private ObservableList<Game> games;
    private ObservableList<Game> filteredGames;

    private double userBalance;

    // Метод инициализации контроллера с балансом
    public void initialize(UserService userService, int userId, double userBalance) {
        this.userService = userService;
        this.userId = userId;
        this.userBalance = userBalance;

        initialize();
        updateBalanceDisplay();
    }

    // Метод для обновления отображения баланса
    private void updateBalanceDisplay() {
        balanceLabel.setText("Баланс: " + userBalance + " руб.");
    }

    @FXML
    public void initialize() {
        ratingComboBox.getItems().setAll(1, 2, 3, 4, 5);
        genreComboBox.setItems(FXCollections.observableArrayList("Экшн", "Стратегия", "РПГ", "Головоломка"));

        games = FXCollections.observableArrayList(
                new Game("cs 2", 5, "Экшн"),
                new Game("DoTA 2", 4, "Стратегия"),
                new Game("WoW", 3, "РПГ"),
                new Game("puzzle", 2, "Головоломка"),
                new Game("CoD", 1, "Экшн")
        );

        filteredGames = FXCollections.observableArrayList(games);
        gameListView.setCellFactory(param -> new ListCell<Game>() {
            @Override
            protected void updateItem(Game game, boolean empty) {
                super.updateItem(game, empty);
                if (empty || game == null) {
                    setText(null);
                } else {
                    setText(game.getName());
                }
            }
        });

        gameListView.setItems(filteredGames);
    }

    // Метод для применения фильтров
    @FXML
    public void handleApplyFilter() {
        String filterText = filterTextField.getText().toLowerCase();
        Integer selectedRating = ratingComboBox.getValue();
        String selectedGenre = genreComboBox.getValue();

        filteredGames.setAll(
                games.stream()
                        .filter(game -> (selectedRating == null || game.getRating() == selectedRating) &&
                                (selectedGenre == null || game.getGenre().equalsIgnoreCase(selectedGenre)) &&
                                (game.getName().toLowerCase().contains(filterText)))
                        .collect(Collectors.toList())
        );
    }

    // Метод для сброса фильтров
    @FXML
    public void handleResetFilter() {
        genreComboBox.getSelectionModel().clearSelection();
        ratingComboBox.getSelectionModel().clearSelection();
        filterTextField.clear();

        filteredGames.setAll(games);
    }

    // Метод для запуска игры
    @FXML
    public void handlePlayGame() {
        double gameCost = 100.0;

        if (!userService.canPlayGame(userId, gameCost)) {
            messageLabel.setText("Недостаточно средств на балансе для запуска игры!");
            return;
        }

        Game selectedGame = gameListView.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            messageLabel.setText("Выберите игру для начала!");
            return;
        }

        userService.deductBalance(userId, gameCost);
        userBalance = userService.getUserBalance(userId);
        updateBalanceDisplay();

        messageLabel.setText("Игра " + selectedGame.getName() + " началась! С вашего баланса списано " + gameCost + " руб за 1 час игры.");
    }

    public static class Game {
        private String name;
        private int rating;
        private String genre;

        public Game(String name, int rating, String genre) {
            this.name = name;
            this.rating = rating;
            this.genre = genre;
        }

        public String getName() {
            return name;
        }

        public int getRating() {
            return rating;
        }

        public String getGenre() {
            return genre;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
