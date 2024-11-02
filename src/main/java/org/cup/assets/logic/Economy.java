package org.cup.assets.logic;

import org.cup.assets.scenes.MainScene;

public class Economy {
    private static double balance;
    private static double productValue;

    public static double getBalance() {
        return balance;
    }
    public static void setBalance(double balance) {
        Economy.balance = balance;
        MainScene.getStatsPanel().setBalanceLabel(balance);
    }

    public static double getProductValue() {
        return productValue;
    }
    public static void setProductValue(double productValue) {
        Economy.productValue = productValue;
        MainScene.getStatsPanel().setProductValueLabel(productValue);
    }
}
