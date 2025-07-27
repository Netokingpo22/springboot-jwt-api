package com.ernesto.springboot_jwt_api.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    private UUID userId;
}
