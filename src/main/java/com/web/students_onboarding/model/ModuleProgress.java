package com.web.students_onboarding.model;

public class ModuleProgress {
    private final long totalItems;
    private final long completedItems;

    public ModuleProgress(long totalItems, long completedItems) {
        this.totalItems = totalItems;
        this.completedItems = completedItems;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public long getCompletedItems() {
        return completedItems;
    }

    public int getCompletionPercentage() {
        if (totalItems == 0) return 0;
        return (int) ((completedItems * 100) / totalItems);
    }
} 