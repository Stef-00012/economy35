package org.cup.engine.core.nodes.components.defaults;

import org.cup.engine.Utils;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.components.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents a sprite that can be rendered with transformations,
 * including position, scale, and rotation. The sprite is loaded from an image file.
 */
public class SpriteRenderer extends Renderer {
    public boolean useFastResizing = false;

    private Transform transform;
    private Image image;
    private JPanel observer;

    private Vector previousScale;

    private boolean scaleNextFrame;

    /**
     * Constructs a Sprite with the specified image source and transform.
     *
     * @param imageSrc The source file path of the image to load.
     * @param transform The Transform object that defines the sprite's properties.
     * @param layer     The rendering layer of the sprite.
     */
    public SpriteRenderer(String imageSrc, Transform transform, int layer) {
        super(layer);
        image = Utils.tryGetImage(imageSrc);
        scaleNextFrame = false;
        this.transform = transform;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        observer = GameManager.graphicsManager.getPainter();
    }

    @Override
    public void render(Graphics2D g) {
        Vector scale = transform.getScale();
        Vector pos = calculateDrawingPosition(transform);
        double rotation = transform.getRotation();

        if(scale != previousScale || scaleNextFrame){
            image.flush();
            image = image.getScaledInstance(scale.getX(), scale.getY(), useFastResizing ? Image.SCALE_FAST : Image.SCALE_SMOOTH);
            previousScale = scale;
            scaleNextFrame = false;
        }

        g.rotate(rotation);
        ((Graphics) g).drawImage(image, pos.getX(), pos.getY(), scale.getX(), scale.getY(), observer);

        // Reset rotation
        g.rotate(-rotation);
    }

    /**
     * Changes the image src
     * @param imageSrc The image location / path
     */
    public void setSprite(String imageSrc){
        image.flush();
        image = Utils.tryGetImage(imageSrc);
        scaleNextFrame = true;
    }
}
