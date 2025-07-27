package com.ernesto.springboot_jwt_api.service;

import com.ernesto.springboot_jwt_api.dto.RegisterRequest;
import com.ernesto.springboot_jwt_api.model.User;
import com.ernesto.springboot_jwt_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User saveUser(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public Optional<User> updateUser(UUID id, RegisterRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return Optional.empty();

        User user = optionalUser.get();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}