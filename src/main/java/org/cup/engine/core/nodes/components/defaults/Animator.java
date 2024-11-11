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

/**
 * Handles multiple animations and controls the playback of a currently playing
 * animation.
 */
public class Animator extends Renderer {
    private final HashMap<String, Animation> animations;
    private Animation currentlyPlaying;

    private long lastSpriteChange;

    private Transform transform;
    private JPanel observer;

    private Image frame;

    /**
     * Constructs an Animator with a specified transform and rendering layer.
     *
     * @param transform The Transform object for position and rotation.
     * @param layer     The rendering layer.
     */
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
        Vector pos = calculateDrawingPosition(transform);
        double rotation = transform.getRotation();

        g.rotate(rotation);

        ((Graphics) g).drawImage(frame, pos.getX(), pos.getY(), scale.getX(), scale.getY(), observer);

        // Reset rotation
        g.rotate(-rotation);
    }

    /**
     * Adds a new animation to the animator.
     *
     * @param name      The name to identify the animation.
     * @param animation The Animation object to add.
     */
    public void addAnimation(String name, Animation animation) {
        if (animations.containsKey(name)) {
            Debug.warn("Animation already present in the animator as " + name + ". Now it has been replaced.");
        }

        animations.put(name, animation);

        // Automatically play the first animation
        if (currentlyPlaying == null) {
            play(name);
        }
    }

    /**
     * Plays the specified animation by name.
     *
     * @param animationName The name of the animation to play.
     */
    public void play(String animationName) {
        Animation toPlay = animations.get(animationName);

        if (toPlay == null) {
            Debug.engineLogErr("Animation " + animationName + " not found in animator");
            return;
        }

        currentlyPlaying = toPlay;
        currentlyPlaying.reset();

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

            frame = currentlyPlaying.nextFrame(transform.getScale()); // Update to the next frame
            lastSpriteChange = System.currentTimeMillis(); // Update the last sprite change time
        }
    }

    /**
     * Flips the animation horizontally by inverting the X-axis scale of the current
     * transformation.
     * This method multiplies the X component of the current scale by -1,
     * effectively mirroring the image horizontally.
     */
    public void flipHorizontally() {
        transform.setScale(Vector.multiplyVec(new Vector(-1, 1), transform.getScale()));
    }
}
