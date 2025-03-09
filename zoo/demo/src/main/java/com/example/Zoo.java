package com.example;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.interfaces.IInventory;


@Component
public class Zoo {
    private ArrayList<Animal> _animals = new ArrayList<Animal>();
    private ArrayList<Herbo> _cutties = new ArrayList<Herbo>();
    private ArrayList<IInventory> _inventory = new ArrayList<IInventory>();
    private int _dailyFood;
    private VetClinic _vet;
    private Config _conf = AppConfig.getInstance();

    @Autowired
    public Zoo(VetClinic vet) {
        _vet = vet;
    }

    public ArrayList<Animal> getAllAnimals() {
        return _animals;
    }

    public ArrayList<Herbo> getCutties() {
        return _cutties;
    }

    public ArrayList<IInventory> getInventories() {
        return _inventory;
    }

    public int getDailyFood() {
        return _dailyFood;
    }
    
    public void addFood(int x) {
        _dailyFood += x;
    }

    public Boolean checkAnimal(Animal animal) {
        // Проверка животного в ветклинике
        if (!_vet.checkAnimal(animal)) {
            System.out.println(_conf.add_animal_err);
            return false;
        }
        return true;
    }
}