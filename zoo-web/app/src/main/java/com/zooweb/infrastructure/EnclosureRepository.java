package com.zooweb.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.repositories.IEnclosureRepository;

@Repository
public class EnclosureRepository implements IEnclosureRepository {
    private HashMap<UUID, IEnclosure> enclosures;

    public EnclosureRepository() {
        this.enclosures = new HashMap<>();
    }

    @Override
    public void addEnclosure(IEnclosure enclosure) {
        enclosures.put(enclosure.getId(), enclosure);
    }

    @Override
    public void removeEnclosure(UUID enclosureId) {
        if (enclosures.get(enclosureId) == null) {
            throw new RuntimeException("Enclosure not found");
        }
        enclosures.remove(enclosureId);
    }

    @Override
    public IEnclosure getEnclosure(UUID enclosureId) {
        if (enclosures.get(enclosureId) == null) {
            throw new RuntimeException("Enclosure not found");
        }
        return enclosures.get(enclosureId);
    }

    @Override
    public List<IEnclosure> getEnclosures() {
        return new ArrayList<>(enclosures.values());
    }

    @Override
    public int getVacantCapacity(UUID enclosureId) {
        if (enclosures.get(enclosureId) == null) {
            throw new RuntimeException("Enclosure not found");
        }
        return enclosures.get(enclosureId).getVacant();
    }
}
