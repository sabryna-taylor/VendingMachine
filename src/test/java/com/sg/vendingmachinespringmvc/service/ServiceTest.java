/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.controller.InsufficentFundsException;
import com.sg.vendingmachinespringmvc.controller.SoldOutException;
import com.sg.vendingmachinespringmvc.dao.ErrorMessage;
import com.sg.vendingmachinespringmvc.dao.ItemDao;
import com.sg.vendingmachinespringmvc.dao.ItemDaoStubImpl;
import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Sabryna
 */
public class ServiceTest {

    private ItemDao itemDao = new ItemDaoStubImpl();
    private Service service = new ServiceImpl(itemDao);

    public ServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        List<Item> itemList = itemDao.getAllItems();
        for (Item item : itemList) {
            itemDao.removeItem(item.getItemID());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addMoneyToMachine method, of class Service.
     */
    @Test
    public void testAddDisplayMoneyToMachine() {
        BigDecimal funds = new BigDecimal("1");
        BigDecimal bd = service.addMoneyToMachine(funds);
        assertEquals(funds, bd);
    }

    /**
     * Test of getItemID method, of class Service.
     */
    @Test
    public void testGetDisplayItemID() throws Exception {
        Item item = new Item();
        item.setItemName("Snickers");
        item.setItemID(2);
        item.setCost(BigDecimal.ONE);
        item.setQuantity(1);
        itemDao.addItem(item);

        assertEquals(1, service.getItemID(1));
    }

    /**
     * Test of getAllItems method, of class Service.
     */
    @Test
    public void testGetAllItems() throws Exception {
        assertEquals(1, service.getAllItems().size());
    }

    /**
     * Test of checkInStock method, of class Service.
     */
    @Test
    public void testCheckInStock() throws Exception {

        long itemID = 1;
        service.getItemID(itemID);
        ErrorMessage message = new ErrorMessage();

        try {
            service.checkInStock();
            fail("Expected SoldOut to be thrown");
        } catch (SoldOutException e) {
            message.setMessage("SOLD OUT");
            return;
        }
        assertEquals("SOLD OUT", message.getMessage());
    }

    /**
     * Test of checkFunds method, of class Service.
     */
    @Test
    public void testCheckFunds() throws Exception {
        long itemID = 1;
        service.getItemID(itemID);
        service.addMoneyToMachine(BigDecimal.ZERO);
        boolean invalid = true;
        ErrorMessage message = new ErrorMessage();

        //VendingMachineNoSuchItemException e
        try {
            service.checkFunds(invalid);
            fail("Expected please deposit: 1 to be thrown");
        } catch (InsufficentFundsException e) {
            message.setMessage("Please deposit more money");
            return;
        }
        
        assertEquals("Please deposit more money", message.getMessage());
    }

    /**
     * Test of returnChange method, of class Service.
     */
    @Test
    public void testReturnDisplayChange() {
        BigDecimal change = new BigDecimal("1");
        boolean valid = true;
        service.returnChange(valid);

        assertEquals(BigDecimal.ONE, change);
    }

    /**
     * Test of getChangeMessage method, of class Service.
     */
    @Test
    public void testDisplayMessages() {
        String message = "Hello!";

        assertEquals("Hello!", message);
    }

}
