package org.zahid.apps.web.pos.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.controller.SecurityController;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.repo.ItemRepo;
import org.zahid.apps.web.pos.service.ItemService;
import org.zahid.apps.web.pos.utils.Miscellaneous;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepo itemRepo;
    private final Logger LOG = LogManager.getLogger(ItemServiceImpl.class.getName());

    public ItemServiceImpl() {

    }

    @Autowired
    public ItemServiceImpl(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    private Sort orderBy(String column) {
        return new Sort(Sort.Direction.ASC, column);
    }

    @Override
    public Long generateID() {
        return itemRepo.generateID();
    }

    @Override
    public boolean exists(Long id) {
        return itemRepo.existsById(id);
    }

    @Override
    public List<Item> getItems() {
        return itemRepo.findAll(orderBy("itemCode"));
    }

    @Override
    public Item findById(Long id) {
        return itemRepo.getOne(id);
    }

    @Override
    public Item save(Item item) throws DataIntegrityViolationException {
        updateWhoColumns(item);
        return itemRepo.save(item);
    }

    @Override
    public List<Item> save(Set<Item> items) throws DataIntegrityViolationException {

        items.forEach(item -> {
            updateWhoColumns(item);
        });
        List<Item> returnItems = itemRepo.saveAll(items);
        return returnItems;
    }

    @Override
    public void delete(Item item) throws DataIntegrityViolationException {
        itemRepo.delete(item);
    }

    @Override
    public void delete(Set<Item> items) throws DataIntegrityViolationException {
        itemRepo.deleteAll(items);
    }

    @Override
    public void deleteById(Long id) throws DataIntegrityViolationException {
        itemRepo.deleteById(id);
    }

    @Override
    public void deleteAll() throws DataIntegrityViolationException {
        itemRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() throws DataIntegrityViolationException {
        itemRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<Item> items) throws DataIntegrityViolationException {
        itemRepo.deleteInBatch(items);
    }

    @Override
    public Set<String> getItemCategories() {
        Set<String> cats = new HashSet<>();
        cats.addAll(itemRepo.getCategories());
//        for (Item item : itemRepo.findAll()) {
//            cats.add(item.getItemCategory());
//        }
        return cats;
    }

    @Override
    public Set<String> getItemUOM() {
        Set<String> uoms = new HashSet<>();
        for (Item item : itemRepo.findAll()) {
            uoms.add(item.getItemUom());
        }
        return uoms;
    }

    private void updateWhoColumns(Item item) {
        String user = (new SecurityController()).getUsername();
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        if (item.getItemCode() == null || !itemRepo.existsById(item.getItemCode())) {
            item.setCreatedBy(user);
            item.setCreationDate(currTime);
        }
        item.setLastUpdatedBy(user);
        item.setLastUpdateDate(currTime);
        item.getItemStocks().forEach(itemStock -> {
            int result = Miscellaneous.exists("XXIM_ITEM_STOCK", "ITEM_STOCK_ID", itemStock.getItemStockId());
            LOG.log(Level.INFO, "Record: " + itemStock.getItemStockId());
            LOG.log(Level.INFO, "Result: " + result);
            if (result < 1) {
                itemStock.setCreatedBy(user);
                itemStock.setCreationDate(currTime);
            }
            itemStock.setLastUpdatedBy(user);
            itemStock.setLastUpdateDate(currTime);
        });
    }
}
