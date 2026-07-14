package com.nicolae.userapi.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1, message = "Age must be greater than 0")
    private int age;

    @PositiveOrZero(message = "Salary cannot be negative")
    private double salary;

    @NotBlank(message = "Department is required")
    private String department;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment(){
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}