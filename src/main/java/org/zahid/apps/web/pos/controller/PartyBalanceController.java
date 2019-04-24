package org.zahid.apps.web.pos.controller;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.entity.PartyBalance;
import org.zahid.apps.web.pos.service.PartyBalanceService;
import org.zahid.apps.web.pos.utils.JsfUtils;
import org.zahid.apps.web.pos.utils.Miscellaneous;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
//@Transactional
@Named("partyBalanceController")
//@Named("partyBalanceController")
//@SessionScoped
// @ELBeanName(value = "partyController")
// @Join(path = "/parties", to = "/views/party/partyList.jsf")
public class PartyBalanceController implements Serializable {

    private PartyBalanceService partyBalanceService;
    //    @Autowired
//    private PartyController partyController;
//    private Party party;
    private List<PartyBalance> balanceList;
    private ListDataModel<PartyBalance> balanceDataModel;
    private PartyBalance selected;
    private final Set<PartyBalance> dmlRecords = new HashSet<>();
    //    private boolean dataFromDBFlag = false;
    private static final Logger LOG = Logger.getLogger(PartyBalanceController.class.getName());

    @PostConstruct
    public void loadData() {
//        Hibernate.initialize(partyBalanceService.getCurrentParty().getPartyBalances());
        balanceList = partyBalanceService.getCurrentPartyBalanceList();
//        party = partyController.getNavigationController().object;
//        party.getPartyBalances().size();
//        balanceList = party.getPartyBalances();
        balanceDataModel = new ListDataModel<>(balanceList);
        // partyBalances = partyBalanceService.getPartyBalanceList();
        // balanceDataModel.setWrappedData(partyBalances);
    }

    public PartyBalanceController() {

    }

    @Autowired
    public PartyBalanceController(PartyBalanceService partyBalanceService) {
        this.partyBalanceService = partyBalanceService;
    }

    public List<PartyBalance> getBalanceList() {
        return balanceList;
    }

    public ListDataModel<PartyBalance> getBalanceDataModel() {
        return balanceDataModel;
    }

    public List<PartyBalance> updateBalanceList() {
        balanceList = partyBalanceService.getCurrentPartyBalanceList();
//        if (dataFromDBFlag) {
//            balanceList = partyBalanceService.getCurrentPartyBalanceListFromDB();
//            dataFromDBFlag = false;
//        } else {
//            balanceList = partyBalanceService.getCurrentPartyBalanceList();
//        }
        return balanceList;
    }

    //    org.apache.commons.beanutils.PropertyUtilsBean.copyProperties();
    public void updateBalanceDataModel() {
        System.out.println("updateBalanceDataModel called");
        balanceDataModel.setWrappedData(updateBalanceList());
//        if (dataFromDBFlag) {
//            dataFromDBFlag = false;
//        }
    }

    public PartyBalance getSelected() {
        return selected;
    }

    public void setSelected(PartyBalance selected) {
        this.selected = selected;
    }

    public Set<PartyBalance> getDmlRecords() {
        return dmlRecords;
    }

    //    public void onValueChange(ValueChangeEvent vce) {
//        logger.info("Old: " + vce.getOldValue());
//        logger.info("New: " + vce.getNewValue());
//        dmlRecords.add(selected);
//    }
//
//    public void onValueChange(AjaxBehaviorEvent event) {
//        dmlRecords.add(selected);
//        logger.info("Changed Balance ID: " + selected.getPartyBalanceId());
//    }
    public void onCellEdit(CellEditEvent event) {
        DataTable dataTable = (DataTable) event.getSource();
        selected = (PartyBalance) dataTable.getRowData();
        dmlRecords.add(selected);
        LOG.log(Level.INFO, "onCellEdit Balance ID: {0}", selected.toString());
        LOG.log(Level.INFO, "onCellEdit New Value: {0}", event.getNewValue());
    }

    //    public void onDateSelect(SelectEvent event) {
//        logger.info("Date: " + event.getObject());
//        dmlRecords.add(selected);
//        logger.info("Changed Balance ID: " + selected.getPartyBalanceId());
//    }
    // public List<PartyBalance> addPartyBalance(PartyBalance partyBalance) {
    // partyController.getNavigationController().object.getPartyBalances().add(partyBalance);
    // balanceList = partyController.getNavigationController().object.getPartyBalances();
    // return balanceList;
    // }
    public PartyBalance prepareCreate() {
//        selected = balanceList.get(0);
        int indx;
        PartyBalance balance = new PartyBalance();
        Party party = partyBalanceService.getCurrentParty();
        LOG.log(Level.INFO, "Party: {0}", party.toString());
        balance.setParty(party);
        if (balanceList == null) {
            balanceList = new ArrayList<>();
        }
        Long id = (partyBalanceService.generateID() >= (balanceList.size() + 1) ? partyBalanceService.generateID() : (balanceList.size() + 1));
        LOG.log(Level.INFO, "Balance ID: {0}", id);
        balance.setPartyBalanceId(id);
//        balanceList = partyBalanceService.addBalanceToBalanceList(balance);
        LOG.log(Level.INFO, "Balance before: {0}", balanceList.size());
        balanceList.add(balance);
        LOG.log(Level.INFO, "Balance after: {0}", balanceList.size());
        indx = balanceList.indexOf(balance);
        balanceDataModel.setWrappedData(balanceList);
        balanceDataModel.setRowIndex(indx);
        selected = balanceDataModel.getRowData();
        dmlRecords.add(selected);
        return selected;
    }

    public void prepareDelete() {
        selected = balanceDataModel.getRowData();
    }

    public void destroy() {
        if (selected != null) {
            LOG.log(Level.INFO, "Balance ID: {0}", selected.toString());
            LOG.log(Level.INFO, "Selected Row: {0}", selected.getPartyBalanceId());
            LOG.log(Level.INFO, "Balance before: {0}", balanceList.size());
            if (partyBalanceService.exists(selected.getPartyBalanceId())) {
                boolean removed = true;
                try {
                    partyBalanceService.delete(selected);
                } catch (DataIntegrityViolationException e) {
                    JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
                    removed = false;
                }
                if (!removed) {
                    return;
                }
            }
            balanceList.remove(selected);
            LOG.log(Level.INFO, "Balance after: {0}", balanceList.size());
        }
    }

    public void save() {
        if (dmlRecords.size() > 0) {
            try {
                List<PartyBalance> balances = partyBalanceService.save(dmlRecords);
//                partyBalanceService.attachBalanceWithParty(balances);
                JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Party Balance(s) saved successfully");
                dmlRecords.clear();
            } catch (DataIntegrityViolationException e) {
                JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
            }
        }
    }

    public void undoChanges() {
        LOG.log(Level.INFO, "Changed Balances: {0}", dmlRecords.size());
        Set<Party> parties = new HashSet<>();
        dmlRecords.forEach((balance) -> {
            parties.add(balance.getParty());
        });
        parties.forEach((party) -> {
            party.setPartyBalances(partyBalanceService.getPartyBalanceListFromDB(party));
        });
        dmlRecords.clear();
//        RequestContext.getCurrentInstance().getAjaxRequestBuilder().update()
//        PrimeFaces.current().ajax().update(ae.getComponent().getParent().getParent().getClientId());
//        logger.info("Client ID: " + ae.getComponent().getParent().getParent().getClientId());
        JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Undo Changes Successful");
    }

}
