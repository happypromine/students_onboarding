package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.ModuleItem;
import com.web.students_onboarding.service.ModuleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/module-items")
public class ModuleItemController {
    private final ModuleItemService moduleItemService;

    @Autowired
    public ModuleItemController(ModuleItemService moduleItemService) {
        this.moduleItemService = moduleItemService;
    }

    @GetMapping
    public List<ModuleItem> getAllModuleItems() {
        return moduleItemService.getAllModuleItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleItem> getModuleItemById(@PathVariable Long id) {
        return moduleItemService.getModuleItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public List<ModuleItem> getModuleItemsByModuleId(@PathVariable Long moduleId) {
        return moduleItemService.getModuleItemsByModuleId(moduleId);
    }

    @PostMapping
    public ModuleItem createModuleItem(@RequestBody ModuleItem moduleItem) {
        return moduleItemService.createModuleItem(moduleItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleItem> updateModuleItem(@PathVariable Long id, @RequestBody ModuleItem moduleItemDetails) {
        try {
            ModuleItem updatedModuleItem = moduleItemService.updateModuleItem(id, moduleItemDetails);
            return ResponseEntity.ok(updatedModuleItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleItem(@PathVariable Long id) {
        try {
            moduleItemService.deleteModuleItem(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 