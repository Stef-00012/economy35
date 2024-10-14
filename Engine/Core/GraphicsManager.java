package Engine.Core;

import java.util.ArrayList;
import java.util.List;

public class GraphicsManager extends Component {
    public interface QueueRequestListener {
        void requestRender(GraphicsManager graphicsManager);
    }

    private List<QueueRequestListener> listeners = new ArrayList<QueueRequestListener>();
    private Painter painter;

    public GraphicsManager() {
        painter = new Painter();
    }

    @Override
    public void setup() {
        System.out.println("Graphics Manager is READY");
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void update() {
        for (QueueRequestListener l : listeners) {
            l.requestRender(this);
        }

        painter.updateUI();
    }

    public void addToQueue(RenderComponent obj) {
        painter.addToQueue(obj);
    }

    public void addListener(QueueRequestListener toAdd) {
        listeners.add(toAdd);
    }

    public Painter getPainter() {
        return painter;
    }

}
