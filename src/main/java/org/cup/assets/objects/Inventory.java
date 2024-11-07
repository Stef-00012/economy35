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

    private int buffer;
    private int maxSize;

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
        // inventoryUI = new SquareRenderer(w, h, x, y, 1, new Color(19, 21, 21));
        // inventoryUI = new SquareRenderer(transform, 1);
        inventoryUI = new SpriteRenderer(PathHelper.sprites + "building//stock.png", transform, 1);
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

    private void initUI() {
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

        increaseInventoryBtn.setColors(
                new Color(50, 192, 37),
                new Color(37, 160, 26));

        newFloorBtn.addActionListener(e -> {
            Economy.spendMoney(newFloorCost);
            Building.get().addRoom();
        });

        increaseInventoryBtn.addActionListener(e -> {
            Economy.spendMoney(increaseInventoryCost);
            maxSize++;
            MainScene.getStatsPanel().setInventoryLabel(this);
        });

        buttonsPanel.add(newFloorBtn);
        buttonsPanel.add(increaseInventoryBtn);
    }

    @Override
    public JPanel getUI() {
        return buttonsPanel;
    }
}