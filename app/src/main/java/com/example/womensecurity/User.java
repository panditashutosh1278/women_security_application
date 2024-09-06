package com.example.womensecurity;


public class User {
    private int id;
    private String name;
    private String phoneNumber;

    public User(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(String john_doe, String name) {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return name + " - " + phoneNumber;
    }
}
