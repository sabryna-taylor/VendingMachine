/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.model.Item;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Sabryna
 */
public class ItemDaoImpl implements ItemDao {

    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";

    private Map<Long, Item> itemMap = new HashMap<>();

    //assign id number
    private static long itemIDCounter = 0;

    @Override
    public Item addItem(Item item) {
        item.setItemID(itemIDCounter);
        itemIDCounter++;
        itemMap.put(item.getItemID(), item);
        return item;
    }

    @Override
    public void removeItem(long itemID) {
        itemMap.remove(itemID);
    }

    @Override
    public Item updateItemQuantity(Item item, int quantity) throws PersistenceException {
        int currentQuantity = item.getQuantity() - 1;
        item.setQuantity(currentQuantity);
        Item updatedQuantity = itemMap.put(item.getItemID(), item);
        writeFile();
        return updatedQuantity;
    }

    @Override
    public List<Item> getAllItems() throws PersistenceException {
        loadFile();
        Collection<Item> i = itemMap.values();
        return new ArrayList(i);
    }

    @Override
    public Item getItem(long itemID) {
        return itemMap.get(itemID);
    }

    public void loadFile() throws PersistenceException {
        Scanner sc;

        try {
            //create scanner for reading the file
            sc = new Scanner(new BufferedReader(new FileReader(new ClassPathResource(INVENTORY_FILE).getFile())));
        } catch (IOException e) {
            throw new PersistenceException("-___- "
                    + "Could not load item data into inventory memory.", e);
        }
        while (sc.hasNextLine()) {
            //get the next line in the file
            String currentLine = sc.nextLine();
            String[] currentToken = currentLine.split(DELIMITER);

            Item currentItem = new Item();
            currentItem.setItemID(Long.parseLong(currentToken[0]));
            currentItem.setItemName(currentToken[1]);
            currentItem.setCost(new BigDecimal(currentToken[2]));
            currentItem.setQuantity(Integer.parseInt(currentToken[3]));

            itemMap.put(currentItem.getItemID(), currentItem);
        }

        sc.close();
    }

    private void writeFile() throws PersistenceException {

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(new ClassPathResource(INVENTORY_FILE).getFile()));
        } catch (IOException e) {
            throw new PersistenceException("Could not save "
                    + "inventory data.", e);
        }

        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            out.println(currentItem.getItemID() + DELIMITER
                    + currentItem.getItemName() + DELIMITER
                    + currentItem.getCost() + DELIMITER
                    + currentItem.getQuantity());
            out.flush();
        }
        out.close();
    }

}
/*

 */
