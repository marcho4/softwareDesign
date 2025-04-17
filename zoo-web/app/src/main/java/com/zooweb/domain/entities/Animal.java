package com.zooweb.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.value_objects.*;

public class Animal implements IAnimal {
    private Species species;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Food favoriteFood;
    private HealthStatus healthStatus;
    private IEnclosure currentEnclosure;
    private UUID id;
    
    public Animal(Species species, String name, LocalDate birthDate, Gender gender, Food favoriteFood, HealthStatus healthStatus) {
        this.species = species;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.favoriteFood = favoriteFood;
        this.healthStatus = healthStatus;
        this.id = UUID.randomUUID();
    }


    public void setSpecies(Species species) {
        this.species = species;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFavoriteFood(Food favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }


    public void feed(Food food) {
        System.out.println("Кормим " + name + " " + food);
    }

    public void move(IEnclosure newEnclosure) {
        currentEnclosure = newEnclosure;
    }

    public void heal() {
        if (healthStatus == HealthStatus.ILL) {
            healthStatus = HealthStatus.HEALTHY;
        } else {
            throw new IllegalStateException("Животное уже здорово");
        }
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public Food getFavoriteFood() {
        return favoriteFood;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;    
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @Override
    public IEnclosure getEnclosure() {
        return currentEnclosure;
    }

    public UUID getId() {
        return id;
    }
}