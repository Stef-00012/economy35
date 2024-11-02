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

import org.cup.assets.objects.Inventory;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;

public class StatsPanel extends JPanel {
    private JLabel balanceLabel;
    private JLabel inventoryLabel;
    private JLabel productValueLabel;

    private JButton increaseInventoryBtn;
    private JButton increaseValueBtn;

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

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));

        increaseInventoryBtn = new GameButton("<html><center>" + "INCREASE INVENTORY" + "<br>" + "(1000)" + "</center></html>");
        increaseValueBtn = new GameButton("<html><center>" + "INCREASE PRODUCT VALUE" + "<br>" + "(3000)" + "</center></html>");

        initializeListeners();

        buttonsPanel.add(increaseInventoryBtn);
        buttonsPanel.add(increaseValueBtn);

        buttonsPanel.add(increaseInventoryBtn);
        buttonsPanel.add(increaseValueBtn);

        // Add components to main panel
        this.add(infoPanel);
        this.add(Box.createRigidArea(new Dimension(0, 20))); // Space
        this.add(separator);
        this.add(Box.createRigidArea(new Dimension(0, 20))); // Space
        this.add(buttonsPanel);
    }

    private void initializeListeners(){
        increaseInventoryBtn.addActionListener(e -> {
            
        });

        increaseValueBtn.addActionListener(e -> {
            
        });
    }

    public void setBalanceLabel(double balance){
        balanceLabel.setText("BALANCE: " + balance);
    }

    public void setInventoryLabel(Inventory inventory){
        balanceLabel.setText("INVENTORY CAPACITY: " + inventory.getResourceCount() + "/" + inventory.getCapacity());
    }

    public void setProductValueLabel(double value){
        balanceLabel.setText("PRODUCT VALUE: " + value);
    }
}
