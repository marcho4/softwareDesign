package com.zooweb.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.entities.FeedingSchedule;
import com.zooweb.domain.repositories.IFeedingScheduleRepository;
import com.zooweb.domain.value_objects.FeedingTime;
import com.zooweb.domain.value_objects.Food;

@Repository
public class FeedingScheduleRepository implements IFeedingScheduleRepository {

    private HashMap<UUID, IFeedingSchedule> feedingSchedules;

    public FeedingScheduleRepository() {
        this.feedingSchedules = new HashMap<>();
    }

    @Override
    public IFeedingSchedule addFeedingSchedule(UUID animalId, Food food, FeedingTime feedingTime) {
        IFeedingSchedule schedule = new FeedingSchedule(animalId, food, feedingTime);
        feedingSchedules.put(schedule.getId(), schedule);
        return schedule;
    }

    @Override
    public IFeedingSchedule getFeedingSchedule(UUID id) {
        return feedingSchedules.get(id);
    }

    @Override
    public void removeFeedingSchedule(IFeedingSchedule schedule) {
        feedingSchedules.remove(schedule.getId());
    }

    @Override
    public void changeFeedingSchedule(IFeedingSchedule schedule, FeedingTime feedingTime) {
        schedule.changeTime(feedingTime.getTime());
        schedule.changeWeekday(feedingTime.getWeekday());
    }

    @Override
    public List<IFeedingSchedule> getFeedingSchedules() {
        return new ArrayList<>(feedingSchedules.values());
    }

    @Override
    public List<IFeedingSchedule> getFeedingSchedulesByAnimal(UUID animalId) {
        return feedingSchedules.values().stream().filter(schedule -> schedule.getAnimalId().equals(animalId)).collect(Collectors.toList());
    }

    @Override
    public List<IFeedingSchedule> getFeedingSchedulesByFood(Food food) {
        return feedingSchedules.values().stream().filter(schedule -> schedule.getFood().equals(food)).collect(Collectors.toList());
    }

    @Override
    public List<IFeedingSchedule> getFeedingSchedulesByFeedingTime(FeedingTime feedingTime) {
        return feedingSchedules.values().stream().filter(schedule -> schedule.getFeedingTime().equals(feedingTime)).collect(Collectors.toList());
    }
}