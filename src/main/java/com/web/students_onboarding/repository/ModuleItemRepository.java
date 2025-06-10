package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.ModuleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleItemRepository extends JpaRepository<ModuleItem, Long> {
    List<ModuleItem> findByModuleModuleId(Long moduleId);
}

