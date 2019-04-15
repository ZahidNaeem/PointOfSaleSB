package org.zahid.apps.web.pos.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;
    private final Logger logger = Logger.getLogger(ItemServiceImpl.class.getName());

    private Sort orderBy(String column) {
        return new Sort(Sort.Direction.ASC, column);
    }

    @Override
    public boolean exists(Long id) {
        return itemRepo.existsById(id);
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = itemRepo.findAll(orderBy("itemCode"));
        return items;
    }

    @Override
    public Item findById(Long id) {
        Item item = itemRepo.getOne(id);
        return item;
    }

    @Override
    public Item save(Item item) {
        String user = (new SecurityController()).getUsername();
        logger.info("User: " + user);
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        if (item.getItemCode() == null || !itemRepo.existsById(item.getItemCode())) {
            item.setCreatedBy(user);
            item.setCreationDate(currTime);
        }
        item.setLastUpdatedBy(user);
        item.setLastUpdateDate(currTime);
        return itemRepo.save(item);
    }

    @Override
    public List<Item> save(Set<Item> items) {
        items.forEach(item -> {
            String user = (new SecurityController()).getUsername();
            Timestamp currTime = new Timestamp(System.currentTimeMillis());
            if (item.getItemCode() == null || !itemRepo.existsById(item.getItemCode())) {
                item.setCreatedBy(user);
                item.setCreationDate(currTime);
            }
            item.setLastUpdatedBy(user);
            item.setLastUpdateDate(currTime);
            itemRepo.saveAndFlush(item);
        });
        return (List<Item>) items;
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
        Set<String> cats = new HashSet<String>();
        for (Item item : itemRepo.findAll()) {
            cats.add(item.getItemCategory());
        }
        return cats;
    }

    @Override
    public Set<String> getItemUOM() {
        Set<String> uoms = new HashSet<String>();
        for (Item item : itemRepo.findAll()) {
            uoms.add(item.getItemUom());
        }
        return uoms;
    }

}
