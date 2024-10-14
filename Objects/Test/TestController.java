package Objects.Test;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Engine.Core.RenderComponent;

public class TestController extends RenderComponent {

    @Override
    public void render(Graphics2D g) {
        g.draw(new Rectangle(100, 200));
    }

    @Override
    public void onEnable() {}

    @Override
    public void update() {}

    
}
