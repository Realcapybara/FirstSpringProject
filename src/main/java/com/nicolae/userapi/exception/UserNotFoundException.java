package com.nicolae.userapi.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String name) {
        super("User not found: " + name);
    }

    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}