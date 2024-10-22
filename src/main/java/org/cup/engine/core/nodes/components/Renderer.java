package org.cup.engine.core.nodes.components;

import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.Node;
import org.cup.engine.core.nodes.components.defaults.Transform;

import java.awt.*;

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
    // #region Predefined Pivot Positions
    public static final Vector CENTER_PIVOT = new Vector(0.5, 0.5);
    public static final Vector TOP_PIVOT = new Vector(0.5, 0);
    public static final Vector BOTTOM_PIVOT = new Vector(0.5, 1);
    public static final Vector LEFT_PIVOT = new Vector(0, 0.5);
    public static final Vector RIGHT_PIVOT = new Vector(1, 0.5);
    public static final Vector TOP_LEFT_PIVOT = new Vector(0, 0);
    public static final Vector TOP_RIGHT_PIVOT = new Vector(1, 0);
    public static final Vector BOTTOM_LEFT_PIVOT = new Vector(0, 1);
    public static final Vector BOTTOM_RIGHT_PIVOT = new Vector(1, 1);
    // #endregion

    private int layer;
    private Vector pivot = CENTER_PIVOT;

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

    @Override
    public void onDisable() {
        // Removes itself from the rendering queue
        GameManager.graphicsManager.removeFromRenderingQueue(this);
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
     * Returns the position where the Graphics Renderer should start drawing
     * by choosing the appropriate offset based on the pivot
     * 
     * @param transform
     * @return
     */
    public Vector calculateDrawingPosition(Transform transform) {
        return transform.getPosition().subtract(Vector.multiplyVec(transform.getScale(), pivot));
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

    public Vector getPivot() {
        return pivot;
    }

    /**
     * Sets the pivot point for the renderer. The pivot point determines
     * which part of the object is considered its origin when drawing.
     * For example, a center pivot means the object will be drawn centered
     * on its position.
     *
     * @param pivot A {@code Vector} representing the new pivot point.
     */
    public void setPivot(Vector pivot) {
        this.pivot = pivot;
    }
    // #endregion

    @Override
    public String toString() {
        return "MyObject{" + "layer=" + layer + ", name='" + getParent().getClass().getSimpleName() + '\'' + '}';
    }
}
