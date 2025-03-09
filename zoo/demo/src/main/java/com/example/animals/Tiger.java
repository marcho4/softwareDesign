package com.example.animals;

import com.example.Predator;

public final class Tiger extends Predator {

    public Tiger(int food_per_day, Boolean is_healthy) {
        super(food_per_day, is_healthy);
    }

    @Override
    public String toString() {
        return "Тигр\n" + super.toString();
    }
}
