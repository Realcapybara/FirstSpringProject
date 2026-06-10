package com.nicolae.userapi.repository;

import com.nicolae.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User("Nicu", 25, 1500));
        users.add(new User("Dubu", 35, 1000));
        users.add(new User("Bubu", 38, 500));
    }

    public List<User> findAll() {
        return users;
    }

    public User findByName(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public boolean deleteByName(String name) {
        return users.removeIf(user -> user.getName().equalsIgnoreCase(name));
    }
}