package com.web.students_onboarding.service;

import com.web.students_onboarding.model.*;
import com.web.students_onboarding.model.Module;
import com.web.students_onboarding.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleItemRepository moduleItemRepository;
    private final ItemProgressRepository itemProgressRepository;
    private final UserLevelRepository userLevelRepository;
    private final TaskService taskService;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, ModuleItemRepository moduleItemRepository, ItemProgressRepository itemProgressRepository, UserLevelRepository userLevelRepository, TaskService taskService) {
        this.moduleRepository = moduleRepository;
        this.moduleItemRepository = moduleItemRepository;
        this.itemProgressRepository = itemProgressRepository;
        this.userLevelRepository = userLevelRepository;
        this.taskService = taskService;
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module getModuleById(Long id) {
        return moduleRepository.findByModuleId(id);
    }

    public List<ModuleItem> getModuleItems(Long moduleId) {
        return moduleItemRepository.findByModuleModuleId(moduleId);
    }

    public List<ModuleItem> getModuleItemsWithProgress(Long moduleId, User user) {
        List<ModuleItem> moduleItems = moduleItemRepository.findByModuleModuleId(moduleId);

        List<ItemProgress> userProgress = itemProgressRepository.findByUserUserId(user.getUserId());

        Map<Long, Boolean> completedItems = new HashMap<>();
        for (ItemProgress progress : userProgress) {
            completedItems.put(progress.getItem().getItemId(), progress.isCompleted());
        }

        for (ModuleItem item : moduleItems) {
            boolean isCompleted = completedItems.getOrDefault(item.getItemId(), false);
            item.setCompleted(isCompleted);
        }
        
        return moduleItems;
    }

    @Transactional
    public void markItemAsCompleted(User user, Long itemId) {
        ModuleItem item = moduleItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        ItemProgress progress = itemProgressRepository
                .findByUserUserIdAndItemItemId(user.getUserId(), itemId)
                .orElseGet(() -> {
                    ItemProgress newProgress = new ItemProgress();
                    newProgress.setUser(user);
                    newProgress.setItem(item);
                    return newProgress;
                });

        if (!progress.isCompleted()) {
            progress.setCompleted(true);
            progress.setCompletedAt(java.time.LocalDateTime.now());
            itemProgressRepository.save(progress);

            addPointsToUser(user, item.getPoints());
            boolean isModuleCompleted = isModuleCompleted(user, item.getModule().getModuleId());
            Map<TaskType, Integer> progressUpdates = new HashMap<>();
            progressUpdates.put(TaskType.module_completion, isModuleCompleted ? 1 : 0);
            progressUpdates.put(TaskType.points_earned, item.getPoints());
            taskService.updateUserProgress(user, progressUpdates);
        }
    }

    private boolean isModuleCompleted(User user, Long moduleId) {
        List<ModuleItem> items = moduleItemRepository.findByModuleModuleId(moduleId);
        long completedItems = itemProgressRepository.countByUserUserIdAndItemModuleModuleIdAndIsCompletedTrue(user.getUserId(), moduleId);
        return items.size() == completedItems;
    }

    private void addPointsToUser(User user, int points) {
        UserLevel userLevel = userLevelRepository.findByUserUserId(user.getUserId())
                .orElseGet(() -> {
                    UserLevel newLevel = new UserLevel();
                    newLevel.setUser(user);
                    newLevel.setLevel(1);
                    newLevel.setTotalPoints(0);
                    newLevel.setUpdatedAt(java.time.LocalDateTime.now());
                    return userLevelRepository.save(newLevel);
                });

        userLevel.addPoints(points);
        userLevelRepository.save(userLevel);
    }

    public UserLevel getUserLevel(User user) {
        return userLevelRepository.findByUserUserId(user.getUserId())
                .orElseGet(() -> {
                    UserLevel newLevel = new UserLevel();
                    newLevel.setUser(user);
                    newLevel.setLevel(1);
                    newLevel.setTotalPoints(0);
                    newLevel.setUpdatedAt(java.time.LocalDateTime.now());
                    return userLevelRepository.save(newLevel);
                });
    }

    public Map<Long, ModuleProgress> getModulesProgress(User user) {
        List<Module> modules = moduleRepository.findAll();
        Map<Long, ModuleProgress> progressMap = new java.util.HashMap<>();

        for (Module module : modules) {
            List<ModuleItem> items = moduleItemRepository.findByModuleModuleId(module.getModuleId());
            long totalItems = items.size();
            long completedItems = itemProgressRepository.countByUserUserIdAndItemModuleModuleIdAndIsCompletedTrue(user.getUserId(), module.getModuleId());

            ModuleProgress progress = new ModuleProgress(totalItems, completedItems);
            progressMap.put(module.getModuleId(), progress);
        }

        return progressMap;
    }

    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public Module updateModule(Long id, Module moduleDetails) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));
        
        module.setTitle(moduleDetails.getTitle());
        module.setDescription(moduleDetails.getDescription());
        
        return moduleRepository.save(module);
    }

    public void deleteModule(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));
        moduleRepository.delete(module);
    }
}