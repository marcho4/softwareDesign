package com.zooweb.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;
import java.time.DayOfWeek;
import java.util.stream.Collectors;

import com.zooweb.application.ZooStatisticsService;
import com.zooweb.presentation.dto.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/stats")
@Tag(name = "Статистика", description = "Получение статистики по животным, вольерам и расписанию кормления")
public class StatsController {

    private final ZooStatisticsService zooStatisticsService;

    @Autowired
    public StatsController(ZooStatisticsService zooStatisticsService) {
        this.zooStatisticsService = zooStatisticsService;
    }
    
    @Operation(summary = "Получить всех животных", description = "Возвращает всех животных")
    @GetMapping("/animals/all")
    public ResponseEntity<List<AnimalDTO>> getAnimalStats() {
        return ResponseEntity.ok(zooStatisticsService.getAllAnimals().stream().map(AnimalDTO::new).collect(Collectors.toList()));
    }
    
    @Operation(summary = "Получить все расписания кормления", description = "Возвращает все расписания кормления")
    @GetMapping("/feeding-schedule/all")
    public ResponseEntity<List<FeedingScheduleDTO>> getAllFeedingSchedules() {
        return ResponseEntity.ok(zooStatisticsService.getAllFeedingSchedules().stream().map(FeedingScheduleDTO::new).collect(Collectors.toList()));
    }

    @Operation(summary = "Получить все вольеры", description = "Возвращает все вольеры")
    @GetMapping("/enclosures/all")
    public ResponseEntity<List<EnclosureDTO>> getAllEnclosures() {
        return ResponseEntity.ok(zooStatisticsService.getAllEnclosures().stream().map(EnclosureDTO::new).collect(Collectors.toList()));
    }

    @Operation(summary = "Получить статистику по животным", description = "Возвращает статистику по животным")
    @GetMapping("/animals")
    public ResponseEntity<AnimalStatsDTO> getAnimalStatsDTO() {
        Map<String, Object> data = zooStatisticsService.getAnimalStatsData();
        
        int healthyCount = (int) data.get("healthyCount");
        int illCount = (int) data.get("illCount");
        
        @SuppressWarnings("unchecked")
        Map<String, Long> speciesCounts = (Map<String, Long>) data.get("speciesCounts");
        
        AnimalStatsDTO dto = new AnimalStatsDTO(healthyCount, illCount, speciesCounts);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Получить статистику по вольерам", description = "Возвращает статистику по вольерам")
    @GetMapping("/enclosures")
    public ResponseEntity<EnclosureStatsDTO> getEnclosureStatsDTO() {
        Map<String, Object> data = zooStatisticsService.getEnclosureStatsData();
        
        int totalCount = (int) data.get("totalCount");
        int occupiedCount = (int) data.get("occupiedCount");
        int emptyCount = (int) data.get("emptyCount");

        @SuppressWarnings("unchecked")
        Map<String, Long> types = (Map<String, Long>) data.get("types");
        
        EnclosureStatsDTO dto = new EnclosureStatsDTO(
            totalCount, occupiedCount, emptyCount, types);
            
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Получить статистику по расписанию кормления", description = "Возвращает статистику по расписанию кормления")
    @GetMapping("/feeding-schedule")
    public ResponseEntity<FeedingScheduleStatsDTO> getFeedingScheduleStatsDTO() {
        Map<String, Object> data = zooStatisticsService.getFeedingScheduleStatsData();
        
        @SuppressWarnings("unchecked")
        Map<DayOfWeek, Long> feedingsByDay = (Map<DayOfWeek, Long>) data.get("feedingsByDay");

        @SuppressWarnings("unchecked")
        Map<String, Long> feedingsByFood = (Map<String, Long>) data.get("feedingsByFood");
        
        FeedingScheduleStatsDTO dto = new FeedingScheduleStatsDTO(feedingsByDay, feedingsByFood);
        return ResponseEntity.ok(dto);
    }
}
