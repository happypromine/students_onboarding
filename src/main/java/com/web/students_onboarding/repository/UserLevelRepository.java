package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLevelRepository extends JpaRepository<UserLevel, Long> {
    Optional<UserLevel> findByUserUserId(Long userId);
}

