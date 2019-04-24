package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;

import java.util.List;
import java.util.Set;

public interface PartyBalanceService {

    public Long generateID();

    public boolean exists(Long id);

    public List<PartyBalance> getPartyBalanceList();

    public List<PartyBalance> getCurrentPartyBalanceList();

    public List<PartyBalance> getCurrentPartyBalanceListFromDB();

    public List<PartyBalance> getPartyBalanceListFromDB(Party party);

    public PartyBalance getBalanceById(Long id);

    public List<PartyBalance> addBalanceToBalanceList(PartyBalance balance);

    public Party getCurrentParty();

    public PartyBalance attachBalanceWithParty(PartyBalance balance);

//    public List<PartyBalance> attachBalanceWithParty(List<PartyBalance> balances);

//    public List<PartyBalance> findAllByParty(Party party);

    // public PartyBalance prepareCreate();

    public PartyBalance save(PartyBalance partyBalance);

    public List<PartyBalance> save(Set<PartyBalance> partyBalances);

    public void delete(PartyBalance partyBalance);

    public void delete(Set<PartyBalance> partyBalances);

    public void deleteById(Long id);

    public void deleteAll();

    public void deleteAllInBatch();

    public void deleteInBatch(Set<PartyBalance> partyBalances);

}
