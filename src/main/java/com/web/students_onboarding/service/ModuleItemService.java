package com.web.students_onboarding.service;

import com.web.students_onboarding.model.ModuleItem;
import com.web.students_onboarding.repository.ModuleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleItemService {
    private final ModuleItemRepository moduleItemRepository;

    @Autowired
    public ModuleItemService(ModuleItemRepository moduleItemRepository) {
        this.moduleItemRepository = moduleItemRepository;
    }

    public List<ModuleItem> getAllModuleItems() {
        return moduleItemRepository.findAll();
    }

    public Optional<ModuleItem> getModuleItemById(Long id) {
        return moduleItemRepository.findById(id);
    }

    public List<ModuleItem> getModuleItemsByModuleId(Long moduleId) {
        return moduleItemRepository.findByModuleModuleId(moduleId);
    }

    public ModuleItem createModuleItem(ModuleItem moduleItem) {
        moduleItem.setCreatedAt(LocalDateTime.now());
        moduleItem.setUpdatedAt(LocalDateTime.now());
        return moduleItemRepository.save(moduleItem);
    }

    public ModuleItem updateModuleItem(Long id, ModuleItem moduleItemDetails) {
        ModuleItem moduleItem = moduleItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModuleItem not found with id: " + id));

        moduleItem.setTitle(moduleItemDetails.getTitle());
        moduleItem.setDescription(moduleItemDetails.getDescription());
        moduleItem.setContent(moduleItemDetails.getContent());
        moduleItem.setPoints(moduleItemDetails.getPoints());
        moduleItem.setModule(moduleItemDetails.getModule());
        moduleItem.setUpdatedAt(LocalDateTime.now());
        
        return moduleItemRepository.save(moduleItem);
    }

    public void deleteModuleItem(Long id) {
        ModuleItem moduleItem = moduleItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModuleItem not found with id: " + id));
        moduleItemRepository.delete(moduleItem);
    }
} 