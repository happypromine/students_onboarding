package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.User;
import com.web.students_onboarding.model.StudyGroup;
import com.web.students_onboarding.service.UserService;
import com.web.students_onboarding.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, StudyGroupRepository studyGroupRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.studyGroupRepository = studyGroupRepository;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("studyGroups", studyGroupRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Long studyGroup,
            Model model) {

        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            model.addAttribute("studyGroups", studyGroupRepository.findAll());
            return "register";
        }

        userService.registerUser(username, email, password, firstName, lastName, studyGroup);
        return "login";
    }
}
