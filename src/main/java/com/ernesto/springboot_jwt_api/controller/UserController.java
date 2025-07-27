package com.ernesto.springboot_jwt_api.controller;

import com.ernesto.springboot_jwt_api.dto.RegisterRequest;
import com.ernesto.springboot_jwt_api.model.User;
import com.ernesto.springboot_jwt_api.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        userService.saveUser(request);

        return ResponseEntity.ok("Usuario creado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @Valid @RequestBody RegisterRequest request) {
        Optional<User> updatedUser = userService.updateUser(id, request);
        if (updatedUser.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok("Usuario actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}