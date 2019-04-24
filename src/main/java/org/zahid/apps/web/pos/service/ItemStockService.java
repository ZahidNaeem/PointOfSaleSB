package org.zahid.apps.web.pos.service;

import org.zahid.apps.web.pos.entity.Item;
import org.zahid.apps.web.pos.entity.ItemStock;

import java.util.List;
import java.util.Set;

public interface ItemStockService {

    public Long generateID();

    public boolean exists(Long id);

    public List<ItemStock> getItemStockList();

    public List<ItemStock> getCurrentItemStockList();

    public List<ItemStock> getCurrentItemStockListFromDB();

    public List<ItemStock> getItemStockListFromDB(Item item);

    public ItemStock getStockById(Long id);

    public List<ItemStock> addStockToStockList(ItemStock stock);

    public Item getCurrentItem();

    public ItemStock attachStockWithItem(ItemStock stock);

//    public List<ItemStock> attachStockWithItem(List<ItemStock> stocks);

//    public List<ItemStock> findAllByItem(Item item);

    // public ItemStock prepareCreate();

    public ItemStock save(ItemStock itemStock);

    public List<ItemStock> save(Set<ItemStock> itemStocks);

    public void delete(ItemStock itemStock);

    public void delete(Set<ItemStock> itemStocks);

    public void deleteById(Long id);

    public void deleteAll();

    public void deleteAllInBatch();

    public void deleteInBatch(Set<ItemStock> itemStocks);

}
