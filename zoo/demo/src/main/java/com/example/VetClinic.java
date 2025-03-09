package com.example;
import org.springframework.stereotype.Component;

@Component
public class VetClinic {

    public VetClinic() {}

    public Boolean checkAnimal(Animal animal) {
        return animal.isHealthy();
    }
}
