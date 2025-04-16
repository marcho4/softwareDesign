package com.zooweb.application;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.entities.Animal;
import com.zooweb.domain.repositories.IAnimalRepository;
import com.zooweb.domain.repositories.IFeedingScheduleRepository;
import com.zooweb.domain.services.IAnimalService;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.Gender;
import com.zooweb.domain.value_objects.HealthStatus;
import com.zooweb.domain.value_objects.Species;

@Service
public class AnimalService implements IAnimalService {
    
    private final IAnimalRepository animalRepository;
    private final IFeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    public AnimalService(IAnimalRepository animalRepository, IFeedingScheduleRepository feedingScheduleRepository) {
        this.animalRepository = animalRepository;
        this.feedingScheduleRepository = feedingScheduleRepository;
    }

    @Override
    public IAnimal getAnimal(UUID id) {
        IAnimal animal = animalRepository.getAnimal(id);
        if (animal == null) {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }
        return animal;
    }

    @Override
    public void removeAnimalFromEnclosure(UUID id) {
        IAnimal animal = animalRepository.getAnimal(id);
        if (animal == null) {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }
        if (animal.getEnclosure() != null) {
            animal.getEnclosure().removeAnimal(animal);
        } else {
            throw new RuntimeException("Животное с id " + id + " не находится в вольере");
        }
    }

    @Override
    public void feedAnimal(UUID id, Food food) {
        IAnimal animal = animalRepository.getAnimal(id);
        if (animal != null) {
            animal.feed(food);
        } else {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }
    }

    @Override
    public void healAnimal(UUID id) {
        IAnimal animal = animalRepository.getAnimal(id);
        if (animal != null) {
            animal.heal();
        } else {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }
    }

    @Override
    public IAnimal createAnimal(Species species, String name, LocalDate birthDate, Gender gender, Food favoriteFood, HealthStatus healthStatus) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Имя животного не может быть пустым");
        }
        if (species == null) {
            throw new RuntimeException("Вид животного не может быть пустым");
        }
        if (birthDate == null) {
            throw new RuntimeException("Дата рождения животного не может быть пустой");
        }
        if (gender == null) {
            throw new RuntimeException("Пол животного не может быть пустым");
        }
        if (favoriteFood == null) {
            throw new RuntimeException("Любимое питание животного не может быть пустым");
        }
        if (healthStatus == null) {
            throw new RuntimeException("Здоровье животного не может быть пустым");
        }
        Animal animal = new Animal(species, name, birthDate, gender, favoriteFood, healthStatus);
        animalRepository.addAnimal(animal);
        return animal;
    }

    @Override
    public void deleteAnimal(UUID id) {
        IAnimal animal = animalRepository.getAnimal(id);
        if (animal == null) {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }
        animalRepository.removeAnimal(id);
        List<IFeedingSchedule> schedules = feedingScheduleRepository.getFeedingSchedulesByAnimal(id);
        for (IFeedingSchedule schedule : schedules) {
            feedingScheduleRepository.removeFeedingSchedule(schedule);
        }
    }

    @Override
    public List<IFeedingSchedule> getAnimalSchedule(UUID id) {
        IAnimal animal = animalRepository.getAnimal(id);
        if (animal == null) {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }
        return feedingScheduleRepository.getFeedingSchedulesByAnimal(id);
    }
} 