package engine.core.nodes.components.defaults;

import java.awt.Color;
import java.awt.Graphics2D;
import engine.Vector;

import engine.core.nodes.components.Renderer;

public class Square extends Renderer {
    private Vector position = Vector.ZERO;
    private Vector scale = Vector.ZERO;
    private Color color;

    public Square(Vector scale, int layer) {
        super(layer);
        this.scale = scale;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(position.getX(), position.getY(), scale.getX(), scale.getY());

        g.setColor(Color.black);
    }
    

    //#region Getters & Setters
    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getScale() {
        return scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    //#endregion
}
