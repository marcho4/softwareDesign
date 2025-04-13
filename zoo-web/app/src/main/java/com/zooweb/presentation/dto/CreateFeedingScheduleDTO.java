package com.zooweb.presentation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zooweb.domain.value_objects.Food;
import java.util.UUID;

@Data
public class CreateFeedingScheduleDTO {

    private static final String allowedWeekdays = "MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY";
    private static final String allowedFeedingTypes = "MEAT, VEGETABLE, FRUIT, GRAIN, OTHER, NUTS";

    @NotNull(message = "Animal ID is required")
    private UUID animalId;

    @NotNull(message = "Weekday is required. Allowed weekdays: " + allowedWeekdays)
    private DayOfWeek weekday;

    @NotNull(message = "Feeding time is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Feeding time must be in the format HH:MM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime feedingTime;

    @NotNull(message = "Feeding type is required. Allowed feeding types: " + allowedFeedingTypes)
    @NotBlank(message = "Feeding type cannot be empty")
    private Food feedingType;
}
