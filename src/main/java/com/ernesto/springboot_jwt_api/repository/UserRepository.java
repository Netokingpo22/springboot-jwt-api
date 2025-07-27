package com.ernesto.springboot_jwt_api.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ernesto.springboot_jwt_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);
}
