package com.ernesto.jwtapi.task.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "TaskRequest", description = "Datos para crear o actualizar una tarea")
public class TaskRequest {

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Título de la tarea", example = "Comprar comida", required = true)
    private String title;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "Descripción detallada de la tarea", example = "Comprar verduras, frutas y carne para la semana", required = true)
    private String description;

    @Schema(description = "ID del usuario al que se asigna la tarea", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID userId;
}
