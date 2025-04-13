package com.zooweb.domain;

import com.zooweb.domain.value_objects.*;

import java.time.LocalDate;
import java.util.UUID;
public interface IAnimal {
    public void feed(Food food);
    public void move(IEnclosure newEnclosure);
    public void heal();

    public Species getSpecies();
    public String getName();
    public LocalDate getBirthDate();
    public Gender getGender();
    public Food getFavoriteFood();
    public HealthStatus getHealthStatus();
    public IEnclosure getEnclosure();
    public UUID getId();
}
