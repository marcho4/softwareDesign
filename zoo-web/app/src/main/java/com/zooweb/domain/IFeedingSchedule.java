package com.zooweb.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;
import com.zooweb.domain.value_objects.*;

public interface IFeedingSchedule {
    void changeWeekday(DayOfWeek newWeekday);
    void changeTime(LocalTime newTime);
    void cancelSchedule();
    UUID getAnimalId();
    Food getFood();
    FeedingTime getFeedingTime();
    UUID getId();
}
