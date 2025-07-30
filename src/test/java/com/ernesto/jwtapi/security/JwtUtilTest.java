package com.ernesto.jwtapi.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();

        Field secretField = JwtUtil.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, "miXsuperXclaveXsuperXsecretaXsegura123456");

        Field expirationField = JwtUtil.class.getDeclaredField("jwtExpiration");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, 3600000);
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        var auth = new UsernamePasswordAuthenticationToken("usuarioPrueba", "password");
        String token = jwtUtil.generateToken(auth);
        assertNotNull(token);
    }

    @Test
    void getUsernameFromToken_shouldReturnCorrectUsername() {
        var auth = new UsernamePasswordAuthenticationToken("usuarioPrueba", "password");
        String token = jwtUtil.generateToken(auth);
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("usuarioPrueba", username);
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        var auth = new UsernamePasswordAuthenticationToken("usuarioPrueba", "password");
        String token = jwtUtil.generateToken(auth);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void validateToken_shouldReturnFalseForTamperedToken() {
        var auth = new UsernamePasswordAuthenticationToken("usuarioPrueba", "password");
        String token = jwtUtil.generateToken(auth);
        String invalidToken = token + "123";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }
}
