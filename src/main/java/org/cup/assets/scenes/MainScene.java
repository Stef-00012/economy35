package org.cup.assets.scenes;

import org.cup.assets.objects.Rectangle;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.Scene;
import org.cup.engine.core.nodes.components.defaults.Transform;

import java.awt.*;

public class MainScene extends Scene {
    Transform squareTransform = new Transform(); // Container for all the squares


    @Override
    public void init() {
        Debug.log("Main Scene initialized");

        // create all the placceholders
        // for the game areas
        createAreas();

        squareTransform.setPosition(new Vector(0, 0));
    }

    @Override
    public void onEnable() {
        Debug.log("Main Scene enabled");
    }


    private void createAreas(){
        // sidewalk
        // addChild(createSection(1280, 720/2, 0, 0, 1, Color.RED));
        addChild(createSection(1280, 20, 0, 700, 1, Color.BLACK));
    }

    private Rectangle createSection(int w, int h, int x, int y, int layer, Color c){
        Rectangle r = new Rectangle(w, h, x, y, layer, c);
        // r.transform.setParentTransform(squareTransform);
        return r;
    }

}
