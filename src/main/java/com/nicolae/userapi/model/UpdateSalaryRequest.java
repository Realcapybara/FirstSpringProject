package com.nicolae.userapi.model;

import jakarta.validation.constraints.PositiveOrZero;

public class UpdateSalaryRequest {

    @PositiveOrZero(message = "Salary cannot be negative")
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}