/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sabryna
 */
public class ItemDaoTest {

    private ItemDao dao = new ItemDaoStubImpl();

    public ItemDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        List<Item> itemList = dao.getAllItems();
        for (Item item : itemList) {
            dao.removeItem(item.getItemID());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addItem method, of class ItemDao.
     */
    @Test
    public void testAddGetItem() {
        Item item = new Item();
        item.setItemName("Snickers");
        item.setItemID(1);
        item.setCost(BigDecimal.ONE);
        item.setQuantity(1);
        dao.addItem(item);

        Item fromDao = dao.getItem(item.getItemID());
        assertEquals(item.getItemID(), fromDao.getItemID());
    }

    /**
     * Test of removeItem method, of class ItemDao.
     */
    @Test
    public void testRemoveItem() throws Exception {
        Item item = new Item();
        item.setItemName("Snickers");
        item.setItemID(1);
        item.setCost(BigDecimal.ONE);
        item.setQuantity(1);

        dao.addItem(item);

        Item item2 = new Item();
        item2.setItemName("Snacks");
        item2.setItemID(2);
        item2.setCost(BigDecimal.ONE);
        item2.setQuantity(1);
        dao.addItem(item2);

        dao.removeItem(item.getItemID());
        assertEquals(1, dao.getAllItems().size());
    }

    /**
     * Test of updateItemQuantity method, of class ItemDao.
     */
    @Test
    public void testUpdateItemQuantity() throws Exception {
        long requestedItem = 1;
        Item item = dao.getItem(requestedItem);
        item.setQuantity(1);

        dao.updateItemQuantity(item, -1);

        assertEquals(0, item.getQuantity());
    }

    /**
     * Test of getAllItems method, of class ItemDao.
     */
    @Test
    public void testGetAllItems() throws Exception {
        Item items = new Item();
        items.setItemName("Snickers");
        items.setItemID(1);
        items.setCost(BigDecimal.ONE);
        items.setQuantity(2);
        dao.addItem(items);

        assertEquals(1, dao.getAllItems().size());
    }

}
