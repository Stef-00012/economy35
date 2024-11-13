package org.cup.assets.objects;

import javax.swing.JPanel;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.GameButton;
import org.cup.assets.UI.Floor;
import org.cup.assets.components.CollisionBox;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import org.cup.engine.core.nodes.components.defaults.Transform;

// the damaged room on the first floor 
class UpgradeRoom extends Floor {
    SpriteRenderer sr;
    CollisionBox collisionBox;
    
    static BoostController boostController;

    // UI
    private JPanel buttonsPanel;

    public UpgradeRoom(){
        transform.setScale(Vector.ONE);
        transform.setPosition(new Vector(116, GameManager.game.getHeight() - 59 - Building.ROOM_HEIGHT));

        // Create and add the sprite renderer to the room.
        Transform rendererTransform = new Transform();
        rendererTransform.setScale(new Vector(Building.ROOM_WIDTH, Building.ROOM_HEIGHT));
        sr = new SpriteRenderer(PathHelper.getSpritePath("\\building\\rooms\\room-empty.png"), rendererTransform, 1);
        sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        addChild(sr);
        rendererTransform.setParentTransform(transform);

        //new Vector(0, -rendererTransform.getScale().y)
        Vector scale = rendererTransform.getScale();
        collisionBox = new CollisionBox(new Vector(scale.x / 2, -scale.y/2), rendererTransform.getScale());
        collisionBox.transform.setParentTransform(transform);

        // Add the tube
        Transform tubeTransform = new Transform();
        tubeTransform.setPosition(new Vector(Building.ROOM_WIDTH, 0));
        tubeTransform.setScale(new Vector(41, 175));
        tubeTransform.setParentTransform(transform);
        SpriteRenderer tubeRenderer = new SpriteRenderer(PathHelper.sprites + "building\\Pipe.png", tubeTransform, 5);
        tubeRenderer.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        addChild(tubeRenderer);

        // Add Elevator Zone
        addChild(new ElevatorZone(transform));

        boostController = new BoostController(this);
        addChild(boostController);


        // UI
        buttonsPanel = new JPanel();
        GameButton unlockButton = new GameButton(
            "<html><center>" + "UNLOCK" + "<br>" + "(???)" + "</center></html>");
        
    }

    public JPanel getUI(){
        return buttonsPanel;
    };

    public CollisionBox getCollisionBox(){
        return collisionBox;
    }

    public static BoostController getBoostController(){
        return boostController;
    }

}

