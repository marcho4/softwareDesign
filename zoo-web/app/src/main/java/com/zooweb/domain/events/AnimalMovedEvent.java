package com.zooweb.domain.events;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;


public class AnimalMovedEvent {

    private IAnimal animal;
    private IEnclosure from;
    private IEnclosure to;

    public AnimalMovedEvent(IAnimal animal, IEnclosure from, IEnclosure to) {
        this.animal = animal;
        this.from = from;
        this.to = to;
    }

    public IAnimal getAnimal() {
        return animal;
    }

    public IEnclosure getFrom() {
        return from;
    }

    public IEnclosure getTo() {
        return to;
    }
}
