package org.cup.assets.logic;

import org.cup.assets.objects.Inventory;
import org.cup.assets.objects.Customer;
import org.cup.engine.core.Debug;

public class CustomerThread extends Thread {
    private Inventory inventory;
    private Customer customer;
    private int packagesToTake; 

    public CustomerThread(Customer customer, Inventory inventory){
        this.inventory = inventory;
        this.customer = customer;
    }

    @Override
    public synchronized void start() {
        Debug.engineLogErr("DO NOT START THIS THREAD DIRECTLY, USE takePackage(int n)");
        super.start();
    }

    public void takePackage(int n){
        packagesToTake = n;
        super.start();
    }

    @Override
    public void run() {
        inventory.takeResource(packagesToTake);
        customer.goAway();
    }

}