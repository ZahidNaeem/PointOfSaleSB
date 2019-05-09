package org.zahid.apps.web.pos.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.service.ItemService;
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

//@Controller
//@Transactional
//@ManagedBean(name = "itemController")
@Named("itemController")
//@SessionScoped
//@Scope(value = "session")
//@ELBeanName(value = "itemController")
//@Join(path = "/items", to = "/views/item/itemList.jsf")
public class ItemController implements Serializable {

    private ItemService itemService;
    //    @Autowired
//    private ItemStockController itemStockController;
    private List<Item> items;
    // ListDataModel<Item> itemDataModel;
    private NavigationController<Item> navigationController;
    // private Set<String> itemCats;
    // private Set<String> itemUoms;
    private final Set<Item> dmlRecords = new HashSet<>();

    // private Item selected;
    // private Set<Long> updatedItemCodes = new HashSet<>();

    private static final Logger LOG = LogManager.getLogger(ItemController.class);


    public ItemController() {

    }

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //     @Deferred
    // @RequestAction
    // @IgnorePostback
    @PostConstruct
    public void loadData() {
        itemOperations("INIT");
        // items = itemService.getItems();
        // // itemDataModel = new ListDataModel<Item>();
        // // itemDataModel.setWrappedData(items);
        // // this.updateDataModel(items);
        // navigationController = new NavigationController<Item>(items);
        // selected = navigationController.first();
    }

    private void itemOperations(String operation) {
        Item item = null;
        if (!operation.equalsIgnoreCase("INIT")) {
            item = navigationController.object;
        }
        int indx;
        if (operation.equalsIgnoreCase("DELETE")) {
            indx = items.indexOf(item);
            boolean removed = true;
            if (itemService.exists(item.getItemCode())) {
                try {
                    itemService.delete(item);
                } catch (DataIntegrityViolationException e) {
                    JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
                    removed = false;
                }
            }
            if (!removed) {
                return;
            }
            items.remove(indx);
            LOG.log(Level.INFO, "items.remove(indx) executed");
            if ((indx - 1) >= 0) {
                navigationController.go(indx - 1);
            }
        } else if (operation.equalsIgnoreCase("INIT") || operation.equalsIgnoreCase("UNDO")) {
            items = itemService.getItems();
            navigationController = new NavigationController<>(items, 0);
            navigationController.first();
            dmlRecords.clear();
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public Set<Item> getDmlRecords() {
        return dmlRecords;
    }

    public NavigationController<Item> getNavigationController() {
        return navigationController;
    }

    public Set<String> getItemCats() {
        return itemService.getItemCategories();
    }

    public Set<String> getItemUoms() {
        return itemService.getItemUOM();
    }

    public void prepareCreate() {
        Item item = new Item();
//        item.setItemDesc("Desc");
//        item.setItemUom("Item");
//        item.setEffectiveStartDate(new Date());
        item.setItemCode(itemService.generateID() >= (items.size() + 1) ? itemService.generateID() : (items.size() + 1));
        items.add(item);
        navigationController = new NavigationController<>(items, items.indexOf(item));
        dmlRecords.add(item);
    }

    public void onValueChanged(ValueChangeEvent vce) {
        LOG.info("*************** onValueChanged Start ***************");
        LOG.info("Component: " + vce.getComponent().getClientId());
        LOG.info("Old Value: " + vce.getOldValue());
        LOG.info("New Value: " + vce.getNewValue());
        // Long itemCode = navigationController.getList().getRowData().getItemCode();
        // logger.info("Item Code: " + itemCode);
//        String[] compareItem = {null};
//        if (vce.getComponent().getClientId().equalsIgnoreCase("ItemForm:itemDesc")) {
//            LOG.info("Item Desc. Component");
//            compareItem[0] = String.valueOf(vce.getOldValue());
//        } else {
//            LOG.info("Other Component");
//            compareItem[0] = navigationController.object.getItemDesc();
//        }
//        LOG.log(Level.INFO, "vce dmlRecords before: {0}", dmlRecords.size());
//        dmlRecords.forEach(item -> {
//            LOG.log(Level.INFO, "Component comparison: {0}", compareItem[0].equalsIgnoreCase(item.getItemDesc()));
//            if (compareItem[0].equalsIgnoreCase(item.getItemDesc())) {
//                LOG.log(Level.INFO, "Item to remove: {0}", item.toString());
//                dmlRecords.remove(item);
//            }
//        });
//        LOG.log(Level.INFO, "vce dmlRecords after: {0}", dmlRecords.size());
        dmlRecords.add(navigationController.object);
        // updatedItemCodes.add(itemCode);
        LOG.info("*************** onValueChanged End ***************");
    }

    public void onValueChanged(AjaxBehaviorEvent event) {
        // logger.info("Ajax Value: " +
        // event.getComponent().getAttributes().get("startDate"));
        // Long itemCode = navigationController.getList().getRowData().getItemCode();
        // logger.info("Item Code: " + itemCode);
        dmlRecords.add(navigationController.object);
        // updatedItemCodes.add(itemCode);
    }

    public void destroy() {
        itemOperations("DELETE");
    }

    public void save() {
        LOG.info("*************** ItemController Save Start ***************");
        if (dmlRecords.size() > 0) {
//            dmlRecords.forEach(rec -> {
//                LOG.log(Level.INFO, "Hash Code: {0}", rec.hashCode());
//                LOG.log(Level.INFO, "Item: {0}", rec.toString());
//                try {
//                    itemService.save(rec);
//                    dmlRecords.clear();
//                    JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Save Successful", "Item(s) saved successfully");
//                } catch (DataIntegrityViolationException e) {
//                    JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
//                }
//            });
            try {
                itemService.save(dmlRecords);
                dmlRecords.clear();
                JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Save Successful", "Item(s) saved successfully");
            } catch (DataIntegrityViolationException e) {
                JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
            } catch (Exception e) {
                JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
            }
        }
        LOG.info("*************** ItemController Save End   ***************");
    }

    public void undoChanges() {
        itemOperations("UNDO");
    }
}
