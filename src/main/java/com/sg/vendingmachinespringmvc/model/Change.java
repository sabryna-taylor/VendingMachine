/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.model;

import java.math.BigDecimal;

/**
 *
 * @author Sabryna
 */
public class Change {

    private BigDecimal money;
    private int numberOfQuarters;
    private int numberOfDimes;
    private int numberOfNickels;
    private int numberOfPennies;

    public Change(BigDecimal money) {

        double convertToDouble = money.doubleValue();

        convertToDouble *= 100;

        int change = (int) convertToDouble;

        int numberOfQuarters = change / 25;
        if (numberOfQuarters > 0) {
            change = change % 25;
        }

        int numberOfDimes = change / 10;
        if (numberOfDimes > 0) {
            change = change % 10;
        }

        int numberOfNickels = change / 5;
        if (numberOfNickels > 0) {
            change = change % 5;
        }

        int numberOfPennies = change;

        this.numberOfQuarters = numberOfQuarters;
        this.numberOfDimes = numberOfDimes;
        this.numberOfNickels = numberOfNickels;
        this.numberOfPennies = numberOfPennies;
    }

    public String toString() {
        String changeDue = "";
        int quarters = getNumberOfQuarters();
        int dimes = getNumberOfDimes();
        int nickels = getNumberOfNickels();
        int pennies = getNumberOfPennies();
        if (quarters != 0) {
            changeDue += " Quarters " + quarters;
        }
        if (dimes != 0) {
            changeDue += " Dimes " + dimes;
        }
        if (nickels != 0) {
            changeDue += " Nickels " + nickels;
        }
        if (pennies != 0) {
            changeDue += " Pennies " + pennies;
        }
        if (changeDue.equals("")) {
            changeDue += "NO CHANGE";
        }
        return changeDue;
    }

    public int getNumberOfQuarters() {
        return numberOfQuarters;
    }

    public int getNumberOfDimes() {
        return numberOfDimes;
    }

    public int getNumberOfNickels() {
        return numberOfNickels;
    }

    public int getNumberOfPennies() {
        return numberOfPennies;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
