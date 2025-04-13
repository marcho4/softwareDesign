package com.zooweb.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.Food;
import java.time.LocalTime;
import java.time.DayOfWeek;

@Service    
public interface IFeedingOrganizationService {
    public void feedAnimal(IAnimal animal, Food food);
    public IFeedingSchedule createFeedingSchedule(UUID animalId, DayOfWeek weekday, LocalTime feedingTime, Food food);
    public List<IFeedingSchedule> getAllFeedingSchedules();
    public IFeedingSchedule getFeedingSchedule(UUID id);
    public void updateFeedingSchedule(UUID id, DayOfWeek weekday, LocalTime feedingTime);
    public void cancelFeedingSchedule(UUID id);
    public void deleteFeedingSchedule(UUID id);
}
