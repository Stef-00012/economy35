package org.cup.assets.scenes;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.StatsPanel;
import org.cup.assets.logic.Economy;
import org.cup.assets.objects.Building;
import org.cup.assets.objects.CustomerSpawner;
import org.cup.assets.objects.Rectangle;
import org.cup.engine.Utils;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.managers.sound.SoundManager;
import org.cup.engine.core.nodes.Scene;
import org.cup.engine.core.nodes.components.defaults.Transform;

import java.awt.*;

import javax.sound.sampled.Clip;

public class MainScene extends Scene {
    private Building building = new Building();
    private static StatsPanel statsPanel = new StatsPanel();

    private static Transform scrollableTransform = new Transform(); 
    private static double transformOffset;
    private static final double scrollSpeed = 5;
    private static final double scrollStep = Building.ROOM_HEIGHT;
    
    private Rectangle floor;

    private Clip mainTheme = SoundManager.createClip(PathHelper.music + "MainTheme.wav", true);

    @Override
    public void init() {
        Debug.log("Main Scene initialized");

        addChild(building);
        building.addRoom();
        building.addRoom();
        addToScrollTransform(building.transform);

        // Initialize stats panel
        Economy.setProductValue(10);
        Economy.setBalance(999999999);
        GameManager.game.addUIElement(statsPanel);

        floor = new Rectangle(GameManager.game.getWidth(), 20, 0, GameManager.game.getHeight()-59, 0, new Color(40,40,40));
        addChild(floor);
        addToScrollTransform(floor.transform);

        addChild(new CustomerSpawner());
        mainTheme.start();
    }

    @Override
    public void onEnable() {
        Debug.log("Main Scene enabled");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        double lepedY = Utils.lerp(scrollableTransform.getPosition().y, transformOffset, scrollSpeed * GameManager.getDeltaTime());
        scrollableTransform.setPosition(new Vector(0, lepedY));
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
        return r;
    }

    public static StatsPanel getStatsPanel(){
        return statsPanel;
    }

    public static void scrollUp(){
        transformOffset += scrollStep * 1.5f;
    }

    public static void scrollDown(){
        transformOffset -= scrollStep * 1.5f;
        if(transformOffset < 0){
            transformOffset = 0;
        }
    }

    public static void addToScrollTransform(Transform t){
        t.setParentTransform(scrollableTransform);
    }

    public static Transform getScrollTransform(){
        return scrollableTransform;
    }
}
