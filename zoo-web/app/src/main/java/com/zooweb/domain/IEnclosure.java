package com.zooweb.domain;

import java.util.List;
import java.util.UUID;

import com.zooweb.domain.value_objects.EnclosureSize;
import com.zooweb.domain.value_objects.Species;

public interface IEnclosure {
    void addAnimal(IAnimal animal);
    void removeAnimal(IAnimal animal);
    void clean();
    List<IAnimal> getAnimals();
    int getCapacity();
    UUID getId();
    boolean isClean();
    EnclosureSize getSize();
    int getVacant();
    Species getSpecies();
}
