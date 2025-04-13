package com.zooweb.infrastructure;

import java.util.UUID;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.repositories.IAnimalRepository;
import com.zooweb.domain.value_objects.Species;

import org.springframework.stereotype.Repository;

@Repository
public class AnimalRepository implements IAnimalRepository {
    private HashMap<UUID, IAnimal> animals;

    public AnimalRepository() {
        this.animals = new HashMap<>();
    }

    @Override
    public void addAnimal(IAnimal animal) {
        animals.put(animal.getId(), animal);
    }

    @Override
    public void removeAnimal(UUID id) {
        animals.remove(id);
    }

    @Override
    public List<IAnimal> getAllAnimals() {
        return animals.values().stream().collect(Collectors.toList());
    }


    @Override
    public IAnimal getAnimal(UUID id) {
        return animals.get(id);
    }

    @Override
    public List<IAnimal> getAnimalsByEnclosure(UUID enclosureId) {
        return animals.values().stream().filter(animal -> animal.getEnclosure().getId().equals(enclosureId)).collect(Collectors.toList());
    }

    @Override
    public List<IAnimal> getAnimalsBySpecies(Species species) {
        return animals.values().stream().filter(animal -> animal.getSpecies().equals(species)).collect(Collectors.toList());
    }
}
