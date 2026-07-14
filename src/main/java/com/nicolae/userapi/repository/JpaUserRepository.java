package com.nicolae.userapi.repository;

import com.nicolae.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNameIgnoreCase(String name);

    List<User> findBySalaryGreaterThanEqual(double salary);

    List<User> findByAgeGreaterThanEqual(int age);

    List<User> findAllByOrderBySalaryDesc();

    List<User> findByDepartmentIgnoreCase(String department);

    List<User> findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual(String department, double salary);
}

