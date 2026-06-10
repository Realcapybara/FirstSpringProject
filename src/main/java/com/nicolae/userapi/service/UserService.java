package com.nicolae.userapi.service;

import com.nicolae.userapi.model.User;
import com.nicolae.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserSalary(String name, double newSalary) {
        User user = userRepository.findByName(name);

        if (user == null) {
            return null;
        }

        user.setSalary(newSalary);
        return user;
    }

    public boolean deleteUserByName(String name) {
        return userRepository.deleteByName(name);
    }

    public User raiseUserSalary(String name, double amount) {
        User user = userRepository.findByName(name);

        if (user == null) {
            return null;
        }

        user.raiseSalary(amount);
        return user;
    }
}