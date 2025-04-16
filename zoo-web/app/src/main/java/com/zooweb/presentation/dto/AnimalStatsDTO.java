package com.zooweb.presentation.dto;

import java.util.Map;

public class AnimalStatsDTO {
    private int healthyCount;
    private int illCount;
    private Map<String, Long> speciesCounts;

    public AnimalStatsDTO(int healthyCount, int illCount, Map<String, Long> speciesCounts) {
        this.healthyCount = healthyCount;
        this.illCount = illCount;
        this.speciesCounts = speciesCounts;
    }

    public int getHealthyCount() {
        return healthyCount;
    }

    public int getIllCount() {
        return illCount;
    }

    public Map<String, Long> getSpeciesCounts() {
        return speciesCounts;
    }
} 