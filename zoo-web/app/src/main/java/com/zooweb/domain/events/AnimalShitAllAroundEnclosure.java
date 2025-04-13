package com.zooweb.domain.events;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;

public class AnimalShitAllAroundEnclosure {

    private IAnimal animal;
    private IEnclosure enclosure;

    public AnimalShitAllAroundEnclosure(IAnimal animal, IEnclosure enclosure) {
        this.animal = animal;
        this.enclosure = enclosure;
    }

    public IAnimal getAnimal() {
        return animal;
    }

    public IEnclosure getEnclosure() {
        return enclosure;
    }
}