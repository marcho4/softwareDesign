package com.zooweb;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.Gender;
import com.zooweb.domain.value_objects.HealthStatus;
import com.zooweb.domain.value_objects.Species;
import com.zooweb.domain.services.*;

import com.zooweb.presentation.dto.CreateAnimalDTO;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimalTests 
{

    @Autowired
    private IAnimalService animalService;

    @Autowired
    private IFeedingOrganizationService feedingOrganizationService;


    private IAnimal animal;

    @Test
    public void AnimalCreateTest()
    {
        CreateAnimalDTO animalCreateDTO = new CreateAnimalDTO();
        animalCreateDTO.setName("Barsik");
        animalCreateDTO.setSpecies(Species.REPTILES);
        animalCreateDTO.setGender(Gender.MALE);
        animalCreateDTO.setBirthDate(LocalDate.now());
        animalCreateDTO.setFavoriteFood(Food.MEAT);
        animalCreateDTO.setHealthStatus(HealthStatus.HEALTHY);

        IAnimal animal = animalService.createAnimal(animalCreateDTO.getSpecies(), animalCreateDTO.getName(), animalCreateDTO.getBirthDate(), animalCreateDTO.getGender(), animalCreateDTO.getFavoriteFood(), animalCreateDTO.getHealthStatus());
        assertNotNull(animal);
        this.animal = animal;
    
    }

    @Test(expected = RuntimeException.class)
    public void Wrong_AnimalCreateTest()
    {
        CreateAnimalDTO animalCreateDTO_2 = new CreateAnimalDTO();
        animalCreateDTO_2.setName("Бобик");
        animalCreateDTO_2.setSpecies(Species.REPTILES);
        animalCreateDTO_2.setGender(Gender.MALE);

        animalService.createAnimal(animalCreateDTO_2.getSpecies(), animalCreateDTO_2.getName(), animalCreateDTO_2.getBirthDate(), animalCreateDTO_2.getGender(), animalCreateDTO_2.getFavoriteFood(), animalCreateDTO_2.getHealthStatus());
    }

    @Test
    public void AnimalGetTest()
    {
        CreateAnimalDTO animalCreateDTO = new CreateAnimalDTO();
        animalCreateDTO.setName("мой любимый кот");
        animalCreateDTO.setSpecies(Species.MAMMALS);
        animalCreateDTO.setGender(Gender.FEMALE);
        animalCreateDTO.setBirthDate(LocalDate.now().minusYears(5));
        animalCreateDTO.setFavoriteFood(Food.VEGETABLES);
        animalCreateDTO.setHealthStatus(HealthStatus.ILL);

        IAnimal animal = animalService.createAnimal(animalCreateDTO.getSpecies(), animalCreateDTO.getName(), animalCreateDTO.getBirthDate(), animalCreateDTO.getGender(), animalCreateDTO.getFavoriteFood(), animalCreateDTO.getHealthStatus());

        assertNotNull(animal);

        this.animal = animal;
        assertNotNull(this.animal);

        IAnimal animal_2 = animalService.getAnimal(this.animal.getId());
        assertNotNull(animal_2);
    }

    @Test
    public void AnimalDeleteTest() {
        CreateAnimalDTO animalCreateDTO = new CreateAnimalDTO();
        animalCreateDTO.setName("мой любимый кот");
        animalCreateDTO.setSpecies(Species.MAMMALS);
        animalCreateDTO.setGender(Gender.FEMALE);
        animalCreateDTO.setBirthDate(LocalDate.now().minusYears(5));
        animalCreateDTO.setFavoriteFood(Food.VEGETABLES);
        animalCreateDTO.setHealthStatus(HealthStatus.ILL);

        IAnimal animal = animalService.createAnimal(animalCreateDTO.getSpecies(), animalCreateDTO.getName(), animalCreateDTO.getBirthDate(), animalCreateDTO.getGender(), animalCreateDTO.getFavoriteFood(), animalCreateDTO.getHealthStatus());
        UUID id = animal.getId();
        animalService.deleteAnimal(id);

        try {
            animalService.getAnimal(id);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void AnimalHealTest() {
        CreateAnimalDTO animalCreateDTO = new CreateAnimalDTO();
        animalCreateDTO.setName("мой любимый кот");
        animalCreateDTO.setSpecies(Species.MAMMALS);
        animalCreateDTO.setGender(Gender.FEMALE);
        animalCreateDTO.setBirthDate(LocalDate.now().minusYears(5));
        animalCreateDTO.setFavoriteFood(Food.VEGETABLES);
        animalCreateDTO.setHealthStatus(HealthStatus.ILL);

        IAnimal animal = animalService.createAnimal(animalCreateDTO.getSpecies(), animalCreateDTO.getName(), animalCreateDTO.getBirthDate(), animalCreateDTO.getGender(), animalCreateDTO.getFavoriteFood(), animalCreateDTO.getHealthStatus());
        
        animalService.healAnimal(animal.getId());
        assertTrue(animalService.getAnimal(animal.getId()).getHealthStatus() == HealthStatus.HEALTHY);

        try {
            animalService.healAnimal(animal.getId());
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void AnimalGetScheduleTest() {
        CreateAnimalDTO animalCreateDTO = new CreateAnimalDTO();
        animalCreateDTO.setName("мой любимый кот");
        animalCreateDTO.setSpecies(Species.MAMMALS);
        animalCreateDTO.setGender(Gender.FEMALE);
        animalCreateDTO.setBirthDate(LocalDate.now().minusYears(5));
        animalCreateDTO.setFavoriteFood(Food.VEGETABLES);
        animalCreateDTO.setHealthStatus(HealthStatus.ILL);


        IAnimal animal = animalService.createAnimal(
            animalCreateDTO.getSpecies(), 
            animalCreateDTO.getName(), 
            animalCreateDTO.getBirthDate(),
            animalCreateDTO.getGender(), 
            animalCreateDTO.getFavoriteFood(),
            animalCreateDTO.getHealthStatus()
        );

        UUID id = animal.getId();
        assertNotNull(id);

        feedingOrganizationService.createFeedingSchedule(id, DayOfWeek.MONDAY, LocalTime.of(10, 0), Food.GRAINS);

        List<IFeedingSchedule> schedule = animalService.getAnimalSchedule(id);
        assertNotNull(schedule);
    }
}
