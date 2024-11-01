package org.cup.assets.scenes;

import org.cup.assets.objects.Building;
import org.cup.assets.objects.Elevator;
import org.cup.assets.objects.Inventory;
import org.cup.assets.objects.Player;
import org.cup.assets.objects.Rectangle;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.Scene;
import org.cup.engine.core.nodes.components.defaults.Transform;
import org.cup.assets.objects.Inventory;

import java.awt.*;

public class MainScene extends Scene {
    private int maxInventorySize = 100;
    
    final Transform sceneTransform = new Transform();
    Building building = new Building();
    Elevator elevator = new Elevator();
    Inventory inventory = new Inventory(maxInventorySize);

    @Override
    public void init() {
        Debug.log("Main Scene initialized");
        sceneTransform.setPosition(new Vector(0, 0));

        addChild(building);
        building.transform.setParentTransform(sceneTransform);

        addChild(elevator);
        elevator.transform.setParentTransform(sceneTransform);

        addChild(inventory);
        inventory.transform.setParentTransform(sceneTransform);

        addRoom();
        addRoom();
    }

    @Override
    public void onEnable() {
        Debug.log("Main Scene enabled");
    }

    public void addRoom(){
        building.addRoom();
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
            // addWorkerRoom(i);
        }
    }


    private Rectangle createSection(int w, int h, int x, int y, int layer, Color c){
        Rectangle r = new Rectangle(w, h, x, y, layer, c);
        r.transform.setParentTransform(sceneTransform);
        return r;
    }

}
