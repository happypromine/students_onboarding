package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.ModuleProgress;
import com.web.students_onboarding.model.Module;
import com.web.students_onboarding.model.Task;
import com.web.students_onboarding.model.TaskProgress;
import com.web.students_onboarding.model.User;
import com.web.students_onboarding.service.MessageService;
import com.web.students_onboarding.service.ModuleService;
import com.web.students_onboarding.service.TaskService;
import com.web.students_onboarding.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final UserService userService;
    private final ModuleService moduleService;
    private final TaskService taskService;
    private final MessageService messageService;

    public StudentController(UserService userService, ModuleService moduleService, 
                           TaskService taskService, MessageService messageService) {
        this.userService = userService;
        this.moduleService = moduleService;
        this.taskService = taskService;
        this.messageService = messageService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("userLevel", moduleService.getUserLevel(user));

        List<Module> modules = moduleService.getAllModules();
        model.addAttribute("modules", modules);
        model.addAttribute("moduleProgress", moduleService.getModulesProgress(user));

        List<Task> tasks = taskService.getAllTasks();
        Map<Long, TaskProgress> taskProgress = new HashMap<>();
        for (Task task : tasks) {
            taskProgress.put(task.getTaskId(), 
                taskService.getTaskProgress(user, task.getTaskId()));
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskProgress", taskProgress);

        List<User> groupUsers = userService.getUsersByGroup(user.getGroup().getGroupId());
        model.addAttribute("groupUsers", groupUsers);

        Map<Long, Long> unreadCounts = new HashMap<>();
        for (User groupUser : groupUsers) {
            unreadCounts.put(groupUser.getUserId(), messageService.getUnreadMessagesCount(user, groupUser));
        }
        model.addAttribute("unreadCounts", unreadCounts);
        
        return "student/dashboard";
    }
} 