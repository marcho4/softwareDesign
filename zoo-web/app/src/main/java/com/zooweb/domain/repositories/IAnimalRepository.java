package com.zooweb.domain.repositories;


import com.zooweb.domain.IAnimal;
import com.zooweb.domain.value_objects.Species;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface IAnimalRepository {
    public void addAnimal(IAnimal animal);
    public void removeAnimal(UUID id);
    public IAnimal getAnimal(UUID id);
    public List<IAnimal> getAllAnimals();
    public List<IAnimal> getAnimalsByEnclosure(UUID enclosureId);
    public List<IAnimal> getAnimalsBySpecies(Species species);
}
