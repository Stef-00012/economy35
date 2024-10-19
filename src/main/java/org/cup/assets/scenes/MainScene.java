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
        // sky
        addChild(createSection(1280, 720, 0, 0, 0, new Color(139, 228, 241)));

        // sidewalk
        addChild(createSection(1280, 20, 0, 700, 1, Color.BLACK));

        // elevator cab (should adapt it's height tho the building's height -> n of rooms)
        addChild(createSection(120, 175, 0, 720-20-175, 2, Color.GRAY));

        // elevator structure
        addChild(createSection(120, 720-20, 0, 0, 1, Color.LIGHT_GRAY));

        // inventory
        addChild(createSection(400, 175, 120, 720-20-175, 1, new Color(19, 21, 21)));

        // store
        addChild(createSection(240, 175, 120+400, 720-20-175, 1, new Color(236, 178, 91)));

        // divider
        addChild(createSection(40, 720-20-175, 120+500, 0, 1, new Color(40, 40, 40)));

        // worker rooms
        for (int i = 1; i <= 3; i++) {
            addWorkerRoom(i);
        }
    }
    
    
    private void addWorkerRoom(int roomNumber){
        Color col = roomNumber % 2 == 0 ? Color.DARK_GRAY : Color.GRAY;
        addChild(createSection(500, 175, 120, 720-20-(175 * (roomNumber + 1)), 1, col));
    }

    private Rectangle createSection(int w, int h, int x, int y, int layer, Color c){
        Rectangle r = new Rectangle(w, h, x, y, layer, c);
        r.transform.setParentTransform(squareTransform);
        return r;
    }

}
