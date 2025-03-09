package com.example;

public abstract class Predator extends Animal {
    public Predator(int food_per_day, Boolean is_healthy) {
        super(food_per_day, is_healthy);
    }

    @Override
    public String toString() {
        return "Ест кг еды в день:" + this.getFood() + "\nЗдоров: " + this.isHealthy();
    }
}
