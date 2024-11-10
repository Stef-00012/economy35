package org.cup.assets.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import org.cup.assets.PathHelper;
import org.cup.assets.objects.Inventory;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;

public class StatsPanel extends JPanel {
    private GameLabel balanceLabel;
    private GameLabel inventoryLabel;
    private GameLabel productValueLabel;

    private JPanel floorPanel;

    public StatsPanel(){
        final int PANEL_WIDTH = GameManager.game.getWidth() / 2 - 100;
        final int PANEL_HEIGHT = (GameManager.game.getHeight() - 39) * 2 / 5;

        setOpaque(false);

        // Set the absolute position
        setBounds(GameManager.game.getWidth() - PANEL_WIDTH - 40, 20, PANEL_WIDTH, PANEL_HEIGHT);
        
        // Main panel with padding
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Add elements Vertically

        // Information panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new GridLayout(3, 1, 5, 5));

        balanceLabel = new GameLabel("BALANCE: " + 0);
        balanceLabel.setIcon(scaleIcon(PathHelper.icons + "coin.png", 20, 20));
        
        inventoryLabel = new GameLabel("INVENTORY CAPACITY: " + 0 + "/?");
        inventoryLabel.setIcon(scaleIcon(PathHelper.icons + "box.png", 20, 20));

        Debug.log(inventoryLabel.getIcon());

        productValueLabel = new GameLabel("PRODUCT VALUE: " + 1);
        productValueLabel.setIcon(scaleIcon(PathHelper.icons + "diamond.png", 20, 20));

        infoPanel.add(balanceLabel);
        infoPanel.add(inventoryLabel);
        infoPanel.add(productValueLabel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setOpaque(false);
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
        inventoryLabel.setText("INVENTORY CAPACITY: " + inventory.getResourceCount() + "-" + inventory.getCapacity());
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
                floorPanel.setOpaque(false);
                add(floorPanel);
                revalidate();
            }
        });
    }

    private ImageIcon scaleIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void dayMode(){
        balanceLabel.setForeground(Color.BLACK);
        inventoryLabel.setForeground(Color.BLACK);
        productValueLabel.setForeground(Color.BLACK);
    }

    public void nightMode(){
        balanceLabel.setForeground(Color.WHITE);
        inventoryLabel.setForeground(Color.WHITE);
        productValueLabel.setForeground(Color.WHITE);
    }
}
