package com.ernesto.jwtapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class JwtRequestFilterTest {

    private JwtUtil mockJwtUtil;
    private UserDetailsService mockUserDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() {
        mockJwtUtil = mock(JwtUtil.class);
        mockUserDetailsService = mock(UserDetailsService.class);
        jwtRequestFilter = new JwtRequestFilter(mockJwtUtil, mockUserDetailsService);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldAuthenticateUser_whenJwtIsValid() throws Exception {
        String token = "valid.jwt.token";
        String username = "testuser";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(mockJwtUtil.getUsernameFromToken(token)).thenReturn(username);
        when(mockJwtUtil.validateToken(token)).thenReturn(true);
        when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        jwtRequestFilter.doFilter(request, response, filterChain);

        verify(mockJwtUtil).getUsernameFromToken(token);
        verify(mockJwtUtil).validateToken(token);
        verify(mockUserDetailsService).loadUserByUsername(username);
        verify(filterChain).doFilter(request, response);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth instanceof UsernamePasswordAuthenticationToken;
        assert auth.getPrincipal().equals(userDetails);
    }
}
