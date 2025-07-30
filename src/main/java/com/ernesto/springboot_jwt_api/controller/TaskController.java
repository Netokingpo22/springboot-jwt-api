package com.ernesto.springboot_jwt_api.controller;

import com.ernesto.springboot_jwt_api.dto.TaskRequest;
import com.ernesto.springboot_jwt_api.model.Task;
import com.ernesto.springboot_jwt_api.model.User;
import com.ernesto.springboot_jwt_api.service.TaskService;
import com.ernesto.springboot_jwt_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tareas", description = "Gestión de tareas para usuarios")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener todas las tareas", description = "Retorna todas las tareas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de tareas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class, type = "array")))
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Obtener tarea por ID", description = "Busca una tarea por su ID")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID de la tarea a buscar", required = true) @PathVariable UUID id) {

        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear tarea para un usuario", description = "Crea una nueva tarea asociada a un usuario")
    @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Usuario no encontrado o datos inválidos", content = @Content)
    @PostMapping("/{userId}")
    public ResponseEntity<String> createTaskForUser(
            @Parameter(description = "ID del usuario al que se asigna la tarea", required = true) @PathVariable UUID userId,
            @Parameter(description = "Datos de la tarea", required = true) @Valid @RequestBody TaskRequest taskRequest) {

        Optional<User> existingUser = userService.getUserById(userId);
        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        User user = existingUser.get();
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .user(user)
                .build();
        taskService.saveTask(task);
        return ResponseEntity.ok("Tarea creada exitosamente");
    }

    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea existente por ID")
    @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(
            @Parameter(description = "ID de la tarea a actualizar", required = true) @PathVariable UUID id,
            @Parameter(description = "Nuevos datos de la tarea", required = true) @Valid @RequestBody TaskRequest taskRequest) {

        Optional<Task> existingTask = taskService.getTaskById(id);
        if (existingTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Task task = existingTask.get();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        taskService.saveTask(task);
        return ResponseEntity.ok("Tarea actualizada exitosamente");
    }

    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea por ID")
    @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @Parameter(description = "ID de la tarea a eliminar", required = true) @PathVariable UUID id) {

        taskService.deleteTask(id);
        return ResponseEntity.ok("Tarea eliminada exitosamente");
    }
}