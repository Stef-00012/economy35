package org.cup.assets.objects;

import java.util.LinkedList;
import java.util.Queue;

import org.cup.engine.core.nodes.GameNode;

public class CustomerSpawner extends GameNode {
    private double interval = 1000;
    private double lastSpawnTimestamp;

    private Queue<Customer> pool;

    public CustomerSpawner(){
        pool = new LinkedList<Customer>();
    }

    @Override
    public void onEnable() {
        lastSpawnTimestamp = System.currentTimeMillis();
    }
    @Override
    public void onUpdate() {
        if(System.currentTimeMillis() - lastSpawnTimestamp >= interval){
            spawnFromPool();
            lastSpawnTimestamp = System.currentTimeMillis();
        }
    }

    private void spawnFromPool(){
        Customer toSpawn = pool.poll();

        if(toSpawn == null){
            toSpawn = new Customer();
            addChild(toSpawn);
            return;
        }

        toSpawn.enable();

    }

    public void addBackToQueue(Customer c){
        pool.add(c);
    }
}
