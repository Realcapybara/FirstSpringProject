package com.nicolae.userapi.service;

import com.nicolae.userapi.exception.UserAlreadyExistsException;
import com.nicolae.userapi.model.User;
import com.nicolae.userapi.repository.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nicolae.userapi.exception.UserNotFoundException;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_shouldSaveUser_whenNameDoesNotExist() {
        User user = new User("Nicu", 30, 5000, "IT");

        when(jpaUserRepository.findByNameIgnoreCase("Nicu"))
                .thenReturn(Optional.empty());

        when(jpaUserRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User savedUser = invocation.getArgument(0);
                    savedUser.setId(1L);
                    return savedUser;
                });

        User result = userService.addUser(user);

        assertEquals(1L, result.getId());
        assertEquals("Nicu", result.getName());
        assertEquals(30, result.getAge());
        assertEquals(5000, result.getSalary());
        assertEquals("IT", result.getDepartment());

        verify(jpaUserRepository).findByNameIgnoreCase("Nicu");
        verify(jpaUserRepository).save(user);
    }

    @Test
    void addUser_shouldThrowException_whenNameAlreadyExists() {
        User user = new User("Nicu", 30, 5000, "IT");
        User existingUser = new User("Nicu", 35, 7000, "HR");

        when(jpaUserRepository.findByNameIgnoreCase("Nicu"))
                .thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(user));

        verify(jpaUserRepository).findByNameIgnoreCase("Nicu");
        verify(jpaUserRepository, never()).save(any(User.class));
    }
    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        User user = new User("Nicu", 30, 5000, "IT");
        user.setId(1L);

        when(jpaUserRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Nicu", result.getName());
        assertEquals("IT", result.getDepartment());

        verify(jpaUserRepository).findById(1L);
    }
    @Test
    void getUserById_shouldThrowException_whenUserDoesNotExist() {
        when(jpaUserRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));

        verify(jpaUserRepository).findById(99L);
    }
    @Test
    void updateUserSalary_shouldUpdateSalary_whenUserExists() {
        User user = new User("Nicu", 30, 5000, "IT");
        user.setId(1L);

        when(jpaUserRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(jpaUserRepository.save(user))
                .thenReturn(user);

        User result = userService.updateUserSalary(1L, 7000);

        assertEquals(7000, result.getSalary());

        verify(jpaUserRepository).findById(1L);
        verify(jpaUserRepository).save(user);
    }
    @Test
    void raiseUserSalary_shouldIncreaseSalary_whenUserExists() {
        User user = new User("Nicu", 30, 5000, "IT");
        user.setId(1L);

        when(jpaUserRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(jpaUserRepository.save(user))
                .thenReturn(user);

        User result = userService.raiseUserSalary(1L, 500);

        assertEquals(5500, result.getSalary());

        verify(jpaUserRepository).findById(1L);
        verify(jpaUserRepository).save(user);
    }
    @Test
    void deleteUserById_shouldDeleteUser_whenUserExists() {
        User user = new User("Nicu", 30, 5000, "IT");
        user.setId(1L);

        when(jpaUserRepository.findById(1L))
                .thenReturn(Optional.of(user));

        userService.deleteUserById(1L);

        verify(jpaUserRepository).findById(1L);
        verify(jpaUserRepository).delete(user);
    }
    @Test
    void getUsersByDepartment_shouldReturnUsersFromDepartment() {
        User nicu = new User("Nicu", 30, 5000, "IT");
        User maria = new User("Maria", 28, 7000, "IT");

        when(jpaUserRepository.findByDepartmentIgnoreCase("IT"))
                .thenReturn(List.of(nicu, maria));

        List<User> result = userService.getUsersByDepartment("IT");

        assertEquals(2, result.size());
        assertEquals("Nicu", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());

        verify(jpaUserRepository).findByDepartmentIgnoreCase("IT");
    }
    @Test
    void getUsersByDepartmentAndMinimumSalary_shouldReturnMatchingUsers() {
        User nicu = new User("Nicu", 30, 5000, "IT");
        User maria = new User("Maria", 28, 7000, "IT");

        when(jpaUserRepository.findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual("IT", 5000))
                .thenReturn(List.of(nicu, maria));

        List<User> result = userService.getUsersByDepartmentAndMinimumSalary("IT", 5000);

        assertEquals(2, result.size());
        assertEquals("Nicu", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());

        verify(jpaUserRepository)
                .findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual("IT", 5000);
    }
    @Test
    void getUsers_shouldUseCombinedQuery_whenBothFiltersExist() {
        User nicu = new User("Nicu", 30, 5000, "IT");
        User maria = new User("Maria", 28, 7000, "IT");

        when(jpaUserRepository
                .findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual("IT", 5000))
                .thenReturn(List.of(nicu, maria));

        List<User> result = userService.getUsers("IT", 5000.0);

        assertEquals(2, result.size());

        verify(jpaUserRepository)
                .findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual("IT", 5000);
    }
}