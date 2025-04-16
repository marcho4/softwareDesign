package com.zooweb.domain;

import com.zooweb.domain.value_objects.*;

import java.time.LocalDate;
import java.util.UUID;
public interface IAnimal {
    void feed(Food food);
    void move(IEnclosure newEnclosure);
    void heal();

    Species getSpecies();
    String getName();
    LocalDate getBirthDate();
    Gender getGender();
    Food getFavoriteFood();
    HealthStatus getHealthStatus();
    IEnclosure getEnclosure();
    UUID getId();
}
