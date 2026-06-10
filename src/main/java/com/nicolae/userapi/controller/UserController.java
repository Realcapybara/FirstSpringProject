package com.nicolae.userapi.controller;

import com.nicolae.userapi.model.User;
import com.nicolae.userapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.nicolae.userapi.model.UpdateSalaryRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.nicolae.userapi.model.RaiseSalaryRequest;
import org.springframework.web.bind.annotation.PatchMapping;
import com.nicolae.userapi.model.CreateUserRequest;
import com.nicolae.userapi.model.UserResponse;
import com.nicolae.userapi.mapper.UserMapper;
import com.nicolae.userapi.validator.UserValidator;
import com.nicolae.userapi.model.ErrorResponse;
import com.nicolae.userapi.validator.ValidationResult;


import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    public UserController(UserService userService, UserMapper userMapper, UserValidator userValidator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.getUsers()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @GetMapping("/users/{name}")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable String name) {
        User foundUser = userService.getUserByName(name);

        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = userMapper.toUserResponse(foundUser);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody CreateUserRequest request) {

        ValidationResult validationResult = userValidator.validateCreateUserRequest(request);

        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(validationResult.getMessage()));
        }

        User user = userMapper.toUser(request);

        User savedUser = userService.addUser(user);

        UserResponse response = userMapper.toUserResponse(savedUser);

        return ResponseEntity.status(201).body(response);
    }
    @PutMapping("/users/{name}/salary")
    public ResponseEntity<?> updateUserSalary(
            @PathVariable String name,
            @RequestBody UpdateSalaryRequest request
    ) {
        ValidationResult validationResult = userValidator.validateUpdateSalaryRequest(request);

        if (!validationResult.isValid()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(validationResult.getMessage()));
        }

        User updatedUser = userService.updateUserSalary(name, request.getSalary());

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = userMapper.toUserResponse(updatedUser);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/users/{name}")
    public ResponseEntity<Void> deleteUserByName(@PathVariable String name) {
        boolean deleted = userService.deleteUserByName(name);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/users/{name}/salary/raise")
    public ResponseEntity<?> raiseUserSalary(
            @PathVariable String name,
            @RequestBody RaiseSalaryRequest request
    ) {

        ValidationResult validationResult = userValidator.validateRaiseSalaryRequest(request);

        if (!validationResult.isValid()){
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(validationResult.getMessage()));
        }

        User updatedUser = userService.raiseUserSalary(name, request.getAmount());

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = userMapper.toUserResponse(updatedUser);

        return ResponseEntity.ok(response);
    }
}