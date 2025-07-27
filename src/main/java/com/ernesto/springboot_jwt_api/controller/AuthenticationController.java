package com.ernesto.springboot_jwt_api.controller;

import com.ernesto.springboot_jwt_api.dto.AuthenticationRequest;
import com.ernesto.springboot_jwt_api.dto.AuthenticationResponse;
import com.ernesto.springboot_jwt_api.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

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
