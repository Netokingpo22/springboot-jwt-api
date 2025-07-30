package com.ernesto.jwtapi.user.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ernesto.jwtapi.task.model.Task;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @Schema(description = "ID único del usuario", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Nombre de usuario único", example = "ernesto123", required = true)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Correo electrónico del usuario", example = "ernesto@example.com", required = true, format = "email")
    private String email;

    @Column(nullable = false, length = 100)
    @Schema(description = "Contraseña cifrada del usuario", example = "hashed_password", required = true)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Lista de tareas asociadas al usuario")
    private List<Task> tasks;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "Fecha y hora de creación del usuario", example = "2025-07-29T15:30:00")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = true, updatable = true)
    @Schema(description = "Fecha y hora de la última actualización del usuario", example = "2025-07-30T10:15:00")
    private LocalDateTime updatedAt;
}
