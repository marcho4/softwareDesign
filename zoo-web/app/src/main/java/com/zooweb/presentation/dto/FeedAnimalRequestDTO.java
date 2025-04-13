package com.zooweb.presentation.dto;

import javax.validation.constraints.NotNull;

import com.zooweb.domain.value_objects.Food;
import lombok.Data;

@Data
public class FeedAnimalRequestDTO {
    @NotNull(message = "Food is required")
    private Food food;
}
