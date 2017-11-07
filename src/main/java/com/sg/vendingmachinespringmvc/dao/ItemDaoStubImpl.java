/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public class ItemDaoStubImpl implements ItemDao {

    Item onlyItem;
    Item item2;
    List<Item> itemList = new ArrayList<>();

    public ItemDaoStubImpl() {
        onlyItem = new Item();
        onlyItem.setItemName("Snickers");
        onlyItem.setItemID(1);
        onlyItem.setCost(BigDecimal.ONE);
        onlyItem.setQuantity(0);

        itemList.add(onlyItem);
    }

    @Override
    public Item addItem(Item item) {
        if (item.equals(onlyItem.getItemID())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public void removeItem(long itemID) {
        itemID = onlyItem.getItemID();
    }

    @Override
    public List<Item> getAllItems() throws PersistenceException {
        return itemList;
    }

    @Override
    public Item getItem(long itemID) {
        if (itemID == onlyItem.getItemID()) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Item updateItemQuantity(Item item, int quantity) throws PersistenceException {
        if (item.getItemID() == onlyItem.getItemID()) {
            onlyItem.setQuantity(onlyItem.getQuantity() - 1);
            return onlyItem;
        } else {
            return null;
        }
    }
}
