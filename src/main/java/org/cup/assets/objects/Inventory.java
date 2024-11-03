package org.cup.assets.objects;

import javax.swing.JPanel;

import org.cup.assets.UI.Floor;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SquareRenderer;

/* 
 * Graphical part of the inventory
*/
public class Inventory extends Floor {
    private SquareRenderer inventoryUI;

    private int buffer;
    private int maxSize;

    public Inventory(int initialMaxSize) {
        buffer = 0;
        maxSize = initialMaxSize;
        updateInventoryStatsUI();
    }

    public void init() {
        int w = 400;
        int h = 175;
        int y = 720 - 20 - 175 + h;
        int x = 120 - 1;
        // inventoryUI = new SquareRenderer(w, h, x, y, 1, new Color(19, 21, 21));
        inventoryUI = new SquareRenderer(transform, 1);
        transform.setScale(new Vector(w, h));
        transform.setPosition(new Vector(x, y));
        inventoryUI.setPivot(Renderer.BOTTOM_LEFT_PIVOT); // Use the top left pivot
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
        updateInventoryStatsUI();
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
        updateInventoryStatsUI();
        notifyAll();
    }

    public int getResourceCount() {
        return buffer;
    }

    public int getCapacity() {
        return maxSize;
    }

    private void updateInventoryStatsUI() {
        MainScene.getStatsPanel().setInventoryLabel(this);
    }

    @Override
    public JPanel getUI() {
        return new JPanel();
    }
}