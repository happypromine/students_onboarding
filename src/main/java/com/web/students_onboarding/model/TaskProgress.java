package com.web.students_onboarding.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_progress")
@Data
public class TaskProgress {
    public Long getTaskProgressId() {
        return taskProgressId;
    }

    public void setTaskProgressId(Long taskProgressId) {
        this.taskProgressId = taskProgressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
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

    public int getCompletionPercentage() {
        if (task == null || task.getTargetValue() == 0) {
            return 0;
        }
        return (int) ((double) currentValue / task.getTargetValue() * 100);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskProgressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    private int currentValue;
    private boolean isCompleted;
    private LocalDateTime completedAt;
}
