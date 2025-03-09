package com.example.animals;
import com.example.Predator;

public final class Wolf extends Predator {

    public Wolf(int food_per_day, Boolean is_healthy) {
        super(food_per_day, is_healthy);
    }
    
    @Override
    public String toString() {
        return "Волк\n" + super.toString();
    }
}
