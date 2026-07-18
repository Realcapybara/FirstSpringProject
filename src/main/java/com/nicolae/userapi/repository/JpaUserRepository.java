package com.nicolae.userapi.repository;

import com.nicolae.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNameIgnoreCase(String name);

    List<User> findBySalaryGreaterThanEqual(double salary);

    List<User> findByAgeGreaterThanEqual(int age);

    List<User> findAllByOrderBySalaryDesc();

    List<User> findByDepartmentIgnoreCase(String department);

    @Query("""
        select user
        from User user
        where lower(user.department) = lower(:department)
          and user.salary >= :minimumSalary
        """)
    List<User> findByDepartmentAndMinimumSalary(
            @Param("department") String department,
            @Param("minimumSalary") double minimumSalary
    );
}

