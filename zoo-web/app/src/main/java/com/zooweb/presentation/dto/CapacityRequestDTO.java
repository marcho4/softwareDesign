package com.zooweb.presentation.dto;

import lombok.Data;

@Data
public class CapacityRequestDTO {
    private int capacity;
    private int vacant;

    public CapacityRequestDTO(int capacity, int vacant) {
        this.capacity = capacity;
        this.vacant = vacant;
    }
}
