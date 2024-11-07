package org.cup.assets.logic;

import org.cup.assets.objects.Inventory;
import org.cup.assets.objects.Customer;
import org.cup.engine.core.Debug;

/**
 * A thread responsible for managing a customer's actions of taking packages
 * from an inventory.
 */
public class CustomerThread extends Thread {
    private Inventory inventory;
    private Customer customer;
    private int packagesToTake;
    private boolean isRunning;

    /**
     * Constructs a CustomerThread for a specific customer and inventory.
     * 
     * @param customer  The customer who will take packages.
     * @param inventory The inventory from which packages will be taken.
     */
    public CustomerThread(Customer customer, Inventory inventory) {
        this.inventory = inventory;
        this.customer = customer;
        isRunning = false;
    }

    /**
     * Starts the thread. Throws an error if called directly; use
     * {@link takePackage(int n)} to start.
     */
    @Override
    public synchronized void start() {
        Debug.engineLogErr("DO NOT START THIS THREAD DIRECTLY, USE takePackage(int n)");
        super.start();
    }

    /**
     * Initiates the process of taking a specific number of packages.
     * 
     * @param n The number of packages the customer will take.
     */
    public void takePackage(int n) {
        packagesToTake = n;

        if (!isRunning) {
            super.start();
            isRunning = true;
        }
    }

    /**
     * Runs the thread, where the customer takes packages from the inventory and
     * then leaves.
     * The thread repeatedly checks if there are packages to take, then performs the
     * action.
     */
    @Override
    public void run() {
        while (true) {
            if (packagesToTake != 0) {

                inventory.takeResource(packagesToTake); // Take the specified number of packages from the inventory
                packagesToTake = 0; // Reset the package count after taking them
                customer.goAway(); // Customer takes the packages and leaves
            }

            // Pause for 100 milliseconds to reduce CPU load
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}