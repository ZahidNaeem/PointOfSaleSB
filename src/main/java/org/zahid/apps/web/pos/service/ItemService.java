package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Item;

import java.util.List;
import java.util.Set;

public interface ItemService {

    public Long generateID();

    public boolean exists(Long id);

    public Item findById(Long id);

    public List<Item> getItems();

    public Set<String> getItemCategories();

    public Set<String> getItemUOM();

    public Item save(Item item);

    public List<Item> save(Set<Item> items);

    public void delete(Item item);

    public void delete(Set<Item> items);

    public void deleteById(Long id);

    public void deleteAll();

    public void deleteAllInBatch();

    public void deleteInBatch(Set<Item> items);
}
