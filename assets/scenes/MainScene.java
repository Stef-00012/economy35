package assets.scenes;


import java.awt.Color;

import assets.objects.MySquare;
import assets.objects.Player;
import engine.Debug;
import engine.Vector;
import engine.core.nodes.Scene;
import engine.core.nodes.components.defaults.Transform;

public class MainScene extends Scene {
    Transform squareTransform = new Transform(); // Container for all the squares


    @Override
    public void init() {
        Debug.log("Main Scene initialized");

        squareTransform.setPosition(new Vector(200, 200));

        addChild(createSquare(150, Color.darkGray, 0));
        addChild(createSquare(100, Color.gray, 1));
        addChild(createSquare(50, Color.white, 2));

        addChild(new Player());
    }

    private MySquare createSquare(double range, Color c, int layer){
        MySquare square = new MySquare(2, range, layer, c);
        square.transform.setParentTransform(squareTransform);
        return square;
    }
    
}
