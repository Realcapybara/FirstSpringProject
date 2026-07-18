package com.nicolae.userapi.service;

import org.springframework.transaction.annotation.Transactional;
import com.nicolae.userapi.exception.UserAlreadyExistsException;
import com.nicolae.userapi.exception.UserNotFoundException;
import com.nicolae.userapi.model.User;
import com.nicolae.userapi.repository.JpaUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
public class UserService {

    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<User> getUsers() {
        return jpaUserRepository.findAll();
    }

    public User getUserById(Long id) {
        return findUserByIdOrThrow(id);
    }

    @Transactional
    public User addUser(User user) {
        if (jpaUserRepository.findByNameIgnoreCase(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException(user.getName());
        }

        return jpaUserRepository.save(user);
    }

    @Transactional
    public User updateUserSalary(Long id, double newSalary) {
        User user = findUserByIdOrThrow(id);

        user.setSalary(newSalary);

        return user;
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = findUserByIdOrThrow(id);

        jpaUserRepository.delete(user);
    }

    @Transactional
    public User raiseUserSalary(Long id, double amount) {
        User user = findUserByIdOrThrow(id);

        user.raiseSalary(amount);

        return user;
    }

    public List<User> getUsersWithSalaryAtLeast(double minimumSalary) {
        return jpaUserRepository.findBySalaryGreaterThanEqual(minimumSalary);
    }

    private User findUserByIdOrThrow(Long id) {
        return jpaUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getUsersWithAgeAtLeast(int minimumAge) {
        return jpaUserRepository.findByAgeGreaterThanEqual(minimumAge);
    }

    public List<User> getUsersSortedBySalaryDesc() {
        return jpaUserRepository.findAllByOrderBySalaryDesc();
    }
    public Page<User> getUsersPage(Pageable pageable) {
        return jpaUserRepository.findAll(pageable);
    }

    public List<User> getUsersByDepartment(String department) {
        return jpaUserRepository.findByDepartmentIgnoreCase(department);
    }

    public List<User> getUsersByDepartmentAndMinimumSalary(String department, double minimumSalary) {
        return jpaUserRepository.findByDepartmentAndMinimumSalary(department, minimumSalary);
    }

    public List<User> getUsers(String department, Double minimumSalary) {
        boolean hasDepartment = department != null && !department.isBlank();
        boolean hasMinimumSalary = minimumSalary != null;

        if (hasDepartment && hasMinimumSalary) {
            return jpaUserRepository
                    .findByDepartmentAndMinimumSalary(
                            department,
                            minimumSalary
                    );
        }

        if (hasDepartment) {
            return jpaUserRepository.findByDepartmentIgnoreCase(department);
        }

        if (hasMinimumSalary) {
            return jpaUserRepository.findBySalaryGreaterThanEqual(minimumSalary);
        }

        return jpaUserRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = findUserByIdOrThrow(id);

        jpaUserRepository.findByNameIgnoreCase(updatedUser.getName())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(updatedUser.getName());
                });

        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setSalary(updatedUser.getSalary());
        existingUser.setDepartment(updatedUser.getDepartment());

        return existingUser;
    }
}