package com.zooweb;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.zooweb.application.AnimalService;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.services.IFeedingOrganizationService;
import com.zooweb.presentation.dto.CreateFeedingScheduleDTO;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedingTests {

    @Autowired
    private IFeedingOrganizationService feedingOrganizationService;

    @Autowired
    private AnimalService animalService;

    public IAnimal createAnimal() {
        return animalService.createAnimal(
                Species.AMPHIBIANS,
                "пупс",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                Food.FRUITS,
                HealthStatus.HEALTHY
        );
    }

    @Test(expected = RuntimeException.class)
    public void testCreateFeedingSchedule() {
        CreateFeedingScheduleDTO createFeedingScheduleDTO = new CreateFeedingScheduleDTO();

        createFeedingScheduleDTO.setWeekday(DayOfWeek.MONDAY);
        createFeedingScheduleDTO.setFeedingTime(LocalTime.of(10, 0));
        createFeedingScheduleDTO.setFeedingType(Food.GRAINS);
        createFeedingScheduleDTO.setAnimalId(UUID.randomUUID());

        feedingOrganizationService.createFeedingSchedule(
            createFeedingScheduleDTO.getAnimalId(), 
            createFeedingScheduleDTO.getWeekday(),
            createFeedingScheduleDTO.getFeedingTime(),
            createFeedingScheduleDTO.getFeedingType()
        );
    }

    @Test
    public void deleteFeedingSchedule() {
        IAnimal animal = this.createAnimal();
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        LocalTime time = LocalTime.now().plusSeconds(20);

        IFeedingSchedule schedule = feedingOrganizationService.createFeedingSchedule(
                animal.getId(),
                day, time, Food.GRAINS
        );

        assert(animalService.getAnimalSchedule(animal.getId()).contains(schedule));

        feedingOrganizationService.deleteFeedingSchedule(schedule.getId());

        assert (feedingOrganizationService.getFeedingSchedule(schedule.getId()) == null);
        assert(!animalService.getAnimalSchedule(animal.getId()).contains(schedule));

    }

    @Test
    public void cancelFeedingSchedule() {
        IAnimal animal = this.createAnimal();
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        LocalTime time = LocalTime.now().plusSeconds(20);

        IFeedingSchedule schedule = feedingOrganizationService.createFeedingSchedule(
                animal.getId(),
                day, time, Food.GRAINS
        );

        feedingOrganizationService.cancelFeedingSchedule(schedule.getId());

        assert (feedingOrganizationService.getFeedingSchedule(schedule.getId()).getFeedingTime() == null);
    }

    @Test
    public void changeFeedingSchedule() {
        IAnimal animal = this.createAnimal();
        DayOfWeek day = LocalDate.now().getDayOfWeek();
        LocalTime time = LocalTime.now().plusSeconds(20);
        IFeedingSchedule schedule = feedingOrganizationService.createFeedingSchedule(
                animal.getId(),
                day, time, Food.GRAINS
        );

        feedingOrganizationService.updateFeedingSchedule(schedule.getId(), DayOfWeek.FRIDAY, LocalTime.of(20, 0));

        FeedingTime feedingTime = feedingOrganizationService.getFeedingSchedule(schedule.getId()).getFeedingTime();
        assertSame(feedingTime.getTime(), LocalTime.of(20, 0));
        assertSame(DayOfWeek.FRIDAY, feedingTime.getWeekday());
    }
}
