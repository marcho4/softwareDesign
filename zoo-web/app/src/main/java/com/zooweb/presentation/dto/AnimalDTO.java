package com.zooweb.presentation.dto;

import com.zooweb.domain.value_objects.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AnimalDTO {
    private UUID id;
    private Species species;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Food favoriteFood;
    private HealthStatus healthStatus;
    private UUID enclosureId;
}