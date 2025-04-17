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
    private final HashMap<UUID, IEnclosure> enclosures;

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
            throw new RuntimeException("Вольер не найден");
        }
        enclosures.remove(enclosureId);
    }

    @Override
    public IEnclosure getEnclosure(UUID enclosureId) {
        if (enclosures.get(enclosureId) == null) {
            throw new RuntimeException("Вольер не найден");
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
            throw new RuntimeException("Вольер не найден");
        }
        return enclosures.get(enclosureId).getVacant();
    }
}
