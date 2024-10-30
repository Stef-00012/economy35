package org.cup.assets.objects;

import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Elevator extends GameNode implements KeyListener {

    Rectangle elevatorCab;
    Player player = null; 
    
    private Vector targetPos = null; 

    public void init(){
        // ! 39px is the height of the titlebar on Windows (DO NOT REMOVE)
        // ! 20px is the height of the sidewalk (DO NOT REMOVE)
        transform.setPosition(new Vector(0, GameManager.game.getHeight() - 39 - 20));


        // graphic part of the cab
        elevatorCab = new Rectangle(120, 175, 0, 0, 1, Color.BLACK);
        elevatorCab.sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        elevatorCab.transform.setParentTransform(transform);
        addChild(elevatorCab);


        // elevator logic
        targetPos = transform.getPosition();
        GameManager.game.addKeyListener(this);
    
        // player
        player = new Player();
        player.transform.setParentTransform(transform);
        addChild(player);
    }

    public void onUpdate(){
        if (targetPos == null)
            return;

        transform.setPosition(Vector.lerp(transform.getPosition(), targetPos, GameManager.getDeltaTime() * 10));
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        final int KEYCODE_ARROW_UP = 38;
        final int KEYCODE_ARROW_DOWN = 40;

        int keyCode = e.getKeyCode();
        Vector movement = new Vector(0, 175);
    
        if (keyCode == KEYCODE_ARROW_UP){
            // go up 1 floor (175px)
            targetPos = targetPos.subtract(movement);
        } else if (keyCode == KEYCODE_ARROW_DOWN){
            // go down 1 floor (175px)
            targetPos = targetPos.add(movement);
        }
    }
}
