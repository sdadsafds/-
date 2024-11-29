package com.club.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://10.207.144.159:3306/user013_db1?characterEncoding=utf8";
        String username = "user013_user1";
        String password = "m_ahGhi0";
        return DriverManager.getConnection(url, username, password);
    }
}
