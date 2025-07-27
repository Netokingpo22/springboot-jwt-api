package com.ernesto.springboot_jwt_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ernesto.springboot_jwt_api.dto.TaskRequest;
import com.ernesto.springboot_jwt_api.model.Task;
import com.ernesto.springboot_jwt_api.model.User;
import com.ernesto.springboot_jwt_api.service.TaskService;
import com.ernesto.springboot_jwt_api.service.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createTaskForUser(@PathVariable UUID userId,
            @Valid @RequestBody TaskRequest taskRequest) {
        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = existingUser.get();
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .user(user)
                .build();
        taskService.saveTask(task);
        return ResponseEntity.ok("Task created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable UUID id, @Valid @RequestBody TaskRequest taskRequest) {
        Optional<Task> existingTask = taskService.getTaskById(id);
        if (existingTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Task task = existingTask.get();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        taskService.saveTask(task);
        return ResponseEntity.ok("Task updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
}
