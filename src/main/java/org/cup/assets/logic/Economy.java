package org.cup.assets.logic;

import org.cup.assets.scenes.MainScene;

/**
 * This class manages the in-game economy, including the player's balance and the value of the product.
 * It provides methods to get and set the balance, the product value, and to spend money.
 */
public class Economy {
    private static double balance; 
    private static double productValue; 

    /**
     * Gets the current balance of the player.
     * 
     * @return The current balance.
     */
    public static double getBalance() {
        return balance;
    }

    /**
     * Sets the balance and updates the balance label in the main scene.
     * 
     * @param balance The new balance to be set.
     */
    public static void setBalance(double balance) {
        Economy.balance = balance;
        // Update the balance label in the UI
        MainScene.getStatsPanel().setBalanceLabel(balance);
    }

    /**
     * Gets the current value of the product.
     * 
     * @return The value of the product.
     */
    public static double getProductValue() {
        return productValue;
    }

    /**
     * Sets the value of the product and updates the product value label in the main scene.
     * 
     * @param productValue The new product value to be set.
     */
    public static void setProductValue(double productValue) {
        Economy.productValue = productValue;
        // Update the product value label in the UI
        MainScene.getStatsPanel().setProductValueLabel(productValue);
    }

    /**
     * Spends a specified amount of money and updates the balance.
     * 
     * @param amount The amount of money to be spent.
     */
    public static void spendMoney(double amount) {
        setBalance(balance - amount);
    }
}
