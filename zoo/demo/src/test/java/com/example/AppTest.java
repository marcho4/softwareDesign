package com.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.animals.*;


public class AppTest 
{
    /**
     * Проверка добавляются ли корректно не здоровые животные разных типов в зоопарк
     */
    @Test
    public void AddingNotHealtyAnimals()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Zoo zoo = context.getBean(Zoo.class);

        Animal animal = new Monkey(10, false, 10);
        assertTrue(false == animal.addToTheZoo(zoo));

        Animal animal3 = new Tiger(10, false);
        assertTrue(false == animal3.addToTheZoo(zoo));

        assertTrue(zoo.getAllAnimals().size() == 0);
    }

    
    /**
     * Проверка добавляются ли корректно здоровые животные разных типов в зоопарк
     */
    @Test
    public void AddingHealthy()
    {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Zoo zoo = context.getBean(Zoo.class);

        Animal animal2 = new Rabbit(10, true, 10);
        assertTrue(true == animal2.addToTheZoo(zoo));

        Animal animal4 = new Wolf(10, true);
        assertTrue(true == animal4.addToTheZoo(zoo));

        assertTrue(zoo.getAllAnimals().size() == 2);
    }

    /**
     * Проверяет корректно ли обрабатываются больные и здоровые животные и добавляются ли они в зоопарк
     */
    @Test 
    public void CorrectCountOfAnimals() {

        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Zoo zoo = context.getBean(Zoo.class);

        Animal animal1 = new Rabbit(10, true, 10);
        Animal animal2 = new Rabbit(10, true, 10);
        Animal animal3 = new Rabbit(10, false, 10);
        Animal animal4 = new Wolf(10, true);
        Animal animal5 = new Wolf(10, true);
        Animal animal6 = new Wolf(10, false);
        
        animal1.addToTheZoo(zoo);
        animal2.addToTheZoo(zoo);
        animal3.addToTheZoo(zoo);
        animal4.addToTheZoo(zoo);
        animal5.addToTheZoo(zoo);
        animal6.addToTheZoo(zoo);


        assertTrue(zoo.getAllAnimals().size() == 4);
    }

    /**
     * Проверяет корректно ли обрабатываются животные с разным количеством еды и добавляются ли они в зоопарк
     */
    @Test
    public void CorrectFoodCount() {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Zoo zoo = context.getBean(Zoo.class);

        Animal animal1 = new Rabbit(20, true, 10);
        Animal animal2 = new Rabbit(10, false, 10);
        Animal animal3 = new Rabbit(40, false, 10);
        Animal animal4 = new Wolf(110, true);
        Animal animal5 = new Wolf(52, true);
        Animal animal6 = new Wolf(66, false);
        
        animal1.addToTheZoo(zoo);
        animal2.addToTheZoo(zoo);
        animal3.addToTheZoo(zoo);
        animal4.addToTheZoo(zoo);
        animal5.addToTheZoo(zoo);
        animal6.addToTheZoo(zoo);

        assertTrue(zoo.getDailyFood() == 182);
    }

    /**
     * Проверяет корректно ли обрабатываются животные с разным количеством доброты и добавляются ли они в зоопарк
     */
    @Test
    public void CorrectCuttiesCount() {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Zoo zoo = context.getBean(Zoo.class);

        Animal animal1 = new Rabbit(20, true, 8);
        Animal animal2 = new Monkey(10, true, 9);
        Animal animal3 = new Monkey(40, true, 5);
        Animal animal4 = new Monkey(110, true, 2);
        Animal animal5 = new Monkey(110, true, 2);
        Animal animal6 = new Rabbit(20, true, 1);
        Animal animal7 = new Wolf(110, true);
        Animal animal8 = new Tiger(52, true);
        Animal animal9 = new Wolf(66, false);
        
        animal1.addToTheZoo(zoo);
        animal2.addToTheZoo(zoo);
        animal3.addToTheZoo(zoo);
        animal4.addToTheZoo(zoo);
        animal5.addToTheZoo(zoo);
        animal6.addToTheZoo(zoo);
        animal7.addToTheZoo(zoo);
        animal8.addToTheZoo(zoo);
        animal9.addToTheZoo(zoo);

        assertTrue(zoo.getCutties().size() == 3);
    }
}
