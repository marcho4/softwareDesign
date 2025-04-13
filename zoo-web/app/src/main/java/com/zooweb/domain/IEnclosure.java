package com.zooweb.domain;

import java.util.List;
import java.util.UUID;

import com.zooweb.domain.value_objects.EnclosureSize;
import com.zooweb.domain.value_objects.Species;

public interface IEnclosure {
    public void addAnimal(IAnimal animal);
    public void removeAnimal(IAnimal animal);
    public void clean();
    public List<IAnimal> getAnimals();
    public int getCapacity();
    public UUID getId();
    public boolean isClean();
    public EnclosureSize getSize();
    public int getVacant();
    public Species getSpecies();
}
