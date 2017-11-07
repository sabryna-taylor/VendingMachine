/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface ItemDao {

    //add item to data
    public Item addItem(Item item);

    //remove item
    public void removeItem(long itemID);

    //update item inventory
    public Item updateItemQuantity(Item item, int quantity) throws PersistenceException;

    //get all
    public List<Item> getAllItems() throws PersistenceException;

    //get by ID
    Item getItem(long itemID);
}
