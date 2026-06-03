package com.nicolae.userapi.service;

import com.nicolae.userapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("Nicu", 25, 1500));
        users.add(new User("Dubu", 35, 1000));
        users.add(new User("Bubu", 38, 500));
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    public User addUser(User user) {
        users.add(user);
        return user;
    }
}