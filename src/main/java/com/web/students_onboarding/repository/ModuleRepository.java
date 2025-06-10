package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    Module findByModuleId(Long id);
}
