package org.cup.assets.scenes;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.StatsPanel;
import org.cup.assets.logic.Economy;
import org.cup.assets.objects.Building;
import org.cup.assets.objects.CustomerSpawner;
import org.cup.assets.objects.DayCycle;
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
    private Building building;

    private static StatsPanel statsPanel;

    private static Transform scrollableTransform; 
    private static double transformOffset;
    private static final double scrollSpeed = 5;
    private static final double scrollStep = Building.ROOM_HEIGHT * 1.3f;
    
    private Rectangle floor;

    private Clip mainTheme;

    public MainScene(){
        scrollableTransform = new Transform();
        statsPanel = new StatsPanel();
        building = new Building();

        mainTheme = SoundManager.createClip(PathHelper.music + "MainTheme.wav", true);
    }

    @Override
    public void init() {
        Debug.log("Main Scene initialized");

        addChild(new DayCycle());

        addChild(building);
        building.addRoom();
        building.addRoom();
        addToScrollTransform(building.transform);

        // Initialize stats panel
        Economy.setProductValue(10);
        Economy.setBalance(999999999);
        GameManager.game.addUIElement(statsPanel);

        // Add base floor
        floor = new Rectangle(GameManager.game.getWidth(), 20, 0, GameManager.game.getHeight()-59, 0, new Color(40,40,40));
        addChild(floor);
        addToScrollTransform(floor.transform);

        // Add Customer Spawner
        addChild(new CustomerSpawner());

        // Start Background Music
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
