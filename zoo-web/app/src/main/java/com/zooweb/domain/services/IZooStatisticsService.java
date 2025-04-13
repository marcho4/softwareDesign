package com.zooweb.domain.services;

import org.springframework.stereotype.Service;

import com.zooweb.domain.entities.Enclosure;
import com.zooweb.domain.value_objects.*;

@Service
public interface IZooStatisticsService {
    public void getAllAnimals();
    public void getAnimalsBySpecies(Species species);
    public void getAnimalsByHealthStatus(HealthStatus healthStatus);
    public void getAnimalsByEnclosure(Enclosure enclosure);
    public void getAllEnclosures();
    public void getFreeEnclosures();
}
