package com.web.students_onboarding.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_progress")
@Data
public class ItemProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ModuleItem item;

    private boolean isCompleted;
    private LocalDateTime completedAt;

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ModuleItem getItem() {
        return item;
    }

    public void setItem(ModuleItem item) {
        this.item = item;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
