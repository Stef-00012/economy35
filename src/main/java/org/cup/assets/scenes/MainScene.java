package org.cup.assets.scenes;

import org.cup.assets.objects.MySquare;
import org.cup.assets.objects.Player;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.Scene;
import org.cup.engine.core.nodes.components.defaults.Transform;

import java.awt.*;

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

    @Override
    public void onEnable() {
        Debug.log("Main Scene enabled");
    }

    private MySquare createSquare(double range, Color c, int layer){
        MySquare square = new MySquare(2, range, layer, c);
        square.transform.setParentTransform(squareTransform);
        return square;
    }

}
