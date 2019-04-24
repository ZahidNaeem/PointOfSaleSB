package org.zahid.apps.web.pos.controller;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.entity.ItemStock;
import org.zahid.apps.web.pos.service.ItemStockService;
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
@Named("itemStockController")
//@Named("itemStockController")
//@SessionScoped
// @ELBeanName(value = "itemController")
// @Join(path = "/items", to = "/views/item/itemList.jsf")
public class ItemStockController implements Serializable {

    private ItemStockService itemStockService;
    //    @Autowired
//    private ItemController itemController;
//    private Item item;
    private List<ItemStock> stockList;
    private ListDataModel<ItemStock> stockDataModel;
    private ItemStock selected;
    private final Set<ItemStock> dmlRecords = new HashSet<>();
    //    private boolean dataFromDBFlag = false;
    private static final Logger LOG = Logger.getLogger(ItemStockController.class.getName());

    @PostConstruct
    public void loadData() {
        stockList = itemStockService.getCurrentItemStockList();
//        item = itemController.getNavigationController().object;
//        item.getItemStocks().size();
//        stockList = item.getItemStocks();
        stockDataModel = new ListDataModel<>(stockList);
        // itemStocks = itemStockService.getItemStockList();
        // stockDataModel.setWrappedData(itemStocks);
    }

    public ItemStockController() {

    }

    @Autowired
    public ItemStockController(ItemStockService itemStockService) {
        this.itemStockService = itemStockService;
    }

    public List<ItemStock> getStockList() {
        return stockList;
    }

    public ListDataModel<ItemStock> getStockDataModel() {
        return stockDataModel;
    }

    public List<ItemStock> updateStockList() {
        stockList = itemStockService.getCurrentItemStockList();
//        if (dataFromDBFlag) {
//            stockList = itemStockService.getCurrentItemStockListFromDB();
//            dataFromDBFlag = false;
//        } else {
//            stockList = itemStockService.getCurrentItemStockList();
//        }
        return stockList;
    }

    //    org.apache.commons.beanutils.PropertyUtilsBean.copyProperties();
    public void updateStockDataModel() {
        System.out.println("updateStockDataModel called");
        stockDataModel.setWrappedData(updateStockList());
//        if (dataFromDBFlag) {
//            dataFromDBFlag = false;
//        }
    }

    public ItemStock getSelected() {
        return selected;
    }

    public void setSelected(ItemStock selected) {
        this.selected = selected;
    }

    public Set<ItemStock> getDmlRecords() {
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
//        logger.info("Changed Stock ID: " + selected.getItemStockId());
//    }
    public void onCellEdit(CellEditEvent event) {
        DataTable dataTable = (DataTable) event.getSource();
        selected = (ItemStock) dataTable.getRowData();
        dmlRecords.add(selected);
        LOG.log(Level.INFO, "onCellEdit Stock ID: {0}", selected.toString());
        LOG.log(Level.INFO, "onCellEdit New Value: {0}", event.getNewValue());
    }

    //    public void onDateSelect(SelectEvent event) {
//        logger.info("Date: " + event.getObject());
//        dmlRecords.add(selected);
//        logger.info("Changed Stock ID: " + selected.getItemStockId());
//    }
    // public List<ItemStock> addItemStock(ItemStock itemStock) {
    // itemController.getNavigationController().object.getItemStocks().add(itemStock);
    // stockList = itemController.getNavigationController().object.getItemStocks();
    // return stockList;
    // }
    public ItemStock prepareCreate() {
//        selected = stockList.get(0);
        int indx;
        ItemStock stock = new ItemStock();
        Item item = itemStockService.getCurrentItem();
        LOG.log(Level.INFO, "Item: {0}", item.toString());
        stock.setItem(item);
        if (stockList == null) {
            stockList = new ArrayList<>();
        }
        Long id = (itemStockService.generateID() >= (stockList.size() + 1) ? itemStockService.generateID() : (stockList.size() + 1));
        LOG.log(Level.INFO, "Stock ID: {0}", id);
        stock.setItemStockId(id);
//        stockList = itemStockService.addStockToStockList(stock);
        LOG.log(Level.INFO, "Stock before: {0}", stockList.size());
        stockList.add(stock);
        LOG.log(Level.INFO, "Stock after: {0}", stockList.size());
        indx = stockList.indexOf(stock);
        stockDataModel.setWrappedData(stockList);
        stockDataModel.setRowIndex(indx);
        selected = stockDataModel.getRowData();
        dmlRecords.add(selected);
        return selected;
    }

    public void prepareDelete() {
        selected = stockDataModel.getRowData();
    }

    public void destroy() {
        if (selected != null) {
            LOG.log(Level.INFO, "Stock ID: {0}", selected.toString());
            LOG.log(Level.INFO, "Selected Row: {0}", selected.getItemStockId());
            LOG.log(Level.INFO, "Stock before: {0}", stockList.size());
            if (itemStockService.exists(selected.getItemStockId())) {
                boolean removed = true;
                try {
                    itemStockService.delete(selected);
                } catch (DataIntegrityViolationException e) {
                    JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
                    removed = false;
                }
                if (!removed) {
                    return;
                }
            }
            stockList.remove(selected);
            LOG.log(Level.INFO, "Stock after: {0}", stockList.size());
        }
    }

    public void save() {
        if (dmlRecords.size() > 0) {
            try {
                List<ItemStock> stocks = itemStockService.save(dmlRecords);
//                itemStockService.attachStockWithItem(stocks);
                JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Item Stock(s) saved successfully");
                dmlRecords.clear();
            } catch (DataIntegrityViolationException e) {
                JsfUtils.showMessage(FacesMessage.SEVERITY_ERROR, "DB Error", Miscellaneous.convertDBError(e));
            }
        }
    }

    public void undoChanges() {
        LOG.log(Level.INFO, "Changed Stocks: {0}", dmlRecords.size());
        Set<Item> items = new HashSet<>();
        dmlRecords.forEach((stock) -> {
            items.add(stock.getItem());
        });
        items.forEach((item) -> {
            item.setItemStocks(itemStockService.getItemStockListFromDB(item));
        });
        dmlRecords.clear();
//        RequestContext.getCurrentInstance().getAjaxRequestBuilder().update()
//        PrimeFaces.current().ajax().update(ae.getComponent().getParent().getParent().getClientId());
//        logger.info("Client ID: " + ae.getComponent().getParent().getParent().getClientId());
        JsfUtils.showMessage(FacesMessage.SEVERITY_INFO, "Undo Changes Successful");
    }

}
