package com.nicolae.userapi.model;

public class UserResponse {

    private String name;
    private int age;
    private double salary;

    public UserResponse(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }
}