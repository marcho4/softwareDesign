package com.example.animals;

import com.example.Herbo;

public final class Rabbit extends Herbo {

    public Rabbit(int food_per_day, Boolean is_healthy, int goodness) {
        super(food_per_day, is_healthy, goodness);
    }

    @Override
    public String toString() {
        return "Зайчик\n" + super.toString();
    }
}
