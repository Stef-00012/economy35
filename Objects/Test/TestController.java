package Objects.Test;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Engine.Core.RenderComponent;

public class TestController extends RenderComponent {
    float posX = 0;
    double deltaTime;
    long lastTime;

    @Override
    public void render(Graphics2D g) {
        // System.out.println("Rendering at x: " + posX);
        g.draw(new Rectangle((int) posX, 0, 200, 200));
    }

    @Override
    public void onEnable() {
        posX = 0;
        lastTime = System.nanoTime();
    }

    @Override
    public void update() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
        lastTime = currentTime;
        System.out.println(deltaTime);
        //System.exit(0);

        posX += deltaTime * 200;
    }

}
