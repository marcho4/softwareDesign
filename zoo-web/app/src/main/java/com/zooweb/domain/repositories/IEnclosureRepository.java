package com.zooweb.domain.repositories;

import com.zooweb.domain.IEnclosure;

import java.util.List;
import java.util.UUID;


public interface IEnclosureRepository {
    void addEnclosure(IEnclosure enclosure);
    void removeEnclosure(UUID enclosureId);
    IEnclosure getEnclosure(UUID enclosureId);
    List<IEnclosure> getEnclosures();
    int getVacantCapacity(UUID enclosureId);
}
