package com.zooweb.presentation.dto;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.value_objects.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AnimalDTO {
    public AnimalDTO(IAnimal animal) {
        this.id = animal.getId();
        this.species = animal.getSpecies();
        this.name = animal.getName();
        this.birthDate = animal.getBirthDate();
        this.gender = animal.getGender();
        this.favoriteFood = animal.getFavoriteFood();
        this.healthStatus = animal.getHealthStatus();
        if (animal.getEnclosure() != null) {
            this.enclosureId = animal.getEnclosure().getId();
        } else {
            this.enclosureId = null;
        }
    }
    private UUID id;
    private Species species;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Food favoriteFood;
    private HealthStatus healthStatus;
    private UUID enclosureId;
}