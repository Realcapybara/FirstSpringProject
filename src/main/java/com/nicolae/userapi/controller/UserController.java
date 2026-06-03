package com.nicolae.userapi.controller;

import com.nicolae.userapi.model.User;
import com.nicolae.userapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        User foundUser = userService.getUserByName(name);

        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundUser);
    }
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);

        return ResponseEntity.status(201).body(savedUser);
    }
}