/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.controller.InsufficentFundsException;
import com.sg.vendingmachinespringmvc.controller.SoldOutException;
import com.sg.vendingmachinespringmvc.dao.ItemDao;
import com.sg.vendingmachinespringmvc.dao.PersistenceException;
import com.sg.vendingmachinespringmvc.model.Change;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public class ServiceImpl implements Service {

    ItemDao dao;
    BigDecimal funds = new BigDecimal("0");
    Item item;
    long itemID;
    String message = "";
    String change = "";

    public ServiceImpl(ItemDao dao) {
        this.dao = dao;
    }

    @Override
    public BigDecimal addMoneyToMachine(BigDecimal funds) {
        this.funds = funds;
        return funds;
    }

    @Override
    public BigDecimal displayMoney() {
        return funds;
    }

    @Override
    public long getItemID(long itemID) {
        item = dao.getItem(itemID);
        this.itemID = itemID;
        return itemID;
    }

    @Override
    public long displayID() {
        return itemID;
    }

    @Override
    public List<Item> getAllItems() throws PersistenceException {
        return new ArrayList(dao.getAllItems());
    }

    @Override
    public boolean checkInStock() throws SoldOutException {
        //check if item is available
        boolean valid = true;
        if (item.getQuantity() < 1) {
            valid = false;
            message = "SOLD OUT!!";
            throw new SoldOutException("SOLD OUT!!!");
        }
        return valid;
    }

    @Override
    public boolean checkFunds(boolean valid) throws InsufficentFundsException {
        valid = true;
        if (funds.compareTo(item.getCost()) < 0) {
            //subtract(funds);
            BigDecimal cost = item.getCost();
            BigDecimal m = cost.subtract(funds);
            message = "Please deposit: " + m;
            valid = false;
            throw new InsufficentFundsException(message);
        }
        return valid;
    }

    @Override
    public Item completePurchase(boolean valid) throws PersistenceException {
        if (valid == true) {
            if (funds != BigDecimal.ZERO) {
                funds = funds.subtract(item.getCost());
                dao.updateItemQuantity(item, -1);
                itemID -= itemID;
                message = "THANK YOU!";
            }
        }
        return item;
    }

    @Override
    public String displayMessages() {
        return message;
    }

    @Override
    public String getChangeMessage() {
        return change;
    }

    @Override
    public void returnChange(boolean valid) {
        if (valid == true) {
            Change c = new Change(funds);
            change = c.toString();
            if (funds.compareTo(BigDecimal.ZERO) > 0) {
                funds = funds.subtract(funds);

            }
            itemID -= itemID;
        }
    }

    @Override
    public String displayChange(boolean setChange) {
        if (setChange == true) {
            Change c = new Change(funds);
            change = c.toString();

        }
        return change;
    }

}
