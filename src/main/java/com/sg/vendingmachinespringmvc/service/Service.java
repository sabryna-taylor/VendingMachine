/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.controller.InsufficentFundsException;
import com.sg.vendingmachinespringmvc.controller.SoldOutException;
import com.sg.vendingmachinespringmvc.dao.PersistenceException;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Sabryna
 */
public interface Service {

    BigDecimal addMoneyToMachine(BigDecimal depositMoney);

    BigDecimal displayMoney();

    long getItemID(long itemID);

    long displayID();

    List<Item> getAllItems() throws PersistenceException;

    boolean checkInStock() throws SoldOutException;

    boolean checkFunds(boolean valid) throws InsufficentFundsException;

    Item completePurchase(boolean valid) throws PersistenceException;

    String displayMessages();

    void returnChange(boolean valid);

    String displayChange(boolean setChange);

    String getChangeMessage();

}
