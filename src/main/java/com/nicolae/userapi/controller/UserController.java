package com.nicolae.userapi.controller;

import com.nicolae.userapi.model.UpdateUserRequest;
import com.nicolae.userapi.model.User;
import com.nicolae.userapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nicolae.userapi.model.UpdateSalaryRequest;
import com.nicolae.userapi.model.RaiseSalaryRequest;
import com.nicolae.userapi.model.CreateUserRequest;
import com.nicolae.userapi.model.UserResponse;
import com.nicolae.userapi.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minimumSalary
    ) {
        return userService.getUsers(department, minimumSalary)
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User foundUser = userService.getUserById(id);

        UserResponse response = userMapper.toUserResponse(foundUser);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequest request) {

        User user = userMapper.toUser(request);

        User savedUser = userService.addUser(user);

        UserResponse response = userMapper.toUserResponse(savedUser);

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        User updatedData = userMapper.toUser(request);

        User updatedUser = userService.updateUser(id, updatedData);

        UserResponse response = userMapper.toUserResponse(updatedUser);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}/salary")
    public ResponseEntity<?> updateUserSalary(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSalaryRequest request
    ) {

        User updatedUser = userService.updateUserSalary(id, request.getSalary());

        UserResponse response = userMapper.toUserResponse(updatedUser);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/users/{id}/salary/raise")
    public ResponseEntity<?> raiseUserSalary(
            @PathVariable Long id,
            @Valid @RequestBody RaiseSalaryRequest request
    ) {

        User updatedUser = userService.raiseUserSalary(id, request.getAmount());

        UserResponse response = userMapper.toUserResponse(updatedUser);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/search/salary")
    public ResponseEntity<List<UserResponse>> getUsersWithSalaryAtLeast(@RequestParam double minimum) {
        List<User> users = userService.getUsersWithSalaryAtLeast(minimum);

        List<UserResponse> response = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/search/age")
    public ResponseEntity<List<UserResponse>> getUsersWithAgeAtLeast(@RequestParam int minimumAge) {
        List<User> users = userService.getUsersWithAgeAtLeast(minimumAge);

        List<UserResponse> response = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/sorted/salary")
    public ResponseEntity<List<UserResponse>> getUsersSortedBySalaryDesc() {
        List<User> users = userService.getUsersSortedBySalaryDesc();

        List<UserResponse> response = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/users/paged")
    public ResponseEntity<Page<UserResponse>> getUsersPage(Pageable pageable) {
        Page<User> usersPage = userService.getUsersPage(pageable);

        Page<UserResponse> response = usersPage.map(userMapper::toUserResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/search/department")
    public ResponseEntity<List<UserResponse>> getUsersByDepartment(@RequestParam String department) {
        List<User> users = userService.getUsersByDepartment(department);

        List<UserResponse> response = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/users/search/department-and-salary")
    public ResponseEntity<List<UserResponse>>getUsersByDepartmentAndMinimumSalary(
            @RequestParam String department,
            @RequestParam double minimumSalary
    ){
        List<User> users = userService.getUsersByDepartmentAndMinimumSalary(department, minimumSalary);

        List<UserResponse> response = users.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}