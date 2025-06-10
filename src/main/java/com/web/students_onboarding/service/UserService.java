package com.web.students_onboarding.service;

import com.web.students_onboarding.model.StudyGroup;
import com.web.students_onboarding.model.User;
import com.web.students_onboarding.repository.UserRepository;
import com.web.students_onboarding.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudyGroupRepository studyGroupRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, StudyGroupRepository studyGroupRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studyGroupRepository = studyGroupRepository;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserByUsername(String username){return  userRepository.findByUsername(username);}
    public User getUserById(Long id){return userRepository.getUserByUserId(id);}
    public List<User> getAllUsersByRole(String role){return userRepository.findAllByRole(role);}
    
    public User registerUser(String username, String email, String password, String firstName, String lastName, Long studyGroupId) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("student");
        user.setFirstname(firstName);
        user.setLastname(lastName);
        
        StudyGroup group = studyGroupRepository.findById(studyGroupId)
            .orElseThrow(() -> new RuntimeException("Study group not found"));
        user.setGroup(group);

        return userRepository.save(user);
    }

    public List<User> getUsersByGroup(Long groupId) {
        return userRepository.findByGroupGroupId(groupId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        user.setGroup(userDetails.getGroup());

        if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
