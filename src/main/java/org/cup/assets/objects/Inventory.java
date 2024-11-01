package org.cup.assets.objects;

import java.awt.Color;

import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;

/* 
 * Graphical part of the inventory
*/
public class Inventory extends GameNode {
    private Rectangle inventoryUI;

    private int buffer;
    private int maxSize;

    public Inventory(int initialMaxSize) {
        buffer = 0;
        maxSize = initialMaxSize;
    }

    public void init() {
        inventoryUI = new Rectangle(400, 175, 120 - 1, 720 - 20 - 175, 1, new Color(19, 21, 21));
        inventoryUI.transform.setParentTransform(transform);
        addChild(inventoryUI);

    }

    public synchronized void addResource() {
        while (buffer >= maxSize) {
            try {
                wait();
            } catch (Exception e) {
                Debug.engineLogErr(e.getMessage());
            }
        }
        buffer++;
    }

    public synchronized void takeResource(int n) {
        while (buffer < n) {
            try {
                wait();
            } catch (Exception e) {
                Debug.engineLogErr(e.getMessage());
            }
        }
        buffer -= n;
        notifyAll();
    }
}