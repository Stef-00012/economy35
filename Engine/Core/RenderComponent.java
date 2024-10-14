package Engine.Core;

import java.awt.Graphics2D;

/**
 * Class to interact with the GraphicsManager
 */
public abstract class RenderComponent extends Component implements Comparable<RenderComponent> {
    public int layer;

    public RenderComponent(int layer) {
        super();
        this.layer = layer;
    }

    public RenderComponent() {
        super();
        this.layer = 0;
    }

    /**
     * Method which defines how the component should be rendered.
     * @param g - The Graphics 2D object passed by the Graphics Manager
     */
    public abstract void render(Graphics2D g);

    @Override
    public int compareTo(RenderComponent o) {
        return Integer.compare(this.layer, o.layer);
    }

    @Override
    public void setup() {
        // Ask the Graphics Manager to be rendered next frame.
        GameManager.graphicsManager.addRenderListener((graphicsManager) -> {
            graphicsManager.enqueueRenderComponent(this);
        });
    }

}
