package org.cup.assets.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.cup.assets.objects.Inventory;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;

public class StatsPanel extends JPanel {
    private JLabel balanceLabel;
    private JLabel inventoryLabel;
    private JLabel productValueLabel;

    private JPanel floorPanel;

    public StatsPanel(){
        final int PANEL_WIDTH = GameManager.game.getWidth() / 2 - 100;
        final int PANEL_HEIGHT = (GameManager.game.getHeight() - 39) * 2 / 5;

        // Set the absolute position
        setBounds(GameManager.game.getWidth() - PANEL_WIDTH - 40, 20, PANEL_WIDTH, PANEL_HEIGHT);
        
        // Main panel with padding
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Add elements Vertically

        // Information panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1, 5, 5));

        // TODO
        balanceLabel = new JLabel("BALANCE: " + 0);
        inventoryLabel = new JLabel("INVENTORY CAPACITY: " + 0 + "/?");
        productValueLabel = new JLabel("PRODUCT VALUE: " + 1);

        // Labels with custom font and style
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        balanceLabel.setFont(boldFont);
        inventoryLabel.setFont(boldFont);
        productValueLabel.setFont(boldFont);

        infoPanel.add(balanceLabel);
        infoPanel.add(inventoryLabel);
        infoPanel.add(productValueLabel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        // Add components to main panel
        this.add(infoPanel);
        this.add(Box.createRigidArea(new Dimension(0, 20))); // Space
        this.add(separator);
        this.add(Box.createRigidArea(new Dimension(0, 20))); // Space
    }

    public void setBalanceLabel(double balance){
        balanceLabel.setText("BALANCE: " + balance);
    }

    public void setInventoryLabel(Inventory inventory){
        inventoryLabel.setText("INVENTORY CAPACITY: " + inventory.getResourceCount() + "/" + inventory.getCapacity());
    }

    public void setProductValueLabel(double value){
        productValueLabel.setText("PRODUCT VALUE: " + value);
    }

    public void updateFloorPanel(Floor f) {
        SwingUtilities.invokeLater(() -> {
            if (floorPanel != null) {
                remove(floorPanel);
            }
            
            JPanel newPanel = f.getUI();
            if (newPanel != null) {
                floorPanel = newPanel;
                add(floorPanel);
                revalidate();
            }
        });
    }
}
