package com.zooweb.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IFeedingSchedule;
import com.zooweb.domain.services.IAnimalService;
import com.zooweb.domain.services.IAnimalTransferService;
import com.zooweb.presentation.dto.AnimalDTO;
import com.zooweb.presentation.dto.CreateAnimalDTO;
import com.zooweb.presentation.dto.EnclosureIdDTO;
import com.zooweb.presentation.dto.FeedAnimalRequestDTO;
import com.zooweb.presentation.dto.MessageResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Животные", description = "Управление животными")
@Validated
public class AnimalController {

    private final IAnimalService animalService;
    private final IAnimalTransferService animalTransferService;

    @Autowired
    public AnimalController(IAnimalService animalService, IAnimalTransferService animalTransferService) {
        this.animalService = animalService;
        this.animalTransferService = animalTransferService;
    }

    @Operation(summary = "Создать новое животное", description = "Создает новое животное с указанными параметрами")
    @PostMapping
    public ResponseEntity<IAnimal> createAnimal(@Validated @RequestBody CreateAnimalDTO animalDTO) {
        IAnimal animal = animalService.createAnimal(
            animalDTO.getSpecies(), 
            animalDTO.getName(), 
            animalDTO.getBirthDate(), 
            animalDTO.getGender(), 
            animalDTO.getFavoriteFood(), 
            animalDTO.getHealthStatus()
        );
        return ResponseEntity.ok(animal);
    }

    @Operation(summary = "Удалить животное", description = "Удаляет животное с указанным ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteAnimal(@Validated @PathVariable UUID id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok(new MessageResponseDTO("Animal deleted successfully"));
    }

    @Operation(summary = "Получить животное по ID", description = "Возвращает животное с указанным ID")
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable @Validated UUID id) {
        IAnimal animal = animalService.getAnimal(id);
        AnimalDTO dto = new AnimalDTO(animal);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Переместить животное из одного вольера в другой", description = "Перемещает животное в другой вольер с указанным ID")
    @PutMapping("/{id}")        
    public ResponseEntity<MessageResponseDTO> moveAnimal(@PathVariable @Validated UUID id, @RequestBody @Validated EnclosureIdDTO enclosureIdDTO) {
        animalTransferService.transferAnimal(id, enclosureIdDTO.getEnclosureId());
        return ResponseEntity.ok(new MessageResponseDTO("Animal moved successfully"));
    }

    @Operation(summary = "Покормить животное", description = "Покормит животное с указанным ID")
    @PutMapping("/{id}/feed")
    public ResponseEntity<MessageResponseDTO> feedAnimal(@PathVariable @Validated UUID id, @RequestBody @Validated FeedAnimalRequestDTO feedAnimalRequestDTO) {
        animalService.feedAnimal(id, feedAnimalRequestDTO.getFood());
        return ResponseEntity.ok(new MessageResponseDTO("Animal fed successfully"));
    }

    @Operation(summary = "Лечить животное", description = "Лечит животное с указанным ID")
    @PutMapping("/{id}/heal")
    public ResponseEntity<MessageResponseDTO> healAnimal(@Validated @PathVariable UUID id) {
        animalService.healAnimal(id);
        return ResponseEntity.ok(new MessageResponseDTO("Animal healed successfully"));
    }

    @Operation(summary = "Получить расписание кормления животного", description = "Возвращает расписание кормления животного с указанным ID")
    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<IFeedingSchedule>> getAnimalSchedule(@PathVariable @Validated UUID id) {
        List<IFeedingSchedule> schedules = animalService.getAnimalSchedule(id);
        return ResponseEntity.ok(schedules);
    }
}
