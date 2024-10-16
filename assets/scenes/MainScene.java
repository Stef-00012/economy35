package assets.scenes;

import java.awt.Color;

import engine.Debug;
import engine.Vector;
import engine.core.nodes.Scene;
import engine.core.nodes.components.defaults.Square;

public class MainScene extends Scene {

    @Override
    public void init() {
        Debug.log("Main Scene initialized");
        addChild(new Square(new Vector(200, 200), 0));

        Square s2 = new Square(new Vector(200, 200), 1);
        s2.setColor(Color.MAGENTA);
        s2.setPosition(new Vector(100, 100));
        addChild(s2);
    }
    
}
