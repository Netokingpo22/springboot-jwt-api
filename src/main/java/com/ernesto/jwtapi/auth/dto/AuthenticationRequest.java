package com.ernesto.jwtapi.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(name = "AuthenticationRequest", description = "Datos para realizar autenticación de usuario")
public class AuthenticationRequest {

    @Schema(description = "Nombre de usuario para autenticarse", example = "ernesto")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;
}
