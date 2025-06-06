package com.zooweb.domain.value_objects;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class FeedingTime {
    private DayOfWeek weekday;
    private LocalTime time;
    
    public FeedingTime(DayOfWeek weekday, LocalTime time) {
        this.weekday = weekday;
        this.time = time;
    }

    public void changeWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
    }

    public void changeTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Кормить каждый " + this.weekday + " в " + this.time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}
