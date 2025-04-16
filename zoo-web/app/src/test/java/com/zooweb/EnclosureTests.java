package com.zooweb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.value_objects.Food;
import com.zooweb.domain.value_objects.Gender;
import com.zooweb.domain.value_objects.HealthStatus;
import com.zooweb.domain.value_objects.Species;

import com.zooweb.domain.services.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnclosureTests {

    @Autowired
    private IEnclosureService enclosureService;
    
    @Autowired
    private IAnimalService animalService;
    
    
    @Test
    public void testCreateEnclosure() {
        IEnclosure enclosure = enclosureService.createEnclosure(Species.MAMMALS, 5, 10, 8, 15);
        assertNotNull(enclosure);
        assertEquals(Species.MAMMALS, enclosure.getSpecies());
        assertEquals(5, enclosure.getCapacity());
        assertEquals(10, enclosure.getSize().getWidth());
        assertEquals(8, enclosure.getSize().getHeight());
        assertEquals(15, enclosure.getSize().getLength());
        assert (enclosureService.getEnclosure(enclosure.getId()) != null);
    }
    
    @Test
    public void testGetEnclosure() {
        IEnclosure enclosure = enclosureService.createEnclosure(Species.BIRDS, 3, 5, 6, 10);
        UUID id = enclosure.getId();
        
        IEnclosure retrieved = enclosureService.getEnclosure(id);
        assertNotNull(retrieved);
        assertEquals(id, retrieved.getId());
        assertEquals(Species.BIRDS, retrieved.getSpecies());
    }
    
    @Test
    public void testCleanEnclosure() {
        IEnclosure enclosure = enclosureService.createEnclosure(Species.REPTILES, 2, 5, 3, 8);
        
        enclosureService.cleanEnclosure(enclosure.getId());
        assertTrue(enclosure.isClean());
    }
    
    @Test
    public void testAddAnimalToEnclosure() {
        IEnclosure enclosure = enclosureService.createEnclosure(Species.MAMMALS, 3, 8, 7, 12);
        IAnimal animal = animalService.createAnimal(Species.MAMMALS, "Leo", LocalDate.now().minusYears(2), 
                                                  Gender.MALE, Food.MEAT, HealthStatus.HEALTHY);
        
        enclosureService.addAnimalToEnclosure(enclosure.getId(), animal.getId());
        
        List<IAnimal> animals = enclosureService.getAnimalsByEnclosure(enclosure.getId());
        assertTrue(animals.contains(animal));
    }
    
    @Test(expected = RuntimeException.class)
    public void testDeleteEnclosure() {
        IEnclosure enclosure = enclosureService.createEnclosure(Species.FISH, 10, 20, 15, 25);
        UUID id = enclosure.getId();
        
        enclosureService.deleteEnclosure(id);
        enclosureService.getEnclosure(id);
    }
}