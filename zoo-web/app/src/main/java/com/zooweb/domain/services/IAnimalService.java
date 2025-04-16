package com.zooweb.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.Gender;
import com.zooweb.domain.value_objects.HealthStatus;
import com.zooweb.domain.value_objects.Species;


public interface IAnimalService {
    public IAnimal getAnimal(UUID id);
    public void removeAnimalFromEnclosure(UUID id);
    public void feedAnimal(UUID id, Food food);
    public void healAnimal(UUID id);
    public IAnimal createAnimal(Species species, String name, LocalDate birthDate, Gender gender, Food favoriteFood, HealthStatus healthStatus);
    public void deleteAnimal(UUID id);
    public List<IFeedingSchedule> getAnimalSchedule(UUID id);
}
