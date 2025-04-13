package com.zooweb.application;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.events.AnimalMovedEvent;
import com.zooweb.domain.repositories.IAnimalRepository;
import com.zooweb.domain.repositories.IEnclosureRepository;
import com.zooweb.domain.services.IAnimalTransferService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AnimalTransferService implements IAnimalTransferService {
    private final IAnimalRepository animalRepo;
    private final IEnclosureRepository enclosureRepo;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public AnimalTransferService(IAnimalRepository animalRepo, IEnclosureRepository enclosureRepo, ApplicationEventPublisher eventPublisher) {
        this.animalRepo = animalRepo;
        this.enclosureRepo = enclosureRepo;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void transferAnimal(UUID id, UUID enclosureId) {
        IAnimal animal = animalRepo.getAnimal(id);

        if (animal == null) {
            throw new RuntimeException("Животное с id " + id + " не найдено");
        }

        IEnclosure enclosure = enclosureRepo.getEnclosure(enclosureId);

        if (enclosure == null) {
            throw new RuntimeException("Вольер с id " + enclosureId + " не найден");
        }

        if (enclosure.getVacant() == 0) {
            throw new RuntimeException("Вольер с id " + enclosureId + " заполнен");
        }

        if (animal.getEnclosure() == null) {
            throw new RuntimeException("Животное должно быть в вольере для трансфера");
        }

        eventPublisher.publishEvent(new AnimalMovedEvent(animal, animal.getEnclosure(), enclosure));
    }
}
