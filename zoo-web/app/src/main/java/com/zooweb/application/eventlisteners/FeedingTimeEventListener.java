package com.zooweb.application.eventlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.events.FeedingTimeEvent;
import com.zooweb.domain.services.IAnimalService;

@Component
public class FeedingTimeEventListener {
    private final IAnimalService animalService;

    @Autowired
    public FeedingTimeEventListener(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @EventListener
    public void handleFeedingTimeEvent(FeedingTimeEvent event) {
        IFeedingSchedule schedule = event.getSchedule();
        animalService.feedAnimal(schedule.getAnimalId(), schedule.getFood());
    }
}
