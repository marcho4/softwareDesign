package com.zooweb.presentation.dto;

import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.FeedingTime;
import lombok.Data;
import java.util.UUID;

@Data
public class FeedingScheduleDTO {
    public FeedingScheduleDTO(IFeedingSchedule feedingSchedule) {
        this.id = feedingSchedule.getId();
        this.animalId = feedingSchedule.getAnimalId();
        this.food = feedingSchedule.getFood();
        if (feedingSchedule.getFeedingTime() != null) {
            this.feedingTime = feedingSchedule.getFeedingTime();
        } else {
            this.feedingTime = null;
        }
    }
    private UUID id;
    private UUID animalId;
    private Food food;
    private FeedingTime feedingTime;
}