package engine.core.nodes.components;

import engine.core.managers.GameManager;
import engine.core.nodes.Node;
import java.awt.Graphics2D;

/**
 * The {@code Renderer} class is an abstract component node that interacts
 * with the {@code Painter} and {@code GraphicsManager} to render graphics
 * on the screen. Each {@code Renderer} instance is assigned a layer, which
 * determines its draw order relative to other renderers (higher layers are
 * drawn on top).
 * <p>
 * Subclasses must implement the {@code render(Graphics2D g)} method to define
 * the
 * rendering behavior.
 * </p>
 */
public abstract class Renderer extends Node {
    private int layer;

    /**
     * Constructs a {@code Renderer} with a specified rendering layer.
     * 
     * @param layer The layer of the node (higher layers are drawn on top).
     */
    public Renderer(int layer) {
        this.layer = layer;
    }

    @Override
    public void onEnable() {
        // Adds itself to the rendering queue
        GameManager.graphicsManager.addToRenderingQueue(this);
    }

    /**
     * Renders the component. Subclasses must implement this method to define 
     * how the component should be drawn on the screen.
     * 
     * @param g The {@code Graphics2D} context used for rendering.
     */
    public abstract void render(Graphics2D g);

    // #region Getters & Setters
    public int getLayer() {
        return layer;
    }

    /**
     * Sets the rendering layer of this {@code Renderer}.
     * <p>
     * This method will also update the rendering queue to reposition this 
     * component based on its new layer.
     * </p>
     * 
     * @param layer The new layer of the renderer.
     */
    public void setLayer(int layer) {
        this.layer = layer;
        GameManager.graphicsManager.updateLayer(this);
    }
    // #endregion

    @Override
    public String toString() {
        return "MyObject{" + "layer=" + layer + ", name='" + getParent().getClass().getSimpleName() + '\'' + '}';
    }
}
