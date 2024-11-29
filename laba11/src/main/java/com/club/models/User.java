package com.club.models;

public class User {
    private int id;
    private String name;
    private String contact;
    private String status;
    private double balance; // Добавляем поле для хранения баланса

    // Конструктор
    public User(int id, String name, String contact, String status, double balance) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.status = status;
        this.balance = balance; // Инициализируем баланс
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Геттер для баланса
    public double getBalance() {
        return balance;
    }

    // Сеттер для баланса
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Метод для конвертации баланса в часы
    public int convertBalanceToHours() {
        final double hourCost = 10.00; // 1 час игры = 10 единиц валюты
        return (int)(balance / hourCost); // Возвращаем количество часов
    }

    // Метод для добавления баланса
    public void addBalance(double amount) {
        this.balance += amount; // Увеличиваем баланс
    }

    // Метод для списания баланса
    public void deductBalance(double amount) {
        if (balance >= amount) {
            this.balance -= amount; // Списываем баланс
        } else {
            System.out.println("Недостаточно средств на балансе.");
        }
    }
}
