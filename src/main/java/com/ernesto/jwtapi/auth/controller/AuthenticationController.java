package com.ernesto.jwtapi.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.ernesto.jwtapi.auth.dto.AuthenticationRequest;
import com.ernesto.jwtapi.auth.dto.AuthenticationResponse;
import com.ernesto.jwtapi.security.JwtUtil;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Operaciones de autenticación y generación de JWT")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Login de usuario", description = "Autentica al usuario y genera un token JWT válido", requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = AuthenticationRequest.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));

            String token = jwtUtil.generateToken(authentication);

            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
