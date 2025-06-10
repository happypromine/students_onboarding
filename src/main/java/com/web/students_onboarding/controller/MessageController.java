package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.Message;
import com.web.students_onboarding.model.User;
import com.web.students_onboarding.service.MessageService;
import com.web.students_onboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listMessages(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        List<User> curators = userService.getAllUsersByRole("curator");
        model.addAttribute("curators", curators);
        model.addAttribute("currentUser", currentUser);
        return "messages/list";
    }

    @GetMapping("/{id}")
    public String viewConversation(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        User otherUser = userService.getUserById(id);
        List<Message> messages = messageService.getConversation(currentUser, otherUser);
        
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("otherUser", otherUser);
        return "messages/conversation";
    }

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User sender = userService.getUserByUsername(userDetails.getUsername());
            User receiver = userService.getUserById(Long.parseLong(payload.get("receiverId")));
            String content = payload.get("content");
            
            Message message = messageService.sendMessage(sender, receiver, content);
            return ResponseEntity.ok(Map.of("success", true, "message", message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/unread/count")
    @ResponseBody
    public ResponseEntity<?> getUnreadCount(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        long count = messageService.getUnreadMessagesCount(user);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/{userId}/new")
    @ResponseBody
    public ResponseEntity<?> getNewMessages(
            @PathVariable Long userId,
            @RequestParam Long lastId,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User currentUser = userService.getUserByUsername(userDetails.getUsername());
            User otherUser = userService.getUserById(userId);
            List<Message> messages = messageService.getNewMessages(currentUser, otherUser, lastId);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "messages", messages
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", e.getMessage()
            ));
        }
    }
} 