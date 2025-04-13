package com.zooweb.domain.services;

import java.util.UUID;

import org.springframework.stereotype.Service;


@Service
public interface IAnimalTransferService {
    public void transferAnimal(UUID id, UUID enclosureId);
}
