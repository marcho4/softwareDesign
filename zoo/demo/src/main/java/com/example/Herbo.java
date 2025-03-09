package com.example;

public abstract class Herbo extends Animal {
    protected int _goodness;

    public Herbo(int food_per_day, Boolean is_healthy, int goodness) {
        super(food_per_day, is_healthy);
        _goodness = goodness;
    }
    
    public int getGoodness() {
        return _goodness;
    }

    public void setGoodness(int x) {
        this._goodness = x;
    }

    @Override
    public Boolean addToTheZoo(Zoo zoo) {
        if (!zoo.checkAnimal(this)){
            return false;
        }
        if (_goodness >= 5) {
            zoo.getCutties().add(this);
        }
        zoo.getAllAnimals().add(this);
        zoo.addFood(this.getFood());
        return true;
    }

    @Override
    public String toString() {
        return "Ест кг еды в день: " + this.getFood() + "\nЗдоров: " + this.isHealthy() + "\nДоброта: " + this.getGoodness();
    }
}