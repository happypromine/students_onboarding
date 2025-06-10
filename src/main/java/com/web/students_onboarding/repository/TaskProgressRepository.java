package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.TaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Long> {
    List<TaskProgress> findByUserUserId(Long userId);
    Optional<TaskProgress> findByUserUserIdAndTaskTaskId(Long userId, Long taskId);
}
