package com.club.models;

public class Computer {
    private int id;
    private String specifications;
    private String status;

    public Computer(int id, String specifications, String status) {
        this.id = id;
        this.specifications = specifications;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getSpecifications() {
        return specifications;
    }

    public String getStatus() {
        return status;
    }
}
