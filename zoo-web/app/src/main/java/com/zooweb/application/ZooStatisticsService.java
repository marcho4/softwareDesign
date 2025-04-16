package com.zooweb.application;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.repositories.IAnimalRepository;
import com.zooweb.domain.repositories.IEnclosureRepository;
import com.zooweb.domain.repositories.IFeedingScheduleRepository;
import com.zooweb.domain.services.IZooStatisticsService;
import com.zooweb.domain.value_objects.HealthStatus;
import com.zooweb.domain.value_objects.Food;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZooStatisticsService implements IZooStatisticsService {
    private final IAnimalRepository animalRepository;
    private final IEnclosureRepository enclosureRepository;
    private final IFeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    public ZooStatisticsService(IAnimalRepository animalRepository, IEnclosureRepository enclosureRepository, IFeedingScheduleRepository feedingScheduleRepository) {
        this.animalRepository = animalRepository;
        this.enclosureRepository = enclosureRepository;
        this.feedingScheduleRepository = feedingScheduleRepository;
    }

    @Override
    public String getAnimalStats() {
        List<IAnimal> animals = getAllAnimals();

        String healthyAnimals = animals.stream()
            .filter(animal -> animal.getHealthStatus() == HealthStatus.HEALTHY)
            .count() + " здоровых животных";

        String illAnimals = animals.stream()
            .filter(animal -> animal.getHealthStatus() == HealthStatus.ILL)
            .count() + " больных животных";
        
        Map<String, Long> speciesCounts = animals.stream()
            .collect(Collectors.groupingBy(
                animal -> animal.getSpecies().toString(),
                Collectors.counting()
            ));

        String speciesCount = "";

        for (Map.Entry<String, Long> entry : speciesCounts.entrySet()) {
            speciesCount += entry.getKey() + ": " + entry.getValue() + " \n ";
        }
        
        return healthyAnimals + " \n " + illAnimals + " \n " + speciesCount;
    }

    @Override
    public String getEnclosureStats() {
        List<IEnclosure> enclosures = getAllEnclosures();
        
        String totalEnclosures = enclosures.size() + " всего вольеров";
        
        String occupiedEnclosures = enclosures.stream()
            .filter(enclosure -> !enclosure.getAnimals().isEmpty())
            .count() + " занятых вольеров";
            
        String emptyEnclosures = enclosures.stream()
            .filter(enclosure -> enclosure.getAnimals().isEmpty())
            .count() + " свободных вольеров";
            
        Map<String, Long> speciesCounts = enclosures.stream()
            .collect(Collectors.groupingBy(
                enclosure -> enclosure.getSpecies().toString(),
                Collectors.counting()
            ));
            
        String speciesCount = "";
        for (Map.Entry<String, Long> entry : speciesCounts.entrySet()) {
            speciesCount += entry.getKey() + ": " + entry.getValue() + " \n ";
        }
        
        return totalEnclosures + " \n " + occupiedEnclosures + " \n " + emptyEnclosures + " \n " + speciesCount;
    }

    @Override
    public String getFeedingScheduleStats() {
        List<IFeedingSchedule> schedules = getAllFeedingSchedules();
        
        // Статистика по дням недели
        Map<DayOfWeek, Long> weekdayStats = schedules.stream()
            .collect(Collectors.groupingBy(
                schedule -> schedule.getFeedingTime().getWeekday(),
                Collectors.counting()
            ));
            
        String weekdayStatsStr = "Расписание кормления по дням недели: ";
        for (Map.Entry<DayOfWeek, Long> entry : weekdayStats.entrySet()) {
            weekdayStatsStr += entry.getKey() + ": " + entry.getValue() + " \n ";
        }
        
        Map<Food, Long> foodStats = schedules.stream()
            .collect(Collectors.groupingBy(
                schedule -> schedule.getFood(),
                Collectors.counting()
            ));
            
        String foodStatsStr = "Расписание кормления по еде: ";
        for (Map.Entry<Food, Long> entry : foodStats.entrySet()) {
            foodStatsStr += entry.getKey() + ": " + entry.getValue() + " \n ";
        }
        
        return weekdayStatsStr + " \n " + foodStatsStr;
    }

    @Override
    public List<IAnimal> getAllAnimals() {
        return animalRepository.getAllAnimals();
    }

    @Override
    public List<IEnclosure> getAllEnclosures() {
        return enclosureRepository.getEnclosures();
    }

    @Override
    public List<IFeedingSchedule> getAllFeedingSchedules() {
        return feedingScheduleRepository.getFeedingSchedules();
    }

    public Map<String, Object> getAnimalStatsData() {
        List<IAnimal> animals = getAllAnimals();

        int healthyCount = (int) animals.stream()
            .filter(animal -> animal.getHealthStatus() == HealthStatus.HEALTHY)
            .count();

        int illCount = (int) animals.stream()
            .filter(animal -> animal.getHealthStatus() == HealthStatus.ILL)
            .count();
        
        Map<String, Long> speciesCounts = animals.stream()
            .collect(Collectors.groupingBy(
                animal -> animal.getSpecies().toString(),
                Collectors.counting()
            ));
        
        Map<String, Object> result = Map.of(
            "healthyCount", healthyCount,
            "illCount", illCount,
            "speciesCounts", speciesCounts
        );
        
        return result;
    }

    public Map<String, Object> getEnclosureStatsData() {
        List<IEnclosure> enclosures = getAllEnclosures();
        
        int totalCount = enclosures.size();
        
        int occupiedCount = (int) enclosures.stream()
            .filter(enclosure -> !enclosure.getAnimals().isEmpty())
            .count();
            
        int emptyCount = (int) enclosures.stream()
            .filter(enclosure -> enclosure.getAnimals().isEmpty())
            .count();
            
        Map<String, Long> typeDistribution = enclosures.stream()
            .collect(Collectors.groupingBy(
                enclosure -> enclosure.getSpecies().toString(),
                Collectors.counting()
            ));
        
        Map<String, Object> result = Map.of(
            "totalCount", totalCount,
            "occupiedCount", occupiedCount,
            "emptyCount", emptyCount,
            "types", typeDistribution
        );
        
        return result;
    }

    public Map<String, Object> getFeedingScheduleStatsData() {
        List<IFeedingSchedule> schedules = getAllFeedingSchedules();
        
        Map<DayOfWeek, Long> feedingsByDay = schedules.stream()
            .collect(Collectors.groupingBy(
                schedule -> schedule.getFeedingTime().getWeekday(),
                Collectors.counting()
            ));
            
        Map<String, Long> feedingsByFood = schedules.stream()
            .collect(Collectors.groupingBy(
                schedule -> schedule.getFood().toString(),
                Collectors.counting()
            ));
        
        Map<String, Object> result = Map.of(
            "feedingsByDay", feedingsByDay,
            "feedingsByFood", feedingsByFood
        );
        
        return result;
    }
}
