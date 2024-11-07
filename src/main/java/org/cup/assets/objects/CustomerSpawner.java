package org.cup.assets.objects;

import java.util.LinkedList;
import java.util.Queue;

import org.cup.assets.scenes.MainScene;
import org.cup.engine.core.nodes.GameNode;

public class CustomerSpawner extends GameNode {
    private double interval = 4000;
    private double lastSpawnTimestamp;

    private Queue<Customer> pool;

    public CustomerSpawner() {
        pool = new LinkedList<Customer>();
    }

    @Override
    public void onEnable() {
        lastSpawnTimestamp = System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {
        if (System.currentTimeMillis() - lastSpawnTimestamp >= interval) {
            spawnFromPool();
            lastSpawnTimestamp = System.currentTimeMillis(); // Reset Spawn Timer
        }
    }

    /**
     * Spawns a customer from the pool if available, or creates a new customer if the pool is empty.
     * Adds the customer as a child node and links it to the scrollable transform of the main scene.
     */
    private void spawnFromPool() {
        Customer toSpawn = pool.poll();

        if (toSpawn == null) {
            toSpawn = new Customer();
            addChild(toSpawn);
            MainScene.addToScrollTransform(toSpawn.transform);
            return;
        }

        toSpawn.enable();

    }

    /**
     * Returns a customer to the pool for reuse, helping reduce object creation overhead.
     *
     * @param c The customer to be added back to the pool.
     */
    public void addBackToQueue(Customer c) {
        pool.add(c);
    }
}
