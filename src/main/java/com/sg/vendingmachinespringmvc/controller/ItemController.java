/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.PersistenceException;
import com.sg.vendingmachinespringmvc.model.Item;
import com.sg.vendingmachinespringmvc.service.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Sabryna
 */
@Controller
public class ItemController {

    Service service;

    @Inject
    public ItemController(Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayAllItems(Model model) throws PersistenceException {
        List<Item> itemList = service.getAllItems();


        String stringFunds = " ";
        String stringID = " ";
        String blank = " ";
        String change = "";

        String message = service.displayMessages();
        stringFunds += service.displayMoney();
        stringID += service.displayID();

        if (service.displayID() == 0) {
            model.addAttribute("id", blank);
        } else {
            model.addAttribute("id", stringID);
        }

        if (message.contains(blank)) {
            model.addAttribute("message", message);
        } else {
            message = blank;
        }

        change = service.getChangeMessage();
        model.addAttribute("change", change);

        //model.addAttribute("change", change);
        model.addAttribute("itemList", itemList);
        model.addAttribute("funds", stringFunds);

        return "index";
    }

    @RequestMapping(value = "/selectItem", method = RequestMethod.GET)
    public String selectItem(HttpServletRequest request) {
        String itemIdParameter = request.getParameter("itemID");
        long itemId = Long.parseLong(itemIdParameter);
        service.getItemID(itemId);
        return "redirect:/";
    }

    @RequestMapping(value = "/makePurchase", method = RequestMethod.GET)
    public String makePurchase(HttpServletRequest request, Model model)
            throws PersistenceException {
        try {
            boolean isValid = service.checkInStock();
            boolean valid = service.checkFunds(isValid);
            service.completePurchase(valid);

            //display change
            service.displayChange(valid);
            service.returnChange(valid);

        } catch (SoldOutException | InsufficentFundsException e) {
            // message += e.getMessage();

        }
        return "redirect:/";
    }

    @RequestMapping(value = "/addDollar", method = RequestMethod.POST)
    public String addDollar(Map<String, Object> model
    ) {
        BigDecimal current = addMoney(BigDecimal.valueOf(1));
        service.addMoneyToMachine(current);
        model.put("currentMoneyInput", current);
        return "redirect:/";
    }

    @RequestMapping(value = "/addQuarter", method = RequestMethod.POST)
    public String addQuarter(Map<String, Object> model
    ) {
        BigDecimal current = addMoney(BigDecimal.valueOf(0.25));
        service.addMoneyToMachine(current);
        model.put("currentMoneyInput", current);
        return "redirect:/";
    }

    @RequestMapping(value = "/addDime", method = RequestMethod.POST)
    public String addDime(Map<String, Object> model
    ) {
        BigDecimal current = addMoney(BigDecimal.valueOf(0.10));
        service.addMoneyToMachine(current);
        model.put("currentMoneyInput", current);

        return "redirect:/";
    }

    @RequestMapping(value = "/addNickel", method = RequestMethod.POST)
    public String addNickel(Map<String, Object> model
    ) {
        BigDecimal current = addMoney(BigDecimal.valueOf(0.05));
        service.addMoneyToMachine(current);
        model.put("currentMoneyInput", current);

        return "redirect:/";
    }

    public BigDecimal addMoney(BigDecimal coins) {
        BigDecimal funds = service.displayMoney();
        funds = service.addMoneyToMachine(funds.add(coins));
        return funds;
    }

    @RequestMapping(value = "/returnChange", method = RequestMethod.POST)
    public String returnChange(Model model) {
        boolean valid = true;
        service.returnChange(valid);

        return "redirect:/";
    }

}
