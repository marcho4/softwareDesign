package com.zooweb.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zooweb.domain.value_objects.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateAnimalDTO {
    @NotNull(message = "Species is required")
    private Species species;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Birth date is required. Format: YYYY-MM-DD")
    @Past(message = "Birth date must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    @NotNull(message = "Gender is required")
    private Gender gender;
    
    @NotNull(message = "Favorite food is required")
    private Food favoriteFood;
    
    @NotNull(message = "Health status is required. It can be 'HEALTHY' or 'ILL'")
    private HealthStatus healthStatus;
} 