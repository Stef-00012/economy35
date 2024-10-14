package Engine.Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages rendering of graphical components in a multi-layered system.
 * It notifies registered listeners to request rendering updates and
 * processes a queue of renderable components through the Painter.
 */
public class GraphicsManager extends Component {

    /**
     * Interface for listeners that want to be notified when
     * rendering requests need to be made.
     */
    public interface RenderRequestListener {
        void onRenderRequest(GraphicsManager graphicsManager);
    }

    // List of listeners that will be notified for rendering requests.
    private List<RenderRequestListener> renderListeners = new ArrayList<>();

    private Painter painter;

    public GraphicsManager() {
        painter = new Painter();
    }

    /**
     * Sets up the GraphicsManager. 
     * This method is called when the component is initialized.
     */
    @Override
    public void setup() {
        System.out.println("Graphics Manager is READY");
    }

    @Override
    public void onEnable() {}

    /**
     * Updates the GraphicsManager. Notifies all registered listeners
     * to request rendering, and then updates the Painter's UI.
     */
    @Override
    public void update() {
        for (RenderRequestListener listener : renderListeners) {
            listener.onRenderRequest(this);
        }
        painter.updateUI();
    }

    /**
     * Adds a RenderComponent to the rendering queue.
     *
     * @param component - The RenderComponent to add.
     */
    public void enqueueRenderComponent(RenderComponent component) {
        painter.addToQueue(component);
    }

    /**
     * Registers a new listener to receive render requests.
     *
     * @param listener - The listener to add.
     */
    public void addRenderListener(RenderRequestListener listener) {
        renderListeners.add(listener);
    }

    /**
     * Gets the Painter instance responsible for rendering.
     *
     * @return - The Painter object.
     */
    public Painter getPainter() {
        return painter;
    }
}
