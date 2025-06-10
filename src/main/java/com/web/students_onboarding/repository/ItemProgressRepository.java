package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.ItemProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemProgressRepository extends JpaRepository<ItemProgress, Long> {
    List<ItemProgress> findByUserUserId(Long userId);
    Optional<ItemProgress> findByUserUserIdAndItemItemId(Long userId, Long itemId);
    long countByUserUserIdAndItemModuleModuleIdAndIsCompletedTrue(Long userId, Long moduleId);
}
