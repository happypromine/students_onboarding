package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.*;
import com.web.students_onboarding.model.Module;
import com.web.students_onboarding.service.ModuleService;
import com.web.students_onboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class ModuleController {

    private final ModuleService moduleService;
    private final UserService userService;

    @Autowired
    public ModuleController(ModuleService moduleService, UserService userService) {
        this.moduleService = moduleService;
        this.userService = userService;
    }

    @GetMapping("/modules")
    public String listModules(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return "redirect:/student/dashboard";
    }

    @GetMapping("/modules/{moduleId}")
    public String viewModule(@PathVariable Long moduleId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        Module module = moduleService.getModuleById(moduleId);
        List<ModuleItem> items = moduleService.getModuleItemsWithProgress(moduleId, user);
        UserLevel userLevel = moduleService.getUserLevel(user);
        
        model.addAttribute("module", module);
        model.addAttribute("items", items);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userLevel", userLevel);
        return "module-details";
    }

    @PostMapping("/modules/{moduleId}/items/{itemId}/complete")
    @ResponseBody
    public String markItemAsCompleted(@PathVariable Long moduleId, @PathVariable Long itemId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        moduleService.markItemAsCompleted(user, itemId);
        return "success";
    }
} 