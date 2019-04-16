package org.zahid.apps.web.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zahid.apps.web.pos.controller.SecurityController;
import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.repo.ItemRepo;
import org.zahid.apps.web.pos.service.ItemService;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;
    private final Logger LOG = Logger.getLogger(ItemServiceImpl.class.getName());

    private Sort orderBy(String column) {
        return new Sort(Sort.Direction.ASC, column);
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
        items.forEach(item -> updateWhoColumns(item));
        return itemRepo.saveAll(items);
    }

    @Override
    public void delete(Item item) {
        itemRepo.delete(item);
    }

    @Override
    public void delete(Set<Item> items) {
        itemRepo.deleteAll(items);
    }

    @Override
    public void deleteById(Long id) {
        itemRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        itemRepo.deleteAll();
    }

    @Override
    public void deleteAllInBatch() {
        itemRepo.deleteAllInBatch();
    }

    @Override
    public void deleteInBatch(Set<Item> items) {
        itemRepo.deleteInBatch(items);
    }

    @Override
    public Set<String> getItemCategories() {
        Set<String> cats = new HashSet<>();
        for (Item item : itemRepo.findAll()) {
            cats.add(item.getItemCategory());
        }
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
    }
}