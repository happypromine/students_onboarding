package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
