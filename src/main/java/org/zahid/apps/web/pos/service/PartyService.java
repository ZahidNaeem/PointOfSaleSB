package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Party;

import java.util.List;
import java.util.Set;

public interface PartyService {

    public Long generateID();

    public boolean exists(Long id);

    public Party findById(Long id);

    public List<Party> getParties();

    public Party save(Party party);

    public List<Party> save(Set<Party> parties);

    public void delete(Party party);

    public void delete(Set<Party> parties);

    public void deleteById(Long id);

    public void deleteAll();

    public void deleteAllInBatch();

    public void deleteInBatch(Set<Party> parties);
}
