package com.zooweb.domain.services;

import java.util.UUID;


public interface IAnimalTransferService {
    public void transferAnimal(UUID id, UUID enclosureId);
}
