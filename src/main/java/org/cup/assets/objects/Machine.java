package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

import java.util.Random;

public class Machine extends GameNode {
    private final String normalSprite = PathHelper.getSpritePath("machine\\machine1.png");
    private final String errorSprite = PathHelper.getSpritePath("machine\\machineError.png");
    private final String boxSprite = PathHelper.getSpritePath("machine\\machineBox.png");
    
    private SpriteRenderer spriteRenderer = new SpriteRenderer(normalSprite, transform, 1);

    private Random randomGen = new Random();

    private int probability = 40; // Probability to drop the resource

    private double lastAttempt; // The time passed since the last time it has been chec
    private double interval = 5000; // Time of the interval(in milliseconds)

    private boolean hasProducedResource;

    public Machine(Room room) {
    }

    @Override
    public void init() {
        addChild(spriteRenderer);
        spriteRenderer.setPivot(Renderer.BOTTOM_LEFT_PIVOT);

        transform.setScale(new Vector(100));
    }

    @Override
    public void onUpdate() {
        if (!hasResource() && System.currentTimeMillis() - lastAttempt > interval) {
            attemptToCreateResource();
            lastAttempt = System.currentTimeMillis();
        }
    }

    public void error() {
        spriteRenderer.setSprite(errorSprite);
    }

    public void success() {
        spriteRenderer.setSprite(boxSprite);
        hasProducedResource = true;
    }

    public void attemptToCreateResource() {
        // Drop the resoure randomly based on the value of the probability, the higher
        // it gets the more probability there is to get a resource
        if (randomGen.nextInt(100) <= probability) {
            success();
        } else {
            error();
        }
    }

    // return if the machine ha a resource
    public boolean hasResource() {
        return hasProducedResource;
    }

    // Reset the machine taking away its resource
    public void takeResource() {
        spriteRenderer.setSprite(normalSprite);
        hasProducedResource = false;
        lastAttempt = System.currentTimeMillis();
    }
}
