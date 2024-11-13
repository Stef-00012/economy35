package org.cup.assets.objects;

import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SquareRenderer;

import java.awt.*;

/**
 * Square that scales based on the sine function
 */
public class Rectangle extends GameNode {
    public SquareRenderer sr; // The square renderer

    private int width;
    private int height;
    private int x;
    private int y;

    public Rectangle(int width, int height, int x, int y, int layer, Color c) {
        super();

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        sr = new SquareRenderer(transform, layer);  // Create the square renderer
        sr.setPivot(Renderer.TOP_LEFT_PIVOT);         // Use the top left pivot
        addChild(sr);                               // Add the square renderer as a child of this node
        sr.setColor(c);
    }

    @Override
    public void onEnable() {
        transform.setScale(new Vector(width, height));
        transform.setPosition(new Vector(x, y));
    }
}

