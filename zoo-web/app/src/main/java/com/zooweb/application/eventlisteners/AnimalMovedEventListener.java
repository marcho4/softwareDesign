package com.zooweb.application.eventlisteners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.events.AnimalMovedEvent;


@Component
public class AnimalMovedEventListener {
    
    @EventListener
    public void animalMovedHandler(AnimalMovedEvent event) {
        System.out.println("Получил событие перемещения животного " + event.getAnimal().getName());

        IAnimal animal = event.getAnimal();
        IEnclosure enclosure = event.getTo();

        animal.move(enclosure);

        event.getFrom().removeAnimal(animal);
        event.getTo().addAnimal(animal);

        System.out.println("Животное " + animal.getName() + " перемещено успешно");
    }
}
