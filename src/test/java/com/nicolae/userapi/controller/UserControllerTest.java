package com.nicolae.userapi.controller;

import com.nicolae.userapi.mapper.UserMapper;
import com.nicolae.userapi.model.User;
import com.nicolae.userapi.model.UserResponse;
import com.nicolae.userapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.nicolae.userapi.model.CreateUserRequest;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    @Test
    void getUserById_shouldReturnUser_whenUserExists() throws Exception {
        User user = new User("Nicu", 30, 5000, "IT");
        user.setId(1L);

        UserResponse response = new UserResponse(1L, "Nicu", 30, 5000, "IT");

        when(userService.getUserById(1L)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nicu"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.salary").value(5000))
                .andExpect(jsonPath("$.department").value("IT"));
    }

    @Test
    void addUser_shouldReturnCreatedUser_whenRequestIsValid() throws Exception {
        User user = new User("Nicu", 30, 5000, "IT");

        User savedUser = new User("Nicu", 30, 5000, "IT");
        savedUser.setId(1L);

        UserResponse response = new UserResponse(1L, "Nicu", 30, 5000, "IT");

        when(userMapper.toUser(any(CreateUserRequest.class))).thenReturn(user);
        when(userService.addUser(user)).thenReturn(savedUser);
        when(userMapper.toUserResponse(savedUser)).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Nicu",
                              "age": 30,
                              "salary": 5000,
                              "department": "IT"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nicu"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.salary").value(5000))
                .andExpect(jsonPath("$.department").value("IT"));
    }
    @Test
    void addUser_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "",
                              "age": 15,
                              "salary": -100,
                              "department": ""
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray());
    }
}