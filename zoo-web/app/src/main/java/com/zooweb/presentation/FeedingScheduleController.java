package com.zooweb.presentation;


import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.services.IFeedingOrganizationService;
import com.zooweb.presentation.dto.CreateFeedingScheduleDTO;
import com.zooweb.presentation.dto.MessageResponseDTO;
import com.zooweb.presentation.dto.UpdateFeedingScheduleDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feeding-schedule")
@Validated
@Tag(name = "Расписание кормления", description = "Управление расписанием кормления животных")
public class FeedingScheduleController {

    private final IFeedingOrganizationService feedingOrganizerService;

    @Autowired  
    public FeedingScheduleController(IFeedingOrganizationService feedingOrganizerService) {
        this.feedingOrganizerService = feedingOrganizerService;
    }

    @Operation(summary = "Создать новое расписание кормления", description = "Создает новое расписание кормления с указанными параметрами")
    @PostMapping
    public ResponseEntity<IFeedingSchedule> createFeedingSchedule(@RequestBody CreateFeedingScheduleDTO createFeedingScheduleDTO) {
        IFeedingSchedule schedule = feedingOrganizerService.createFeedingSchedule(createFeedingScheduleDTO.getAnimalId(), createFeedingScheduleDTO.getWeekday(), createFeedingScheduleDTO.getFeedingTime(), createFeedingScheduleDTO.getFeedingType());
        return ResponseEntity.ok(schedule);
    }   

    @Operation(summary = "Удалить расписание кормления", description = "Удаляет расписание кормления с указанным ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteFeedingSchedule(@PathVariable UUID id) {
        feedingOrganizerService.deleteFeedingSchedule(id);
        return ResponseEntity.ok(new MessageResponseDTO("Feeding schedule deleted"));
    }

    @Operation(summary = "Получить расписание кормления по ID", description = "Возвращает расписание кормления с указанным ID")
    @GetMapping("/{id}")
    public ResponseEntity<IFeedingSchedule> getFeedingSchedule(@PathVariable UUID id) {
        IFeedingSchedule schedule = feedingOrganizerService.getFeedingSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    @Operation(summary = "Обновить расписание кормления", description = "Обновляет расписание кормления с указанным ID")
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateFeedingSchedule(@PathVariable UUID id, @RequestBody UpdateFeedingScheduleDTO updateFeedingScheduleDTO) {

        feedingOrganizerService.updateFeedingSchedule(id, updateFeedingScheduleDTO.getWeekday(), updateFeedingScheduleDTO.getFeedingTime());
        return ResponseEntity.ok(new MessageResponseDTO("Feeding schedule updated"));
    }

    @Operation(summary = "Отменить расписание кормления", description = "Отменяет расписание кормления с указанным ID")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<MessageResponseDTO> cancelFeedingSchedule(@PathVariable UUID id) {
        feedingOrganizerService.cancelFeedingSchedule(id);
        return ResponseEntity.ok(new MessageResponseDTO("Feeding schedule cancelled"));
    }

    @Operation(summary = "Получить все расписания кормления", description = "Возвращает все расписания кормления")
    @GetMapping("/all")
    public ResponseEntity<List<IFeedingSchedule>> getAllFeedingSchedules() {
        List<IFeedingSchedule> schedules = feedingOrganizerService.getAllFeedingSchedules();
        return ResponseEntity.ok(schedules);
    }
}
