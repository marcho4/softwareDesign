package com.zooweb.presentation.dto;

import java.util.Map;

public class EnclosureStatsDTO {
    private int totalCount;
    private int occupiedCount;
    private int emptyCount;
    private Map<String, Long> types;

    public EnclosureStatsDTO(int totalCount, int occupiedCount, int emptyCount, Map<String, Long> types) {
        this.totalCount = totalCount;
        this.occupiedCount = occupiedCount;
        this.emptyCount = emptyCount;
        this.types = types;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }

    public int getEmptyCount() {
        return emptyCount;
    }

    public Map<String, Long> getTypes() {
        return types;
    }
} 