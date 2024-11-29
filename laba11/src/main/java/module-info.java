module com.example.kurs {
    requires java.sql;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;


    opens com.example.kurs to javafx.fxml;
    exports com.example.kurs;
    exports com.club.database;
    opens com.club.database to javafx.fxml;
    exports com.club.services;
    opens com.club.services to javafx.fxml;
    exports com.club.controllers;
    opens com.club.controllers to javafx.fxml;
    exports com.club.models;
    opens com.club.models to javafx.fxml;
}