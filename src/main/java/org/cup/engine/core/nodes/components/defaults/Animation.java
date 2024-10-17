package org.cup.engine.core.nodes.components.defaults;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;

public class Animation {
    private Image[] renderableSprites;
    private float timeBetweenFrames;
    private boolean loop;

    private int frameCount;

    private Vector currentSize;

    public Animation(String[] sprites, float timeBetweenFrames, boolean loop) {
        this.renderableSprites = new Image[sprites.length];
        this.timeBetweenFrames = timeBetweenFrames;
        this.frameCount = 0;
        this.loop = loop;

        if (sprites.length == 0) {
            Debug.engineLogErr("The animation is empty");
        }

        for (int i = 0; i < sprites.length; i++) {
            try {
                renderableSprites[i] = ImageIO.read(new File(sprites[i]));
            } catch (IOException e) {
                Debug.engineLogErr("Failed to load " + sprites[i]);
                System.exit(0);
            }
        }
    }

    public Animation(String[] sprites, boolean loop) {
        this(sprites, 100, loop);
    }

    public Animation(String[] sprites) {
        this(sprites, true);
    }

    public void resizeSprites(Vector size){
        if(currentSize == null){
            currentSize = new Vector(renderableSprites[0].getWidth(GameManager.graphicsManager.getPainter()));
        }
        if(size.len() < currentSize.len()) return;

        int x = size.getX();
        int y = size.getY();
        for(int i = 0; i < renderableSprites.length; i++){
            Image scaledInstance = renderableSprites[i].getScaledInstance(x, y, Image.SCALE_SMOOTH);
            renderableSprites[i] = scaledInstance;
        }
        currentSize = size;
    }

    public Image nextFrame() {
        if (isLastFrame() && loop) {
            frameCount = 0;
        }

        Image frame = renderableSprites[frameCount];

        frameCount++;

        return frame;
    }

    public void resizeIfNecessary(Vector size) {
        if (currentSize != size)
            resizeSprites(size);
    }

    public boolean isLastFrame() {
        return frameCount >= renderableSprites.length;
    }

    public void reset() {
        frameCount = 0;
    }

    // #region Getters & Setters
    public float getTimeBetweenFrames() {
        return timeBetweenFrames;
    }

    public void setTimeBetweenFrames(float speed) {
        this.timeBetweenFrames = speed;
    }

    public boolean isLoop() {
        return loop;
    }
    // #endregion
}
