package org.cup.engine.core.nodes.components.defaults;

import org.cup.engine.Vector;
import org.cup.engine.core.nodes.components.Renderer;

import java.awt.*;

/**
 * Represents a square that can be rendered with transformations,
 * including position, scale, and rotation.
 */
public class Square extends Renderer {
    private Transform transform;
    private Color color;

    /**
     * Constructs a Square with the specified Transform and layer.
     *
     * @param transform The Transform object that defines the square's properties.
     * @param layer     The rendering layer of the square.
     */
    public Square(Transform transform, int layer) {
        super(layer);
        this.transform = transform;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);

        // Get scale and position from the transform
        Vector scale = transform.getScale();
        Vector pos = transform.getPosition().subtract(scale.divide(2)); // Center the square
        double rotation = transform.getRotation();

        g.rotate(rotation);
        g.fillRect(pos.getX(), pos.getY(), scale.getX(), scale.getY());

        // Reset color and rotation
        g.rotate(-rotation);
        g.setColor(Color.black);
    }

    // #region Getters & Setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    // #endregion
}

