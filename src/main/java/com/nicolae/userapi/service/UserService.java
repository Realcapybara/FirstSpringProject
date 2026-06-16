package com.nicolae.userapi.service;

import com.nicolae.userapi.model.User;
import com.nicolae.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.nicolae.userapi.exception.UserNotFoundException;
import com.nicolae.userapi.exception.UserAlreadyExistsException;

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
        return findUserOrThrow(name);
    }

    public User addUser(User user) {
        User existingUser = userRepository.findByName(user.getName());

        if (existingUser != null) {
            throw new UserAlreadyExistsException(user.getName());
        }

        return userRepository.save(user);
    }

    public User updateUserSalary(String name, double newSalary) {
        User user = findUserOrThrow(name);

        user.setSalary(newSalary);
        return user;
    }

    public void deleteUserByName(String name) {
        boolean deleted = userRepository.deleteByName(name);

        if (!deleted) {
            throw new UserNotFoundException(name);
        }
    }

    public User raiseUserSalary(String name, double amount) {
        User user = findUserOrThrow(name);

        user.raiseSalary(amount);
        return user;
    }

    private User findUserOrThrow(String name) {
        User user = userRepository.findByName(name);

        if (user == null) {
            throw new UserNotFoundException(name);
        }

        return user;
    }
}