package com.zooweb.presentation.dto;

import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.value_objects.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class EnclosureDTO {
    public EnclosureDTO(IEnclosure enclosure) {
        this.id = enclosure.getId();
        this.species = enclosure.getSpecies();
        this.capacity = enclosure.getCapacity();
        this.vacant = enclosure.getVacant();
        this.isClean = enclosure.isClean();
        this.size = enclosure.getSize();
        this.animalIds = enclosure.getAnimals().stream().map(IAnimal::getId).collect(Collectors.toList());
    }
    private UUID id;
    private Species species;
    private int capacity;
    private int vacant;
    private boolean isClean;
    private EnclosureSize size;
    private List<UUID> animalIds;
}