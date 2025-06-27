package com.example.fitnesstracker.data.model;

public class User {
    private String userName;
    private String userId;
    private String email;
    private String gender;
    private double height;
    private double weight;

    public User(String userName, String userId, String email, String gender, double height, double weight) {
        this.userName = userName;
        this.userId = userId;
        this.email = email;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
