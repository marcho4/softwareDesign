package com.zooweb.application;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.repositories.IFeedingScheduleRepository;
import com.zooweb.domain.repositories.IAnimalRepository;
import com.zooweb.domain.services.IFeedingOrganizationService;
import com.zooweb.domain.value_objects.FeedingTime;
import com.zooweb.domain.value_objects.Food;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class FeedingOrganizationService implements IFeedingOrganizationService{

    private final IFeedingScheduleRepository feedingScheduleRepository;
    private final IAnimalRepository animalRepository;

    @Autowired
    public FeedingOrganizationService(IFeedingScheduleRepository feedingScheduleRepository, IAnimalRepository animalRepository) {
        this.feedingScheduleRepository = feedingScheduleRepository;
        this.animalRepository = animalRepository;
    }

    @Override
    public void feedAnimal(IAnimal animal, Food food) {
        animal.feed(food);
    }

    @Override
    public List<IFeedingSchedule> getAllFeedingSchedules() {
        return feedingScheduleRepository.getFeedingSchedules();
    }

    @Override
    public IFeedingSchedule getFeedingSchedule(UUID id) {
        return feedingScheduleRepository.getFeedingSchedule(id);
    }

    // Обновляет только если параметр не null
    @Override
    public void updateFeedingSchedule(UUID id, DayOfWeek weekday, LocalTime feedingTime) {
        IFeedingSchedule feedingSchedule = feedingScheduleRepository.getFeedingSchedule(id);
        if (weekday != null) {
            feedingSchedule.changeWeekday(weekday);
        }
        if (feedingTime != null) {
            feedingSchedule.changeTime(feedingTime);
        }
    }

    @Override
    public void cancelFeedingSchedule(UUID id) {
        IFeedingSchedule feedingSchedule = feedingScheduleRepository.getFeedingSchedule(id);
        feedingSchedule.cancelSchedule();
    }

    @Override
    public void deleteFeedingSchedule(UUID id) {
        IFeedingSchedule feedingSchedule = feedingScheduleRepository.getFeedingSchedule(id);
        feedingScheduleRepository.removeFeedingSchedule(feedingSchedule);
    }

    @Override
    public IFeedingSchedule createFeedingSchedule(UUID animalId, DayOfWeek weekday, LocalTime time, Food food) {
        IAnimal animal = animalRepository.getAnimal(animalId);
        if (animal == null) {
            throw new RuntimeException("Животное не найдено");
        }
        FeedingTime feedingTime = new FeedingTime(weekday, time);
        return feedingScheduleRepository.addFeedingSchedule(animalId, food, feedingTime);
    }   

}
