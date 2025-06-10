package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.*;
import com.web.students_onboarding.service.ModuleService;
import com.web.students_onboarding.service.TaskService;
import com.web.students_onboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    @Autowired
    public TaskController(UserService userService, TaskService taskService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskProgress>> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(taskService.getUserTasks(user));
    }

    @PostMapping("/progress")
    public ResponseEntity<Void> updateProgress(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<TaskType, Integer> progressUpdates) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        taskService.updateUserProgress(user, progressUpdates);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/initialize")
    public ResponseEntity<Void> initializeUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        taskService.initializeUserTasks(user);
        return ResponseEntity.ok().build();
    }

} 