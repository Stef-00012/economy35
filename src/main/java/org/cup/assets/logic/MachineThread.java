package org.cup.assets.logic;

import java.util.Random;

import org.cup.assets.objects.Machine;

public class MachineThread extends Thread {
    // Probability of obtaining a resource upon the end of the timer
    private int probability = 50;

    // Time that needs to elapse before a resourse gets dropped
    private int timer = 3000;

    private Machine machine;
    
    private Random randomGen = new Random(); 

    public MachineThread(Machine machine) {
        this.machine = machine;
    }

    @Override
    public void run() {
        System.out.println("Thread initialized");
        while (true) {
            try {
                sleep(timer);
            } catch (Exception e) {
                System.err.println(e);
            }

            // drop the resoure randomly based on the value of the probability, the higher
            // it gets the more probability there is to get a resource
            if (randomGen.nextInt(100) <= probability) {
                machine.normal();
            }

            else {
                machine.error();
            }

        }
    }
}
