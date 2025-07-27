package com.ernesto.springboot_jwt_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ernesto.springboot_jwt_api.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findById(UUID id);
}
