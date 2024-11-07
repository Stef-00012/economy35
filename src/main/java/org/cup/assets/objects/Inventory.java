package org.cup.assets.objects;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.Floor;
import org.cup.assets.UI.GameButton;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import org.cup.assets.logic.Economy;

/* 
 * Graphical part of the inventory
*/
public class Inventory extends Floor {
    private SpriteRenderer inventoryUI;
    private int buffer; // Packages in the inventory
    private int maxSize; // Maximum storage capacity
    private int newFloorCost = 500;
    private int increaseInventoryCost = 80;

    // UI
    private JPanel buttonsPanel = new JPanel();
    GameButton newFloorBtn = new GameButton(
            "<html><center>" + "ADD NEW FLOOR" + "<br>($" + newFloorCost + ")</center></html>");
    GameButton increaseInventoryBtn = new GameButton(
            "<html><center>" + "INCREASE INVENTORY" + "<br>($" + increaseInventoryCost + ")</center></html>");

    public Inventory(int initialMaxSize) {
        buffer = 0;
        maxSize = initialMaxSize;
        updateInventoryStatsUI();

        initUI();
    }

    public void init() {
        int w = 400;
        int h = 175;
        int y = 720 - 20 - 175 + h;
        int x = 120 - 1;

        inventoryUI = new SpriteRenderer(PathHelper.sprites + "building//stock.png", transform, 1);
        transform.setScale(new Vector(w, h));
        transform.setPosition(new Vector(x, y));
        inventoryUI.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        addChild(inventoryUI);
    }

    /**
     * Adds a single resource to the inventory buffer.
     * This method blocks if the inventory is full.
     */
    public synchronized void addResource() {
        while (buffer >= maxSize) {
            try {
                wait();             // Wait until space becomes available
            } catch (Exception e) {
                Debug.err(e.getMessage());
            }
        }
        buffer++;                   // Increment the inventory buffer
        updateInventoryStatsUI();   // Update UI to reflect new inventory count
        notifyAll();                // Notify any waiting threads
    }

    /**
     * Removes a specified number of resources from the inventory buffer.
     * This method blocks if the inventory doesn't contain enough resources.
     *
     * @param n The number of resources to remove.
     */
    public synchronized void takeResource(int n) {
        while (buffer < n) {
            try {
                wait();             // Wait until enough resources are available
            } catch (Exception e) {
                Debug.engineLogErr(e.getMessage());
            }
        }
        buffer -= n;                // Decrement the inventory buffer
        updateInventoryStatsUI();   // Update UI to reflect new inventory count
        notifyAll();                // Notify any waiting threads
    }

    /**
     * Gets the current count of resources in the inventory.
     *
     * @return The current resource count.
     */
    public int getResourceCount() {
        return buffer;
    }

    /**
     * Gets the maximum capacity of the inventory.
     *
     * @return The maximum capacity.
     */
    public int getCapacity() {
        return maxSize;
    }

    /**
     * Updates the inventory stats display in the UI, reflecting
     * changes in resource count or capacity.
     */
    private void updateInventoryStatsUI() {
        MainScene.getStatsPanel().setInventoryLabel(this);
    }

    /**
     * Initializes the UI, setting up buttons for adding floors
     * and increasing inventory capacity with appropriate event listeners.
     */
    private void initUI() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

        increaseInventoryBtn.setColors(
                new Color(50, 192, 37),
                new Color(37, 160, 26));

        // Listeners
        newFloorBtn.addActionListener(e -> {
            Economy.spendMoney(newFloorCost);
            Building.get().addRoom();
        });

        increaseInventoryBtn.addActionListener(e -> {
            Economy.spendMoney(increaseInventoryCost);
            maxSize++;
            MainScene.getStatsPanel().setInventoryLabel(this);
        });

        // Add buttons to the panel
        buttonsPanel.add(newFloorBtn);
        buttonsPanel.add(increaseInventoryBtn);
    }

    @Override
    public JPanel getUI() {
        return buttonsPanel;
    }
}