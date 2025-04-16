package com.zooweb.domain.services;

import java.util.List;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.IFeedingSchedule;

public interface IZooStatisticsService {
    List<IAnimal> getAllAnimals();
    List<IEnclosure> getAllEnclosures();
    List<IFeedingSchedule> getAllFeedingSchedules();
    String getFeedingScheduleStats();
    String getAnimalStats();
    String getEnclosureStats();
}
