package com.nicolae.userapi.validator;

import com.nicolae.userapi.model.CreateUserRequest;
import com.nicolae.userapi.model.RaiseSalaryRequest;
import com.nicolae.userapi.model.UpdateSalaryRequest;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public ValidationResult validateCreateUserRequest(CreateUserRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return new ValidationResult(false, "Name is required");
        }

        if (request.getAge() <= 0) {
            return new ValidationResult(false, "Age must be greater than 0");
        }

        if (request.getSalary() < 0) {
            return new ValidationResult(false, "Salary cannot be negative");
        }

        return new ValidationResult(true, null);
    }

    public ValidationResult validateUpdateSalaryRequest(UpdateSalaryRequest request) {
        if (request.getSalary() < 0) {
            return new ValidationResult(false, "Salary cannot be negative");
        }

        return new ValidationResult(true, null);
    }

    public ValidationResult validateRaiseSalaryRequest(RaiseSalaryRequest request) {
        if (request.getAmount() <= 0) {
            return new ValidationResult(false, "Raise amount must be greater than 0");
        }

        return new ValidationResult(true, null);
    }
}