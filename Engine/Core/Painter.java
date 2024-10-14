package Engine.Core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Responsible for rendering graphical components on a JPanel.
 * It manages a queue of RenderComponent objects to be rendered.
 */
public class Painter extends JPanel {

    // Thread-safe queue for storing renderable components.
    private final PriorityBlockingQueue<RenderComponent> renderQueue;

    /**
     * Constructor for the Painter.
     * Initializes the render queue.
     */
    public Painter() {
        renderQueue = new PriorityBlockingQueue<>();
    }

    /**
     * Adds a RenderComponent to the render queue.
     *
     * @param component The RenderComponent to be added.
     */
    public void addToQueue(RenderComponent component) {
        renderQueue.add(component);
    }

    /**
     * Paints the components on the JPanel.
     * This method is called whenever the component needs to be redrawn.
     *
     * @param g The Graphics context for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Render each component in the queue.
        while (!renderQueue.isEmpty()) {
            RenderComponent component = renderQueue.poll();
            component.render(g2);
        }
    }
}
