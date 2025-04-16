package com.zooweb.presentation.dto;

import java.time.DayOfWeek;
import java.util.Map;

public class FeedingScheduleStatsDTO {
    private Map<DayOfWeek, Long> feedingsByDay;
    private Map<String, Long> feedingsByFood;

    public FeedingScheduleStatsDTO(Map<DayOfWeek, Long> feedingsByDay, Map<String, Long> feedingsByFood) {
        this.feedingsByDay = feedingsByDay;
        this.feedingsByFood = feedingsByFood;
    }

    public Map<DayOfWeek, Long> getFeedingsByDay() {
        return feedingsByDay;
    }

    public Map<String, Long> getFeedingsByFood() {
        return feedingsByFood;
    }
} 