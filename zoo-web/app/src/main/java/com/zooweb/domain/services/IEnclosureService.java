package com.zooweb.domain.services;

import java.util.List;
import java.util.UUID;

import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.value_objects.Species;

import org.springframework.stereotype.Service;

@Service
public interface IEnclosureService {
    public IEnclosure createEnclosure(Species species, int capacity, int width, int height, int length);
    public IEnclosure getEnclosure(UUID id);
    public void deleteEnclosure(UUID id);
    
    public void cleanEnclosure(UUID id);

    public void addAnimalToEnclosure(UUID id, UUID animalId);
    public void removeAnimalFromEnclosure(UUID id, UUID animalId);

    public List<IAnimal> getAnimalsByEnclosure(UUID id);
    public List<IEnclosure> getAllEnclosures();
}
