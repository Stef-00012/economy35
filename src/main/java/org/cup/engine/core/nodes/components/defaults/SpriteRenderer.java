package org.cup.engine.core.nodes.components.defaults;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import org.cup.engine.Vector;
import org.cup.engine.core.managers.ResourceManager;
import org.cup.engine.core.nodes.components.Renderer;

public class SpriteRenderer extends Renderer {
    private Transform transform;
    private String currentImagePath;
    private JPanel observer;
    
    public SpriteRenderer(String imageSrc, Transform transform, int layer) {
        super(layer);
        this.currentImagePath = imageSrc;
        this.transform = transform;
    }
    
    @Override
    public void render(Graphics2D g) {
        Vector scale = transform.getScale();
        Vector pos = calculateDrawingPosition(transform);
        double rotation = transform.getRotation();
        
        // Get appropriately scaled image
        Image image = ResourceManager.getImage(
            currentImagePath, 
            scale.getX(), 
            scale.getY()
        );
        
        if (image != null) {
            g.rotate(rotation);
            g.drawImage(image, pos.getX(), pos.getY(), observer);
            g.rotate(-rotation);
        }
    }
    
    public void setSprite(String imageSrc) {
        this.currentImagePath = imageSrc;
    }
}