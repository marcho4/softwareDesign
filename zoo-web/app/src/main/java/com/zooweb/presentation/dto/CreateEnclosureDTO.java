package com.zooweb.presentation.dto;

import com.zooweb.domain.value_objects.Species;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateEnclosureDTO {

    private static final String allowedSpecies = "MAMMALS, BIRDS, REPTILES, PREDATORS, HERBIVOROUS, FISH, AMPHIBIANS, INSECTS, OTHER";

    @NotNull(message = "Species is required. Allowed species: " + allowedSpecies)
    private Species species;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotNull(message = "Width is required")
    @Positive(message = "Width must be positive")
    private int width;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be positive")
    private int height;

    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    private int length;

}
