package org.cup.engine.core.nodes.components.defaults;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cup.engine.Vector;
import org.cup.engine.core.Debug;

public class Animation {
    private Image[] renderableSprites;
    private float timeBetweenFrames;
    private boolean loop;

    private int frameCount;

    public Animation(String[] sprites, float timeBetweenFrames, boolean loop) {
        this.renderableSprites = new Image[sprites.length];
        this.timeBetweenFrames = timeBetweenFrames;
        this.frameCount = 0;
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

    public void resizeSprites(Vector size) {
        for (int i = 0; i < renderableSprites.length; i++) {
            // Use Image.SCALE_SMOOTH for better quality
            Image scaledImage = renderableSprites[i].getScaledInstance((int) size.getX(), (int) size.getY(), Image.SCALE_SMOOTH);
            renderableSprites[i] = scaledImage;
        }
    }

    public Image nextFrame() {
        if (isLastFrame() && loop) {
            frameCount = 0; // Loop back to the start
        }

        Image frame = renderableSprites[frameCount];
        frameCount++;
        return frame;
    }

    public boolean isLastFrame() {
        return frameCount >= renderableSprites.length;
    }

    public void reset() {
        frameCount = 0;
    }

    public float getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    public void setTimeBetweenFrames(float speed) {
        this.timeBetweenFrames = speed;
    }

    public boolean isLoop() {
        return loop;
    }
}
