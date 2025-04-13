package com.zooweb.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.zooweb.domain.events.FeedingTimeEvent;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.repositories.IFeedingScheduleRepository;

@Component
public class FeedingScheduleNotifier {
    private final ApplicationEventPublisher eventPublisher;
    private final IFeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    public FeedingScheduleNotifier(ApplicationEventPublisher eventPublisher, IFeedingScheduleRepository feedingScheduleRepository) {
        this.eventPublisher = eventPublisher;
        this.feedingScheduleRepository = feedingScheduleRepository;
    }

    @Scheduled(fixedRate = 5000) 
    public void checkFeedingSchedules() {
        LocalTime currentTime = LocalTime.now();
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();

        List<IFeedingSchedule> feedingSchedules = feedingScheduleRepository.getFeedingSchedules();
        for (IFeedingSchedule schedule : feedingSchedules) {
            if (schedule.getFeedingTime().getTime().equals(currentTime) && 
                schedule.getFeedingTime().getWeekday() == currentDay) {
                eventPublisher.publishEvent(new FeedingTimeEvent(schedule));
            }
        }
    }
}