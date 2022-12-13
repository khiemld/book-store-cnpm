package com.bookstore.admin.service;

public class OrderStatusService {
    private int id;
    private String name;

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

    public OrderStatusService() {
    }

    public OrderStatusService(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
