package com.zooweb.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.entities.Enclosure;
import com.zooweb.domain.repositories.IEnclosureRepository;
import com.zooweb.domain.repositories.IAnimalRepository;
import com.zooweb.domain.services.IEnclosureService;
import com.zooweb.domain.value_objects.EnclosureSize;
import com.zooweb.domain.value_objects.Species;

@Service
public class EnclosureService implements IEnclosureService {
    
    private final IEnclosureRepository enclosureRepo;
    private final IAnimalRepository animalRepo;

    @Autowired
    public EnclosureService(IEnclosureRepository enclosureRepo, IAnimalRepository animalRepo) {
        this.enclosureRepo = enclosureRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public IEnclosure createEnclosure(Species species, int capacity, int width, int height, int length) {
        EnclosureSize size = new EnclosureSize(width, height, length);
        IEnclosure enclosure = new Enclosure(capacity, size, species);
        enclosureRepo.addEnclosure(enclosure);
        return enclosure;
    }

    @Override
    public IEnclosure getEnclosure(UUID id) {
        return enclosureRepo.getEnclosure(id);
    }

    @Override
    public void deleteEnclosure(UUID id) {
        if (enclosureRepo.getEnclosure(id) == null) {
            throw new RuntimeException("Вольер не найден");
        }
        enclosureRepo.removeEnclosure(id);
    }

    @Override
    public void cleanEnclosure(UUID id) {
        enclosureRepo.getEnclosure(id).clean();
    }

    @Override
    public void addAnimalToEnclosure(UUID id, UUID animalId) {
        IEnclosure enclosure = this.getEnclosure(id);

        if (enclosure == null) {
            throw new RuntimeException("Вольер не найден");
        }

        IAnimal animal = animalRepo.getAnimal(animalId);

        if (animal == null) {
            throw new RuntimeException("Животное не найдено");
        }

        if (animal.getSpecies() != enclosure.getSpecies()) {
            throw new RuntimeException("Виды животного и клетки не совпадают");
        }

        if (enclosure.getSpecies() != animal.getSpecies()) {
            throw new RuntimeException("Виды животного и клетки должны совпадать");
        }

        if (animal.getEnclosure() != null) {
            throw new RuntimeException("Животное уже в клетке");
        }

        enclosure.addAnimal(animal);
        animal.move(enclosure);
    }

    @Override
    public void removeAnimalFromEnclosure(UUID id, UUID animalId) {
        IEnclosure enclosure = enclosureRepo.getEnclosure(id);
        if (enclosure == null) {
            throw new RuntimeException("Вольер не найден");
        }


        IAnimal animal = animalRepo.getAnimal(animalId);

        if (animal == null) {
            throw new RuntimeException("Животное не найдено");
        }

        enclosure.removeAnimal(animal);
    }

    @Override
    public List<IAnimal> getAnimalsByEnclosure(UUID id) {
        return enclosureRepo.getEnclosure(id).getAnimals();
    }

    @Override
    public List<IEnclosure> getAllEnclosures() {
        return enclosureRepo.getEnclosures();
    }
   
}
