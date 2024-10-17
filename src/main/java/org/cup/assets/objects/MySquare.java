package org.cup.assets.objects;

import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SquareRenderer;

import java.awt.*;

/**
 * Square that scales based on the sine function
 */
public class MySquare extends GameNode {
    private SquareRenderer sr; // The square renderer

    private double speed;
    private double range;
    private float t;

    public MySquare(double speed, double range, int layer, Color c) {
        super();

        this.speed = speed;
        this.range = range;

        t = 0;

        sr = new SquareRenderer(transform, layer); // Create the square renderer
        addChild(sr); // Add the square renderer as a child of this node
        sr.setColor(c);
    }

    @Override
    public void onEnable() {
        //transform.setPosition(new Vector(200, 200));
        transform.setScale(new Vector(range, range));
    }

    @Override
    public void onUpdate() {
        Vector minScale = new Vector(range);
        Vector additionalSquare = new Vector(Math.sin(t) * range);

        transform.setScale(minScale.add(additionalSquare));
        // Debug.log(Vector.ONE.multiply(Math.sin(t) * range).toString());
        t += GameManager.getDeltaTime() * speed; // Increment time
    }
}

