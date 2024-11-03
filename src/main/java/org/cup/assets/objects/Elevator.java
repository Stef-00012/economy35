package org.cup.assets.objects;

import org.cup.assets.UI.Floor;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Elevator extends GameNode implements KeyListener {
    Rectangle elevatorCab;
    Player player = null; 
    
    private Floor targetFloor = null; 
    private int currentFloor;

    public void init(){
        // ! 39px is the height of the titlebar on Windows (DO NOT REMOVE)
        // ! 20px is the height of the sidewalk (DO NOT REMOVE)
        transform.setPosition(new Vector(0, GameManager.game.getHeight() - 39 - 20));

        // graphic part of the cab
        elevatorCab = new Rectangle(120, 175, 0, 0, 1, new Color(100,100,100));
        elevatorCab.sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
        elevatorCab.transform.setParentTransform(transform);
        addChild(elevatorCab);

        // Elevator logic
        targetFloor = Building.get().getInventory();
        currentFloor = 0;
        updateFloorUI();
        GameManager.game.addKeyListener(this);
    
        // player
        player = new Player();
        player.transform.setParentTransform(transform);
        addChild(player);
    }

    public void onUpdate(){
        if (targetFloor == null)
            return;

        Vector lepedPos = Vector.lerp(transform.getPosition(), targetFloor.transform.getPosition(), GameManager.getDeltaTime() * 10);
        transform.setPosition(new Vector(transform.getPosition().x, lepedPos.y));
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
    
        if (keyCode == KEYCODE_ARROW_UP){
            // go up 1 floor (175px)
            Floor upFloor = Building.get().getUpFloor(currentFloor);
            if(upFloor != null){
                targetFloor = upFloor;
                updateFloorUI();
                currentFloor++;
            }
            
        } else if (keyCode == KEYCODE_ARROW_DOWN){
            // go down 1 floor (175px)
            Floor downFloor = Building.get().getDownFloor(currentFloor);
            if(downFloor != null){
                targetFloor = downFloor;
                updateFloorUI();
                currentFloor--;
            }
        }
    }

    private void updateFloorUI(){
        MainScene.getStatsPanel().updateFloorPanel(targetFloor);
    }
}
