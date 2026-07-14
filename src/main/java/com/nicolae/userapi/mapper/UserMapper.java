package com.nicolae.userapi.mapper;

import com.nicolae.userapi.model.CreateUserRequest;
import com.nicolae.userapi.model.User;
import com.nicolae.userapi.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(CreateUserRequest request) {
        return new User(
                request.getName(),
                request.getAge(),
                request.getSalary(),
                request.getDepartment()
        );
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getSalary(),
                user.getDepartment()
        );
    }
}