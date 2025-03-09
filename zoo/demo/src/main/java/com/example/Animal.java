package com.example;

import com.example.interfaces.IAlive;

public abstract class Animal implements IAlive {
    private int _food_per_day;
    private Boolean _is_healthy;

    public Animal(int food_per_day, Boolean is_healthy) {
        _food_per_day = food_per_day;
        _is_healthy = is_healthy;
    }

    @Override
    public int getFood() {
        return _food_per_day;
    }

    @Override
    public void setFood(int x) {
        this._food_per_day = x;
    }

    public Boolean isHealthy() {
        return _is_healthy;
    }

    public Boolean addToTheZoo(Zoo zoo) {

        if (!zoo.checkAnimal(this)){
            return false;
        }

        zoo.addFood(this.getFood());
        zoo.getAllAnimals().add(this);
        return true;
    }
}
