package org.cup.assets.UI;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.RoundRectangle2D;

public class GameButton extends JButton {
    private Color BUTTON_COLOR;
    private Color BORDER_COLOR;
    private int CORNER_RADIUS;
    private int BORDER_SIZE;
    
    public GameButton(String text, Color btnColor, Color borderColor, int borderRadius, int borderSize) {
        super(text);
        BUTTON_COLOR = btnColor;
        BORDER_COLOR = borderColor;
        CORNER_RADIUS = borderRadius;
        BORDER_SIZE = borderSize;
        setup();
    }
    
    // Creates a btn with
    // - border radius: 15px
    // - border size: 5px
    public GameButton(String text, Color btnColor, Color borderColor) {
        this(text, btnColor, borderColor, 15, 5);
    }

    // Creates a YELLOW btn with
    // - border radius: 15px
    // - border size: 5px
    public GameButton(String text) {
        this(text, new Color(212, 163, 0), new Color(183, 140, 0));
    }

    public void setColors(Color btnColor, Color borderColor){
        BUTTON_COLOR = btnColor;
        BORDER_COLOR = borderColor;
    }

    private void setup() {
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setFocusable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Make the button slightly larger to accommodate the border
        Dimension size = getPreferredSize();
        size.width += BORDER_SIZE * 2;
        size.height += BORDER_SIZE * 2;
        setPreferredSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw border (darker color)
        g2.setColor(BORDER_COLOR);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, CORNER_RADIUS, CORNER_RADIUS));

        // Draw main button (lighter color) - slightly smaller to create border effect
        g2.setColor(BUTTON_COLOR);
        g2.fill(new RoundRectangle2D.Float(
            BORDER_SIZE, BORDER_SIZE, 
            width - (BORDER_SIZE * 2), 
            height - (BORDER_SIZE * 2), 
            CORNER_RADIUS - BORDER_SIZE, 
            CORNER_RADIUS - BORDER_SIZE
        ));

        // Create a smaller graphics context for the text to account for the border
        Graphics2D textG2 = (Graphics2D) g2.create(
            BORDER_SIZE, 
            BORDER_SIZE, 
            width - (BORDER_SIZE * 2), 
            height - (BORDER_SIZE * 2)
        );

        // Let the superclass handle the HTML text rendering
        super.paintComponent(textG2);

        textG2.dispose();
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Border is handled in paintComponent
    }
}