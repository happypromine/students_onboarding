package com.web.students_onboarding.controller;

import com.web.students_onboarding.model.StudyGroup;
import com.web.students_onboarding.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/groups")
public class StudyGroupController {

    private final StudyGroupRepository groupRepository;

    @Autowired
    public StudyGroupController(StudyGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public List<StudyGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<StudyGroup> createGroup(@RequestBody StudyGroup group) {
        return ResponseEntity.ok(groupRepository.save(group));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyGroup> getGroupById(@PathVariable Long id) {
        return groupRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyGroup> updateGroup(@PathVariable Long id, @RequestBody StudyGroup groupDetails) {
        return groupRepository.findById(id)
                .map(group -> {
                    group.setGroupName(groupDetails.getGroupName());
                    return ResponseEntity.ok(groupRepository.save(group));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}