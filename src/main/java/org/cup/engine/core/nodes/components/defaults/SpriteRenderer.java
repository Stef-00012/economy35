package org.cup.engine.core.nodes.components.defaults;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import org.cup.engine.Vector;
import org.cup.engine.core.managers.ResourceManager;
import org.cup.engine.core.nodes.components.Renderer;

/**
 * Represents a sprite or an image
 */
public class SpriteRenderer extends Renderer {
    private Transform transform;
    private String currentImagePath;
    private JPanel observer;
    
    /**
     * Constructor for the SpriteRenderer
     * @param imageSrc The image of the sprite
     * @param transform The transform used are reference 
     * @param layer The rendering layer
     */
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
    
    /**
     * Updates the component to render a new sprite 
     * @param imageSrc
     */
    public void setSprite(String imageSrc) {
        this.currentImagePath = imageSrc;
    }

    public void flip() {
        transform.setScale(Vector.multiplyVec(new Vector(-1, 1), transform.getScale()));
    }
}