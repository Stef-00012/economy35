package assets.scenes;

import java.awt.Color;

import engine.Debug;
import engine.Vector;
import engine.core.managers.GameManager;
import engine.core.nodes.GameNode;
import engine.core.nodes.components.defaults.Square;

public class MySquare extends GameNode {
    private Square sr; // The square renderer

    private double speed;
    private double range;
    private float t;

    public MySquare(double speed, double range, int layer, Color c) {
        super();

        this.speed = speed;
        this.range = range;

        t = 0;

        sr = new Square(transform, layer);
        addChild(sr);
        sr.setColor(c);
    }

    @Override
    public void onEnable() {
        transform.setPosition(new Vector(200, 200));
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
