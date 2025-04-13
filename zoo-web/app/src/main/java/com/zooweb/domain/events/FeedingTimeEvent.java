package com.zooweb.domain.events;

import com.zooweb.domain.IFeedingSchedule;


public class FeedingTimeEvent {
    private IFeedingSchedule schedule;

    public FeedingTimeEvent(IFeedingSchedule schedule) {
        this.schedule = schedule;
    }

    public IFeedingSchedule getSchedule() {
        return schedule;
    }
}