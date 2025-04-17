package com.zooweb.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zooweb.domain.services.IEnclosureService;
import com.zooweb.presentation.dto.AnimalIdDTO;
import com.zooweb.presentation.dto.CapacityRequestDTO;
import com.zooweb.presentation.dto.CreateEnclosureDTO;
import com.zooweb.presentation.dto.EnclosureDTO;
import com.zooweb.presentation.dto.MessageResponseDTO;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/enclosures")
@Tag(name = "Вольеры", description = "Управление вольерами")
@Validated
public class EnclosureController {
    
    private final IEnclosureService enclosureService;

    @Autowired
    public EnclosureController(IEnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }

    @Operation(summary = "Получить вольер по ID", description = "Возвращает вольер с указанным ID")
    @GetMapping("/{id}")
    public ResponseEntity<EnclosureDTO> getEnclosure(@PathVariable UUID id) {
        IEnclosure enclosure = enclosureService.getEnclosure(id);
        EnclosureDTO dto = new EnclosureDTO(enclosure);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Создать новый вольер", description = "Создает новый вольер с указанными параметрами")
    @PostMapping
    public ResponseEntity<IEnclosure> addEnclosure(@Validated @RequestBody CreateEnclosureDTO enclosureDTO) {
        IEnclosure enclosure = enclosureService.createEnclosure(enclosureDTO.getSpecies(), enclosureDTO.getCapacity(), enclosureDTO.getWidth(), enclosureDTO.getHeight(), enclosureDTO.getLength());
        return ResponseEntity.ok(enclosure);
    }

    @Operation(summary = "Удалить вольер", description = "Удаляет вольер с указанным ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> removeEnclosure(@PathVariable UUID id) {
        enclosureService.deleteEnclosure(id);
        return ResponseEntity.ok(new MessageResponseDTO("Enclosure deleted successfully"));
    }

    @Operation(summary = "Добавить животное в вольер", description = "Добавляет животное в вольер с указанным ID")
    @PostMapping("/{id}/animals")
    public ResponseEntity<MessageResponseDTO> addAnimalToEnclosure(@PathVariable UUID id, @Validated @RequestBody AnimalIdDTO animalIdDTO) {
        enclosureService.addAnimalToEnclosure(id, animalIdDTO.getAnimalId());
        return ResponseEntity.ok(new MessageResponseDTO("Animal added to enclosure successfully"));
    }

    @Operation(summary = "Получить всех животных в вольере", description = "Возвращает всех животных в вольере с указанным ID")
    @GetMapping("/{id}/animals")
    public ResponseEntity<List<IAnimal>> getAnimalsInEnclosure(@PathVariable UUID id) {
        List<IAnimal> animals = enclosureService.getAnimalsByEnclosure(id);
        return ResponseEntity.ok(animals);
    }

    @Operation(summary = "Удалить животное из вольера", description = "Удаляет животное из вольера с указанным ID")
    @DeleteMapping("/{id}/animals/{animalId}")
    public ResponseEntity<MessageResponseDTO> removeAnimalFromEnclosure(@PathVariable UUID id, @PathVariable UUID animalId) {
        enclosureService.removeAnimalFromEnclosure(id, animalId);
        return ResponseEntity.ok(new MessageResponseDTO("Animal removed from enclosure successfully"));
    }

    @Operation(summary = "Получить статус чистоты вольера", description = "Возвращает статус чистоты вольера с указанным ID")
    @GetMapping("/{id}/clean")
    public ResponseEntity<MessageResponseDTO> cleanStatusEnclosure(@PathVariable UUID id) {
        boolean isClean = enclosureService.getEnclosure(id).isClean();
        return ResponseEntity.ok(new MessageResponseDTO(isClean ? "Enclosure is clean" : "Enclosure is not clean"));
    }

    @Operation(summary = "Очистить вольер", description = "Очищает вольер с указанным ID")
    @PutMapping("/{id}/clean")
    public ResponseEntity<MessageResponseDTO> cleanEnclosure(@PathVariable UUID id) {
        enclosureService.cleanEnclosure(id);
        return ResponseEntity.ok(new MessageResponseDTO("Enclosure cleaned"));   
    }

    @Operation(summary = "Получить вместимость вольера", description = "Возвращает вместимость вольера с указанным ID")
    @GetMapping("/{id}/capacity")
    public ResponseEntity<CapacityRequestDTO> capacityEnclosure(@PathVariable UUID id) {
        int capacity = enclosureService.getEnclosure(id).getCapacity();
        int vacant = enclosureService.getEnclosure(id).getVacant();
        return ResponseEntity.ok(new CapacityRequestDTO(capacity, vacant));
    }
}
