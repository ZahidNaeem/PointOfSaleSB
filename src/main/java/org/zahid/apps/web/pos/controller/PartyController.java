package org.zahid.apps.web.pos.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.zahid.apps.web.pos.entity.Party;
import org.zahid.apps.web.pos.service.PartyService;
import org.zahid.apps.web.pos.utils.JsfUtils;
import org.zahid.apps.web.pos.utils.Miscellaneous;
import org.zahid.apps.web.pos.utils.NavigationController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

//@Controller
//@Transactional
//@ManagedBean(name = "partyController")
@Named("partyController")
//@SessionScoped
//@Scope(value = "session")
//@ELBeanName(value = "partyController")
//@Join(path = "/parties", to = "/views/party/partyList.jsf")
public class PartyController implements Serializable {

    private PartyService partyService;
    //    @Autowired
//    private PartyStockController partyStockController;
    private List<Party> parties;
    // ListDataModel<Party> partyDataModel;
    private NavigationController<Party> navigationController;
    private final Set<Party> dmlRecords = new HashSet<>();

    // private Party selected;
    // private Set<Long> updatedPartyCodes = new HashSet<>();

    private static final Logger LOG = LogManager.getLogger(PartyController.class);


    public PartyController() {

    }

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    //     @Deferred
    // @RequestAction
    // @IgnorePostback
    @PostConstruct
    public void loadData() {
        partyOperations("INIT");
        // parties = partyService.getParties();
        // // partyDataModel = new ListDataModel<Party>();
        // // partyDataModel.setWrappedData(parties);
        // // this.updateDataModel(parties);
        // navigationController = new NavigationController<Party>(parties);
        // selected = navigationController.first();
    }

    private void partyOperations(String operation) {
        Party party = null;
        if (!operation.equalsIgnoreCase("INIT")) {
            party = navigationController.object;
        }
        int indx;
        if (operation.equalsIgnoreCase("DELETE")) {
            indx = parties.indexOf(party);
            boolean removed = true;
            if (partyService.exists(party.getPartyCode())) {
                try {
                    partyService.delete(party);
                } catch (DataIntegrityViolationException e) {
                    JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
                    removed = false;
                }
            }
            if (!removed) {
                return;
            }
            parties.remove(indx);
            LOG.log(Level.INFO, "parties.remove(indx) executed");
            if ((indx - 1) >= 0) {
                navigationController.go(indx - 1);
            }
        } else if (operation.equalsIgnoreCase("INIT") || operation.equalsIgnoreCase("UNDO")) {
            parties = partyService.getParties();
            navigationController = new NavigationController<>(parties, 0);
            navigationController.first();
            dmlRecords.clear();
        }
    }

    public List<String> getPartyType() {
        List<String> partyTypeList = asList("Buyer", "Supplier");
        return partyTypeList;
    }

    public List<Party> getParties() {
        return parties;
    }

    public Set<Party> getDmlRecords() {
        return dmlRecords;
    }

    public NavigationController<Party> getNavigationController() {
        return navigationController;
    }

    public void prepareCreate() {
        Party party = new Party();
//        party.setPartyDesc("Desc");
//        party.setPartyUom("Party");
//        party.setEffectiveStartDate(new Date());
        party.setPartyCode(partyService.generateID() >= (parties.size() + 1) ? partyService.generateID() : (parties.size() + 1));
        parties.add(party);
        navigationController = new NavigationController<>(parties, parties.indexOf(party));
        dmlRecords.add(party);
    }

    public void onValueChanged(ValueChangeEvent vce) {
        LOG.info("*************** onValueChanged Start ***************");
        LOG.info("Component: " + vce.getComponent().getClientId());
        LOG.info("Old Value: " + vce.getOldValue());
        LOG.info("New Value: " + vce.getNewValue());
        // Long partyCode = navigationController.getList().getRowData().getPartyCode();
        // logger.info("Party Code: " + partyCode);
//        String[] compareParty = {null};
//        if (vce.getComponent().getClientId().equalsIgnoreCase("PartyForm:partyDesc")) {
//            LOG.info("Party Desc. Component");
//            compareParty[0] = String.valueOf(vce.getOldValue());
//        } else {
//            LOG.info("Other Component");
//            compareParty[0] = navigationController.object.getPartyDesc();
//        }
//        LOG.log(Level.INFO, "vce dmlRecords before: {0}", dmlRecords.size());
//        dmlRecords.forEach(party -> {
//            LOG.log(Level.INFO, "Component comparison: {0}", compareParty[0].equalsIgnoreCase(party.getPartyDesc()));
//            if (compareParty[0].equalsIgnoreCase(party.getPartyDesc())) {
//                LOG.log(Level.INFO, "Party to remove: {0}", party.toString());
//                dmlRecords.remove(party);
//            }
//        });
//        LOG.log(Level.INFO, "vce dmlRecords after: {0}", dmlRecords.size());
        dmlRecords.add(navigationController.object);
        // updatedPartyCodes.add(partyCode);
        LOG.info("*************** onValueChanged End ***************");
    }

    public void onValueChanged(AjaxBehaviorEvent event) {
        // logger.info("Ajax Value: " +
        // event.getComponent().getAttributes().get("startDate"));
        // Long partyCode = navigationController.getList().getRowData().getPartyCode();
        // logger.info("Party Code: " + partyCode);
        dmlRecords.add(navigationController.object);
        // updatedPartyCodes.add(partyCode);
    }

    public void destroy() {
        partyOperations("DELETE");
    }

    public void save() {
        LOG.info("*************** PartyController Save Start ***************");
        if (dmlRecords.size() > 0) {
            try {
                partyService.save(dmlRecords);
                dmlRecords.clear();
                JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Save Successful", "Party(s) saved successfully");
            } catch (DataIntegrityViolationException e) {
                JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
            } catch (Exception e) {
                JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
            }
        }
        LOG.info("*************** PartyController Save End   ***************");
    }

    public void undoChanges() {
        partyOperations("UNDO");
    }
}
