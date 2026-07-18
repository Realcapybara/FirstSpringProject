package com.nicolae.userapi.repository;

import com.nicolae.userapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Test
    void findByNameIgnoreCase_shouldFindUserIgnoringCase() {
        User user = new User("Nicu", 30, 5000, "IT");
        jpaUserRepository.save(user);

        Optional<User> result = jpaUserRepository.findByNameIgnoreCase("nicu");

        assertTrue(result.isPresent());
        assertEquals("Nicu", result.get().getName());
    }

    @Test
    void findBySalaryGreaterThanEqual_shouldReturnUsersWithSalaryAtLeastGivenValue() {
        jpaUserRepository.save(new User("Nicu", 30, 5000, "IT"));
        jpaUserRepository.save(new User("Alex", 22, 4000, "HR"));
        jpaUserRepository.save(new User("Maria", 28, 7000, "IT"));

        List<User> result = jpaUserRepository.findBySalaryGreaterThanEqual(5000);

        assertEquals(2, result.size());

        List<String> names = result.stream()
                .map(User::getName)
                .toList();

        assertTrue(names.contains("Nicu"));
        assertTrue(names.contains("Maria"));
        assertFalse(names.contains("Alex"));
    }

    @Test
    void findByAgeGreaterThanEqual_shouldReturnUsersWithAgeAtLeastGivenValue() {
        jpaUserRepository.save(new User("Nicu", 30, 5000, "IT"));
        jpaUserRepository.save(new User("Alex", 22, 4000, "HR"));
        jpaUserRepository.save(new User("Maria", 28, 7000, "IT"));

        List<User> result = jpaUserRepository.findByAgeGreaterThanEqual(25);

        assertEquals(2, result.size());

        List<String> names = result.stream()
                .map(User::getName)
                .toList();

        assertTrue(names.contains("Nicu"));
        assertTrue(names.contains("Maria"));
        assertFalse(names.contains("Alex"));
    }

    @Test
    void findByDepartmentIgnoreCase_shouldReturnUsersFromDepartmentIgnoringCase() {
        jpaUserRepository.save(new User("Nicu", 30, 5000, "IT"));
        jpaUserRepository.save(new User("Alex", 22, 4000, "HR"));
        jpaUserRepository.save(new User("Maria", 28, 7000, "IT"));

        List<User> result = jpaUserRepository.findByDepartmentIgnoreCase("it");

        assertEquals(2, result.size());

        List<String> names = result.stream()
                .map(User::getName)
                .toList();

        assertTrue(names.contains("Nicu"));
        assertTrue(names.contains("Maria"));
        assertFalse(names.contains("Alex"));
    }

    @Test
    void findAllByOrderBySalaryDesc_shouldReturnUsersSortedBySalaryDescending() {
        jpaUserRepository.save(new User("Nicu", 30, 5000, "IT"));
        jpaUserRepository.save(new User("Alex", 22, 4000, "HR"));
        jpaUserRepository.save(new User("Maria", 28, 7000, "IT"));

        List<User> result = jpaUserRepository.findAllByOrderBySalaryDesc();

        assertEquals("Maria", result.get(0).getName());
        assertEquals("Nicu", result.get(1).getName());
        assertEquals("Alex", result.get(2).getName());
    }
    @Test
    void findByDepartmentAndMinimumSalary_shouldReturnMatchingUsers() {
        jpaUserRepository.save(new User("Nicu", 30, 5000, "IT"));
        jpaUserRepository.save(new User("Maria", 28, 7000, "IT"));
        jpaUserRepository.save(new User("Alex", 22, 8000, "HR"));
        jpaUserRepository.save(new User("George", 35, 4000, "IT"));

        List<User> result = jpaUserRepository
                .findByDepartmentAndMinimumSalary("it", 5000);

        assertEquals(2, result.size());

        List<String> names = result.stream()
                .map(User::getName)
                .toList();

        assertTrue(names.contains("Nicu"));
        assertTrue(names.contains("Maria"));
        assertFalse(names.contains("Alex"));
        assertFalse(names.contains("George"));
    }
}