package com.zooweb.domain.repositories;

import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.FeedingTime;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface IFeedingScheduleRepository {
    public IFeedingSchedule addFeedingSchedule(UUID animalId, Food food, FeedingTime feedingTime);
    public void removeFeedingSchedule(IFeedingSchedule schedule);
    public void changeFeedingSchedule(IFeedingSchedule schedule, FeedingTime feedingTime);
    public IFeedingSchedule getFeedingSchedule(UUID id);
    public List<IFeedingSchedule> getFeedingSchedules();
    public List<IFeedingSchedule> getFeedingSchedulesByAnimal(UUID animalId);
    public List<IFeedingSchedule> getFeedingSchedulesByFood(Food food);
    public List<IFeedingSchedule> getFeedingSchedulesByFeedingTime(FeedingTime feedingTime);
}
