package com.zooweb.domain.entities;

import java.util.UUID;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.value_objects.EnclosureSize;
import com.zooweb.domain.value_objects.Species;

import java.util.ArrayList;
import java.util.List;

public class Enclosure implements IEnclosure {
    private UUID id;
    private int capacity;
    private List<IAnimal> animals;
    private boolean isClean;
    private EnclosureSize size;
    private Species species;

    public Enclosure(int capacity, EnclosureSize size, Species species) {
        this.id = UUID.randomUUID();
        this.capacity = capacity;
        this.animals = new ArrayList<>();
        this.size = size;
        this.species = species;
    }

    @Override
    public void addAnimal(IAnimal animal) {
        if (animals.contains(animal)) {
            throw new RuntimeException("Животное уже в клетке");
        }

        if (animals.size() < capacity) {
            animals.add(animal);
            System.out.println("Животное добавлено в клетку");
        } else {
            throw new RuntimeException("Клетка переполнена");
        }
    }

    @Override
    public void removeAnimal(IAnimal animal) {
        if (animals.contains(animal)) {
            animals.remove(animal);
            System.out.println("Животное удалено из клетки");
        } else {
            throw new RuntimeException("Животное не найдено в клетке");
        }
    }

    @Override
    public void clean() {
        if (isClean) {
            throw new RuntimeException("Клетка уже чиста, не надо ее полировать!");
        }
        isClean = true;
        System.out.println("Клетка почистилась");
    }

    @Override
    public List<IAnimal> getAnimals() {
        return animals;
    }       

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isClean() {
        return isClean;
    }
    
    @Override
    public EnclosureSize getSize() {
        return size;
    }

    @Override
    public int getVacant() {
        return capacity - animals.size();
    }

    @Override
    public Species getSpecies() {
        return species;
    }
}
