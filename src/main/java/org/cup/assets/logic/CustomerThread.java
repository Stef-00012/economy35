package org.cup.assets.logic;

import org.cup.assets.objects.Inventory;
import org.cup.assets.objects.Customer;
import org.cup.engine.core.Debug;

public class CustomerThread extends Thread {
    private Inventory inventory;
    private Customer customer;
    private int packagesToTake;
    private boolean isRunning;

    public CustomerThread(Customer customer, Inventory inventory) {
        this.inventory = inventory;
        this.customer = customer;
        isRunning = false;
    }

    @Override
    public synchronized void start() {
        Debug.engineLogErr("DO NOT START THIS THREAD DIRECTLY, USE takePackage(int n)");
        super.start();
    }

    public void takePackage(int n) {
        packagesToTake = n;

        if(!isRunning){
            super.start();
            isRunning = true;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (packagesToTake != 0) {
                inventory.takeResource(packagesToTake);
                packagesToTake = 0;
                customer.goAway();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}