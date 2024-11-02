package org.cup.assets.objects;

import java.awt.Color;

import javax.swing.JLabel;

import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;

/* 
 * Graphical part of the inventory
*/
public class Inventory extends GameNode {
    private Rectangle inventoryUI;

    private int buffer;
    private int maxSize;

    private JLabel packageCount = new JLabel("0/?", JLabel.CENTER);

    public Inventory(int initialMaxSize) {
        buffer = 0;
        maxSize = initialMaxSize;
    }

    public void init() {
        int x = 120 - 1;
        int y = 720 - 20 - 175;
        int w = 400;
        int h = 175;
        inventoryUI = new Rectangle(w, h, x, y, 1, new Color(19, 21, 21));
        inventoryUI.transform.setParentTransform(transform);
        addChild(inventoryUI);

        // UI
        packageCount.setForeground(Color.WHITE);
        packageCount.setBounds(x, y, w, h);
        packageCount.setHorizontalAlignment(JLabel.CENTER);
        packageCount.setVerticalAlignment(JLabel.CENTER);
        GameManager.game.addUIElement(packageCount);
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
        packageCount.setText(buffer + "/" + maxSize);
        notifyAll();
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
        packageCount.setText(buffer + "/" + maxSize);
        notifyAll();
    }

    public int getResourceCount(){
        return buffer;
    }

    public int getCapacity(){
        return maxSize;
    }
}