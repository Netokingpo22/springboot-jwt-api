package com.ernesto.springboot_jwt_api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "task_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @Schema(description = "ID único de la tarea", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Título descriptivo de la tarea", example = "Comprar comida", required = true)
    private String title;

    @Column(nullable = false, length = 500)
    @Schema(description = "Descripción detallada de la tarea", example = "Comprar verduras, frutas y carne para la semana", required = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @Schema(description = "Usuario al que pertenece esta tarea")
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "Fecha y hora en que se creó la tarea", example = "2025-07-29T15:30:00")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = true, updatable = true)
    @Schema(description = "Fecha y hora de la última actualización de la tarea", example = "2025-07-30T10:15:00")
    private LocalDateTime updatedAt;
}
