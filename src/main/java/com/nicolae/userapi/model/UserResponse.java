package com.nicolae.userapi.model;

public class UserResponse {

    private Long id;
    private String name;
    private int age;
    private double salary;
    private String department;

    public UserResponse(Long id, String name, int age, double salary, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }

    public double getId() {
        return id;
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

    public String getDepartment() { return department; }
}