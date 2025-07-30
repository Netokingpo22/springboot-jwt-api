package com.ernesto.springboot_jwt_api.controller;

import com.ernesto.springboot_jwt_api.dto.RegisterRequest;
import com.ernesto.springboot_jwt_api.model.User;
import com.ernesto.springboot_jwt_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "CRUD de usuarios y registro")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista con todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class, type = "array")))
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario por su ID único")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID del usuario a buscar", required = true) @PathVariable UUID id) {

        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados")
    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content)
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Parameter(description = "Datos del usuario para registrar", required = true) @Valid @RequestBody RegisterRequest request) {

        userService.saveUser(request);
        return ResponseEntity.ok("Usuario creado");
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente por ID")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable UUID id,
            @Parameter(description = "Nuevos datos del usuario", required = true) @Valid @RequestBody RegisterRequest request) {

        Optional<User> updatedUser = userService.updateUser(id, request);
        if (updatedUser.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok("Usuario actualizado");
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente por ID")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable UUID id) {

        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}