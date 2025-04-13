package com.zooweb.presentation.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnimalIdDTO {
    @NotNull(message = "Animal ID is required")
    private UUID animalId;
}
