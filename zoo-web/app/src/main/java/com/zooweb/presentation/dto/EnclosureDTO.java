package com.zooweb.presentation.dto;

import com.zooweb.domain.value_objects.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class EnclosureDTO {
    private UUID id;
    private Species species;
    private int capacity;
    private int vacant;
    private boolean isClean;
    private EnclosureSize size;
    private List<UUID> animalIds;
}