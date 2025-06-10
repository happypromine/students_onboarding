package com.web.students_onboarding.service;

import com.web.students_onboarding.model.*;
import com.web.students_onboarding.repository.TaskRepository;
import com.web.students_onboarding.repository.TaskProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskProgressRepository taskProgressRepository;

    @Autowired
    public TaskService(TaskProgressRepository taskProgressRepository, TaskRepository taskRepository) {
        this.taskProgressRepository = taskProgressRepository;
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setTaskType(taskDetails.getTaskType());
        
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskRepository.delete(task);
    }

    @Transactional
    public void updateUserProgress(User user, Map<TaskType, Integer> progressUpdates) {
        List<TaskProgress> userTasks = taskProgressRepository.findByUserUserId(user.getUserId());
        if (userTasks.isEmpty()) {
            initializeUserTasks(user);
            userTasks = taskProgressRepository.findByUserUserId(user.getUserId());
        }
        
        for (TaskProgress taskProgress : userTasks) {
            Task task = taskProgress.getTask();
            TaskType taskType = task.getTaskType();

            if (progressUpdates.containsKey(taskType)) {
                int updateValue = progressUpdates.get(taskType);
                taskProgress.setCurrentValue(taskProgress.getCurrentValue() + updateValue);
                

                if (!taskProgress.isCompleted() && taskProgress.getCurrentValue() >= task.getTargetValue()) {
                    taskProgress.setCompleted(true);
                    taskProgress.setCompletedAt(LocalDateTime.now());
                }
                
                taskProgressRepository.save(taskProgress);
            }
        }
    }

    public List<TaskProgress> getUserTasks(User user) {
        List<TaskProgress> userTasks = taskProgressRepository.findByUserUserId(user.getUserId());
        if (userTasks.isEmpty()) {
            initializeUserTasks(user);
            userTasks = taskProgressRepository.findByUserUserId(user.getUserId());
        }
        return userTasks;
    }

    @Transactional
    public void initializeUserTasks(User user) {
        List<Task> allTasks = taskRepository.findAll();
        for (Task task : allTasks) {
            if (taskProgressRepository.findByUserUserIdAndTaskTaskId(user.getUserId(), task.getTaskId()).isEmpty()) {
                TaskProgress taskProgress = new TaskProgress();
                taskProgress.setUser(user);
                taskProgress.setTask(task);
                taskProgress.setCurrentValue(0);
                taskProgress.setCompleted(false);
                taskProgressRepository.save(taskProgress);
            }
        }
    }

    public TaskProgress getTaskProgress(User user, Long taskId) {
        return taskProgressRepository.findByUserUserIdAndTaskTaskId(user.getUserId(), taskId)
                .orElseGet(() -> {
                    TaskProgress progress = new TaskProgress();
                    progress.setUser(user);
                    progress.setTask(taskRepository.findById(taskId).orElseThrow());
                    progress.setCurrentValue(0);
                    progress.setCompleted(false);
                    return taskProgressRepository.save(progress);
                });
    }
} 