package com.nicolae.userapi.model;

import jakarta.validation.constraints.Positive;

public class RaiseSalaryRequest {

    @Positive(message = "Raise amount must be greater than 0")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}