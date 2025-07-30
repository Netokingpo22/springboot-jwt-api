package com.ernesto.jwtapi.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ernesto.jwtapi.auth.dto.RegisterRequest;
import com.ernesto.jwtapi.user.model.User;
import com.ernesto.jwtapi.user.repository.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setUsername("ernesto");
        request.setEmail("ernesto@example.com");
        request.setPassword("123456");
    }

    @Test
    void saveUser_ShouldEncodePasswordAndSaveUser() {
        String encodedPassword = "encoded123";
        when(passwordEncoder.encode("123456")).thenReturn(encodedPassword);

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .username("ernesto")
                .email("ernesto@example.com")
                .password(encodedPassword)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.saveUser(request);

        assertNotNull(result);
        assertEquals("ernesto", result.getUsername());
        assertEquals("ernesto@example.com", result.getEmail());
        assertEquals(encodedPassword, result.getPassword());

        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(User.class));
    }
}
