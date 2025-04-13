package com.zooweb.presentation.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Data;

@Data
public class UpdateFeedingScheduleDTO {
    private DayOfWeek weekday;
    private LocalTime feedingTime;
}
