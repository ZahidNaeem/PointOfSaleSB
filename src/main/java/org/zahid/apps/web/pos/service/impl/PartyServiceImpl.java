package org.zahid.apps.web.pos.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.controller.SecurityController;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.repo.PartyRepo;
import org.zahid.apps.web.pos.service.PartyService;
import org.zahid.apps.web.pos.utils.Miscellaneous;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class PartyServiceImpl implements PartyService {

    private PartyRepo partyRepo;
    private final Logger LOG = LogManager.getLogger(PartyServiceImpl.class);

    public PartyServiceImpl() {

    }

    @Autowired
    public PartyServiceImpl(PartyRepo partyRepo) {
        this.partyRepo = partyRepo;
    }

    private Sort orderBy(String column) {
        return new Sort(Sort.Direction.ASC, column);
    }

    @Override
    public Long generateID() {
        return partyRepo.generateID();
    }

    @Override
    public boolean exists(Long id) {
        return partyRepo.existsById(id);
    }

    @Override
    public List<Party> getParties() {
        return partyRepo.findAll(orderBy("partyCode"));
    }

    @Override
    public Party findById(Long id) {
        return partyRepo.getOne(id);
    }

    @Override
    public Party save(Party party) throws DataIntegrityViolationException {
        updateWhoColumns(party);
        return partyRepo.save(party);
    }

    @Override
    public List<Party> save(Set<Party> parties) throws DataIntegrityViolationException {
        parties.forEach(party -> updateWhoColumns(party));
        List<Party> returnParties = partyRepo.saveAll(parties);
        return returnParties;
    }

    @Override
    public void delete(Party party) throws DataIntegrityViolationException {
        partyRepo.delete(party);
    }

    @Override
    public void delete(Set<Party> parties) throws DataIntegrityViolationException {
        partyRepo.deleteAll(parties);
    }

    @Override
    public void deleteById(Long id) throws DataIntegrityViolationException {
        partyRepo.deleteById(id);
    }

    @Override
    public void deleteAll() throws DataIntegrityViolationException {
        partyRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() throws DataIntegrityViolationException {
        partyRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<Party> parties) throws DataIntegrityViolationException {
        partyRepo.deleteInBatch(parties);
    }

    private void updateWhoColumns(Party party) {
        String user = (new SecurityController()).getUsername();
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        if (party.getPartyCode() == null || !partyRepo.existsById(party.getPartyCode())) {
            party.setCreatedBy(user);
            party.setCreationDate(currTime);
        }
        party.setLastUpdatedBy(user);
        party.setLastUpdateDate(currTime);
        party.getPartyBalances().forEach(partyBalance -> {
            int result = Miscellaneous.exists("XXIM_PARTY_BALANCE", "PARTY_BALANCE_ID", partyBalance.getPartyBalanceId());
            LOG.log(Level.INFO, "Record: " + partyBalance.getPartyBalanceId());
            LOG.log(Level.INFO, "Result: " + result);
            if (result < 1) {
                partyBalance.setCreatedBy(user);
                partyBalance.setCreationDate(currTime);
            }
            partyBalance.setLastUpdatedBy(user);
            partyBalance.setLastUpdateDate(currTime);
        });
    }
}
