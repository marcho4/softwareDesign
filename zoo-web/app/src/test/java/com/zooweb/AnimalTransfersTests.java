package com.zooweb;


import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.services.IAnimalService;
import com.zooweb.domain.services.IAnimalTransferService;
import com.zooweb.domain.services.IEnclosureService;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.Gender;
import com.zooweb.domain.value_objects.HealthStatus;
import com.zooweb.domain.value_objects.Species;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimalTransfersTests {

    @Autowired
    private IAnimalTransferService animalTransferService;

    @Autowired
    private IAnimalService animalService;

    @Autowired
    private IEnclosureService enclosureService;

    @Test(expected = RuntimeException.class)
    public void AnimalTransferTest_1() {
        IAnimal animal = animalService.createAnimal(
                Species.AMPHIBIANS,
                "пупс",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                Food.FRUITS,
                HealthStatus.HEALTHY
        );

        IEnclosure enclosure = enclosureService.createEnclosure(
                Species.AMPHIBIANS,
                3,
                10,
                20,
                30
        );

        assertNotNull(enclosureService.getEnclosure(enclosure.getId()));

        animalTransferService.transferAnimal(animal.getId(), enclosure.getId());
    }

    @Test(expected = RuntimeException.class)
    public void AnimalTransferTest_2() {
        IAnimal animal = animalService.createAnimal(
                Species.AMPHIBIANS,
                "пупс",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                Food.FRUITS,
                HealthStatus.HEALTHY
        );

        IEnclosure enclosure = enclosureService.createEnclosure(
                Species.AMPHIBIANS,
                3,
                10,
                20,
                30
        );

        assertNotNull(enclosureService.getEnclosure(enclosure.getId()));

        enclosureService.addAnimalToEnclosure(enclosure.getId(), animal.getId());

        IEnclosure enclosure_2 = enclosureService.createEnclosure(
                Species.INSECTS,
                3,
                10,
                20,
                30
        );

        animalTransferService.transferAnimal(animal.getId(), enclosure_2.getId());
    }

    @Test()
    public void AnimalTransferTest_3() {
        IAnimal animal = animalService.createAnimal(
                Species.AMPHIBIANS,
                "пупс",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                Food.FRUITS,
                HealthStatus.HEALTHY
        );

        IEnclosure enclosure = enclosureService.createEnclosure(
                Species.AMPHIBIANS,
                3,
                10,
                20,
                30
        );

        assertNotNull(enclosureService.getEnclosure(enclosure.getId()));

        enclosureService.addAnimalToEnclosure(enclosure.getId(), animal.getId());

        IEnclosure enclosure_2 = enclosureService.createEnclosure(
                Species.AMPHIBIANS,
                3,
                10,
                20,
                30
        );

        animalTransferService.transferAnimal(animal.getId(), enclosure_2.getId());
        assertEquals(animal.getEnclosure(), enclosure_2);
        assertEquals(3, enclosure.getVacant());
        assertTrue(enclosure.getAnimals().isEmpty());
        assertEquals(2, enclosure_2.getVacant());
        assertTrue(enclosure_2.getAnimals().contains(animal));
    }

    @Test(expected = RuntimeException.class)
    public void AnimalTransferTest_4() {
        IAnimal animal = animalService.createAnimal(
                Species.AMPHIBIANS,
                "пупс",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                Food.FRUITS,
                HealthStatus.HEALTHY
        );

        IEnclosure enclosure = enclosureService.createEnclosure(
                Species.AMPHIBIANS,
                3,
                10,
                20,
                30
        );

        assertNotNull(enclosureService.getEnclosure(enclosure.getId()));

        enclosureService.addAnimalToEnclosure(enclosure.getId(), animal.getId());

        IAnimal animal2 = animalService.createAnimal(
                Species.AMPHIBIANS,
                "малышка",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                Food.FRUITS,
                HealthStatus.HEALTHY
        );

        IEnclosure enclosure_2 = enclosureService.createEnclosure(
                Species.AMPHIBIANS,
                1,
                10,
                20,
                30
        );
        enclosureService.addAnimalToEnclosure(enclosure_2.getId(), animal2.getId());

        animalTransferService.transferAnimal(animal.getId(), enclosure_2.getId());
    }
}
