package com.ernesto.jwtapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "RegisterRequest", description = "Datos para registrar un nuevo usuario")
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50)
    @Schema(description = "Nombre de usuario único", example = "ernesto123", required = true)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100)
    @Schema(description = "Contraseña segura del usuario", example = "MiContraseña123", required = true)
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Correo inválido")
    @Size(max = 100)
    @Schema(description = "Correo electrónico válido", example = "ernesto@example.com", required = true)
    private String email;
}
