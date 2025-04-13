package com.zooweb.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;
import com.zooweb.domain.value_objects.*;

public interface IFeedingSchedule {
    public void changeWeekday(DayOfWeek newWeekday);
    public void changeTime(LocalTime newTime);
    public void cancelSchedule();   
    public UUID getAnimalId();
    public Food getFood();
    public FeedingTime getFeedingTime();
    public UUID getId();
}
