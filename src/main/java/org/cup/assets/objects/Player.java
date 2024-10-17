package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class Player extends GameNode {
    private SpriteRenderer sprite = new SpriteRenderer(PathHelper.getSpritePath("test.png"), transform, 5);

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

