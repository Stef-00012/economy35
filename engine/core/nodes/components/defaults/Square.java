package engine.core.nodes.components.defaults;

import java.awt.Color;
import java.awt.Graphics2D;
import engine.Vector;

import engine.core.nodes.components.Renderer;

public class Square extends Renderer {
    private Transform transform;
    private Color color;

    public Square(Transform transform, int layer) {
        super(layer);
        this.transform = transform;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);

        Vector scale = transform.getScale();
        Vector pos = transform.getPosition().subtract(scale.divide(2)); // Center the square
        double rotation = transform.getRotation();

        g.rotate(rotation);
        g.fillRect(pos.getX(), pos.getY(), scale.getX(), scale.getY());

        // Reset color and rotation
        g.rotate(-rotation);
        g.setColor(Color.black);
    }
    

    //#region Getters & Setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    //#endregion
}
