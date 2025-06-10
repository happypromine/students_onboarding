package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.Module;
import com.web.students_onboarding.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/modules")
public class ModuleRestController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleRestController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        Module module = moduleService.getModuleById(id);
        return module != null ? ResponseEntity.ok(module) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        module.setCreatedAt(java.time.LocalDateTime.now());
        module.setUpdatedAt(java.time.LocalDateTime.now());
        return ResponseEntity.ok(moduleService.createModule(module));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable Long id, @RequestBody Module moduleDetails) {
        try {
            moduleDetails.setUpdatedAt(java.time.LocalDateTime.now());
            Module updatedModule = moduleService.updateModule(id, moduleDetails);
            return ResponseEntity.ok(updatedModule);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        try {
            moduleService.deleteModule(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
