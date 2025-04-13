package com.zooweb.domain.entities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.*;


public class FeedingSchedule implements IFeedingSchedule {
    private UUID animalId;
    private Food food;
    private FeedingTime feedingTime;
    private UUID id;

    public FeedingSchedule(UUID animalId, Food food, FeedingTime feedingTime) {
        this.animalId = animalId;
        this.food = food;
        this.feedingTime = feedingTime;
        this.id = UUID.randomUUID();
    }

    @Override
    public void changeWeekday(DayOfWeek newWeekday) {
        this.feedingTime.changeWeekday(newWeekday);
    }

    @Override
    public void changeTime(LocalTime newTime) {
        this.feedingTime.changeTime(newTime);
    }

    @Override
    public void cancelSchedule() {
        this.feedingTime = null;
    }  
    
    @Override
    public UUID getAnimalId() {
        return animalId;
    }

    @Override
    public Food getFood() {
        return food;
    }

    @Override
    public FeedingTime getFeedingTime() {
        return feedingTime;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
