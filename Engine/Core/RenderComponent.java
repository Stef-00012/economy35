package Engine.Core;

import java.awt.Graphics2D;

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

    public abstract void render(Graphics2D g);

    @Override
    public int compareTo(RenderComponent o) {
        return Integer.compare(this.layer, o.layer);
    }

    @Override
    public void setup() {
        GameManager.graphicsManager.addListener((graphicsManager) -> {
            graphicsManager.addToQueue(this);
        });
    }

}
