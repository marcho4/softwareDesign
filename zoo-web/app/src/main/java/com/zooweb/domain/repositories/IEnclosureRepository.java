package com.zooweb.domain.repositories;

import com.zooweb.domain.IEnclosure;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface IEnclosureRepository {
    public void addEnclosure(IEnclosure enclosure);
    public void removeEnclosure(UUID enclosureId);
    public IEnclosure getEnclosure(UUID enclosureId);
    public List<IEnclosure> getEnclosures();
    public int getVacantCapacity(UUID enclosureId);
}
