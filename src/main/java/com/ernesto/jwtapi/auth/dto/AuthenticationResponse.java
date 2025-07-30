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
@Schema(name = "AuthenticationResponse", description = "Respuesta que contiene el token JWT generado tras la autenticación")
public class AuthenticationResponse {

    @Schema(description = "Token JWT para autenticación en el sistema", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
