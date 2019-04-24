package org.zahid.apps.web.pos.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.controller.PartyController;
import org.zahid.apps.web.pos.controller.SecurityController;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;
import org.zahid.apps.web.pos.repo.PartyBalanceRepo;
import org.zahid.apps.web.pos.service.PartyBalanceService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class PartyBalanceServiceImpl implements PartyBalanceService {

    @Autowired
    private PartyBalanceRepo partyBalanceRepo;
    @Autowired
    private PartyController partyController;
    private final Logger logger = Logger.getLogger(PartyBalanceServiceImpl.class.getName());

    @Override
    public Long generateID() {
        return partyBalanceRepo.generateID();
    }

    @Override
    public boolean exists(Long id) {
        return partyBalanceRepo.existsById(id);
    }

    @Override
    public List<PartyBalance> getPartyBalanceList() {
        return partyBalanceRepo.findAll();
    }

    @Override
    public List<PartyBalance> getCurrentPartyBalanceList() {
        return partyController.getNavigationController().object.getPartyBalances();
    }

    @Override
    public List<PartyBalance> getCurrentPartyBalanceListFromDB() {
        return partyBalanceRepo.findAllByParty(partyController.getNavigationController().object);
    }

    @Override
    public List<PartyBalance> getPartyBalanceListFromDB(Party party) {
        return partyBalanceRepo.findAllByParty(party);
    }

    @Override
    public PartyBalance getBalanceById(Long id) {
        return partyBalanceRepo.getOne(id);
    }

    //	@Override
//	public List<PartyBalance> findAllByParty(Party party) {
//		return party.getPartyBalances();
//	}
    @Override
    public List<PartyBalance> addBalanceToBalanceList(PartyBalance stock) {
        getCurrentPartyBalanceList().add(stock);
        return getCurrentPartyBalanceList();
    }

    @Override
    public Party getCurrentParty() {
        return partyController.getNavigationController().object;
    }

    @Override
    public PartyBalance attachBalanceWithParty(PartyBalance stock) {
        partyController.getNavigationController().object.getPartyBalances().add(stock);
        return stock;
    }

//    @Override
//    public List<PartyBalance> attachStockWithParty(List<PartyBalance> stocks) {
//        --partyController.getNavigationController().object.getPartyBalances().addAll(stocks);
//        return stocks;
//    }

    //	@Override
//	public PartyBalance prepareCreate() {
//		PartyBalance stock = new PartyBalance();
//		stock.setParty(partyController.getNavigationController().object);
//		partyController.getNavigationController().object.getPartyBalances().add(stock);
//		return stock;
//	}
    @Override
    public PartyBalance save(PartyBalance partyBalance) {
        String user = (new SecurityController()).getUsername();
        logger.info("User: " + user);
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        if (!partyBalanceRepo.existsById(partyBalance.getPartyBalanceId())) {
            partyBalance.setCreatedBy(user);
            partyBalance.setCreationDate(currTime);
        }
        partyBalance.setLastUpdatedBy(user);
        partyBalance.setLastUpdateDate(currTime);
        return partyBalanceRepo.save(partyBalance);
    }

    @Override
    public List<PartyBalance> save(Set<PartyBalance> partyBalances) {
        if (partyController.getDmlRecords().size() > 0) {
            System.out.println("Parties: " + partyController.getDmlRecords().size());
            partyController.save();
        }
        for (PartyBalance partyBalance : partyBalances) {
            String user = (new SecurityController()).getUsername();
            Timestamp currTime = new Timestamp(System.currentTimeMillis());
            if (partyBalance.getPartyBalanceId() == null || !partyBalanceRepo.existsById(partyBalance.getPartyBalanceId())) {
                partyBalance.setCreatedBy(user);
                partyBalance.setCreationDate(currTime);
            }
            partyBalance.setLastUpdatedBy(user);
            partyBalance.setLastUpdateDate(currTime);
        }
        return partyBalanceRepo.saveAll(partyBalances);
    }

    @Override
    public void delete(PartyBalance partyBalance) throws DataIntegrityViolationException {
        partyBalanceRepo.delete(partyBalance);
    }

    @Override
    public void delete(Set<PartyBalance> partyBalances) throws DataIntegrityViolationException {
        partyBalanceRepo.deleteAll(partyBalances);
    }

    @Override
    public void deleteById(Long id) throws DataIntegrityViolationException {
        partyBalanceRepo.deleteById(id);
    }

    @Override
    public void deleteAll() throws DataIntegrityViolationException {
        partyBalanceRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() throws DataIntegrityViolationException {
        partyBalanceRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<PartyBalance> partyBalances) throws DataIntegrityViolationException {
        partyBalanceRepo.deleteInBatch(partyBalances);
    }

}
