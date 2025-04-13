package com.zooweb.presentation.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EnclosureIdDTO {

    @NotNull(message = "Enclosure ID is required")
    private UUID enclosureId;

}
