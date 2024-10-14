package Engine.Core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.PriorityBlockingQueue;

public class Painter extends JPanel {
    private final PriorityBlockingQueue<RenderComponent> queue;

    public Painter() {
        queue = new PriorityBlockingQueue<>();
    }

    public void addToQueue(RenderComponent e) {
        queue.add(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        while (!queue.isEmpty()) {
            RenderComponent component = queue.poll();
            component.render(g2);
        }
    }
}