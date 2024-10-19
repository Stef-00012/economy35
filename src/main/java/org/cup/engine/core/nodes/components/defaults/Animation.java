package org.cup.engine.core.nodes.components.defaults;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cup.engine.Vector;
import org.cup.engine.core.Debug;

/**
 * Represents an animation consisting of multiple frames (sprites).
 */
public class Animation {
    private Image[] renderableSprites;
    private float timeBetweenFrames; // Time to wait between frames in milliseconds
    private boolean loop;

    private int currentFrameIndex;

    /**
     * Constructs an Animation with the specified frames, timing, and looping
     * behavior.
     *
     * @param sprites       Array of image file paths for the animation frames.
     * @param timeBetweenFrames Time in milliseconds to wait between frames.
     * @param loop              Whether the animation should loop.
     */
    public Animation(String[] sprites, float timeBetweenFrames, boolean loop) {
        this.renderableSprites = new Image[sprites.length];
        this.timeBetweenFrames = timeBetweenFrames;
        this.currentFrameIndex = 0;
        this.loop = loop;

        if (sprites.length == 0) {
            Debug.engineLogErr("The animation is empty");
        }

        loadImages(sprites);
    }

    public Animation(String[] sprites, boolean loop) {
        this(sprites, 100, loop);
    }

    public Animation(String[] sprites) {
        this(sprites, true);
    }

    private void loadImages(String[] sprites) {
        for (int i = 0; i < sprites.length; i++) {
            try {
                renderableSprites[i] = ImageIO.read(new File(sprites[i]));
            } catch (IOException e) {
                Debug.engineLogErr("Failed to load " + sprites[i]);
                System.exit(0);
            }
        }
    }

    /**
     * Resizes all frames to the specified size.
     *
     * @param size The new size for each frame.
     */
    public void resizeSprites(Vector size) {
        for (int i = 0; i < renderableSprites.length; i++) {
            // Use Image.SCALE_SMOOTH for better quality
            Image scaledImage = renderableSprites[i].getScaledInstance((int) size.getX(), (int) size.getY(),
                    Image.SCALE_SMOOTH);
            renderableSprites[i] = scaledImage;
        }
    }

    /**
     * Retrieves the next frame in the animation sequence.
     *
     * @return The next frame as an Image.
     */
    public Image nextFrame() {
        if (isLastFrame() && loop) {
            currentFrameIndex = 0; // Loop back to the start
        }

        Image frame = renderableSprites[currentFrameIndex];
        currentFrameIndex++;
        return frame;
    }

    /**
     * Checks if the current frame is the last frame of the animation.
     *
     * @return True if the current frame is the last; otherwise false.
     */
    public boolean isLastFrame() {
        return currentFrameIndex >= renderableSprites.length;
    }

    /**
     * Resets the animation to the first frame.
     */
    public void reset() {
        currentFrameIndex = 0;
    }

    /**
     * Gets the time between frames in milliseconds.
     *
     * @return The time between frames.
     */
    public float getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    /**
     * Sets the time between frames.
     *
     * @param speed The new time between frames in milliseconds.
     */
    public void setTimeBetweenFrames(float speed) {
        this.timeBetweenFrames = speed;
    }

    /**
     * Checks if the animation loops.
     *
     * @return True if the animation loops; otherwise false.
     */
    public boolean isLoop() {
        return loop;
    }
}
