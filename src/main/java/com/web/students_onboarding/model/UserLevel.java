package com.web.students_onboarding.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_level")
@Data
public class UserLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userLevelId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private int level;
    private int totalPoints;
    private LocalDateTime updatedAt;

    public void addPoints(int points) {
        this.totalPoints += points;
        this.level = calculateLevel(this.totalPoints);
        this.updatedAt = LocalDateTime.now();
    }

    private int calculateLevel(int points) {
        // Формула для расчета уровня: каждый уровень требует 100 очков
        return (points / 100) + 1;
    }

    public int getPointsToNextLevel() {
        int pointsForCurrentLevel = (level - 1) * 100;
        return 100 - (totalPoints - pointsForCurrentLevel);
    }

    public Long getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(Long userLevelId) {
        this.userLevelId = userLevelId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
