package org.cup.engine.core.managers.graphics;

import org.cup.engine.core.nodes.components.Renderer;

/**
 * The {@code GraphicsManager} handles rendering operations by managing a
 * {@code Painter} and coordinating renderers' interactions with the rendering
 * queue.
 */
public class GraphicsManager {
    private Painter painter;

    public GraphicsManager() {
        painter = new Painter();
    }

    /**
     * Returns the current {@code Painter}.
     *
     * @return The {@code Painter} instance.
     */
    public Painter getPainter() {
        return painter;
    }

    /**
     * Adds a {@code Renderer} to the rendering queue.
     *
     * @param renderer The {@code Renderer} to add.
     */
    public void addToRenderingQueue(Renderer renderer) {
        painter.queue.addObject(renderer);
    }

    /**
     * Removes a {@code Renderer} from the rendering queue.
     *
     * @param renderer The {@code Renderer} to remove.
     */
    public void removeFromRenderingQueue(Renderer renderer) {
        painter.queue.removeObject(renderer);
    }

    /**
     * Updates a {@code Renderer}'s layer in the rendering queue.
     *
     * @param renderer The {@code Renderer} to update.
     */
    public void updateLayer(Renderer renderer) {
        painter.queue.updateObject(renderer);
    }

    /**
     * Updates the graphics by triggering a repaint on the painter.
     */
    public void updateGraphics() {
        painter.removeAll();
        painter.revalidate();
        painter.updateUI();
    }
}

