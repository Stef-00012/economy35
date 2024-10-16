package assets.objects;

import engine.Vector;
import engine.core.nodes.GameNode;
import engine.core.nodes.components.defaults.Sprite;

public class Player extends GameNode {
    private Sprite sprite = new Sprite("./assets/sprites/test.png", transform, 5);

    @Override
    public void init() {
        System.out.println("This is a test warning");
        System.out.println("Player Initialized");

        addChild(sprite);

        transform.setScale(new Vector(200));
        transform.setPosition(new Vector(500, 100));
    }

    @Override
    public void onEnable() {
        System.out.println("Player Enabled");
    }

    @Override
    public void onUpdate() {
        // This will be executed every frame
    }

    @Override
    public void onDisable() {
        
    }

    
}
