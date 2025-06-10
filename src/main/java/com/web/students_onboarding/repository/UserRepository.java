package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByRole(String role);
    boolean existsByUsername(String username);
    User getUserByUserId(Long id);
    List<User> findByGroupGroupId(Long groupId);
}
