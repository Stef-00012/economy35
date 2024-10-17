package org.cup.engine.core.nodes.components.defaults;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.JPanel;

import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.components.Renderer;

public class Animator extends Renderer {
    private final HashMap<String, Animation> animations;
    private Animation currentlyPlaying;

    private long lastSpriteChange;
    private Vector previousScale;

    private Transform transform;
    private JPanel observer;
    private Image image;

    public Animator(Transform transform, int layer) {
        super(layer);
        animations = new HashMap<>();
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
        Vector pos = transform.getPosition().subtract(scale.divide(2)); // Center the square
        double rotation = transform.getRotation();

        g.rotate(rotation);

        if (scale != previousScale) {
            currentlyPlaying.resizeSprites(pos);
            previousScale = scale;
        }

        ((Graphics) g).drawImage(image, pos.getX(), pos.getY(), scale.getX(), scale.getY(), observer);

        // Reset color and rotation
        g.rotate(-rotation);
    }

    public void addAnimation(String name, Animation animation) {
        if (animations.containsKey(name)) {
            Debug.warn("Animation already present in the animator as " + name + ". Now it has been replaced.");
        }

        animations.put(name, animation);

        if (currentlyPlaying == null) {
            play(name);
        }
    }

    public void play(String animationName){
        Animation toPlay = animations.getOrDefault(animationName, null);
    
        if (toPlay == null) {
            Debug.engineLogErr("Animation " + animationName + " not found in animator");
            return;
        }
    
        currentlyPlaying = toPlay;
        currentlyPlaying.reset();
        //currentlyPlaying.resizeIfNecessary(transform.getScale());
    
        image = currentlyPlaying.nextFrame(); 
        lastSpriteChange = System.currentTimeMillis(); 
    }
    

    @Override
    public void onUpdate() {
        if (currentlyPlaying == null) {
            Debug.engineLogErr("There's no animation playing");
            return;
        }
        // Check if it's time to switch to the next frame
        if (System.currentTimeMillis() - lastSpriteChange > currentlyPlaying.getTimeBetweenFrames()) {
            if (currentlyPlaying.isLastFrame() && !currentlyPlaying.isLoop())
                return; // If the animation has ended, return

            image = currentlyPlaying.nextFrame(); // Update to the next frame
            lastSpriteChange = System.currentTimeMillis(); // Update the last sprite change time
        }
    }

}
