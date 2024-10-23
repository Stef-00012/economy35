package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;
import org.cup.engine.core.nodes.components.defaults.Transform;
import javax.swing.*;

public class Employee extends GameNode {
    private Animator animator = new Animator(transform, 1);
    private String spritesFolder = PathHelper.sprites + "placeholder-guy\\";
    private float speed = 100;

    // State Machine
    public static final int IDLE = 0;
    public static final int TAKE_RESOURCE = 1;
    public static final int DELIVER_RESOURCE = 2;

    private int status = IDLE;

    private Machine roomMachine;
    Transform packageDropZone;

    public Employee(Room room) {
        animator.addAnimation("idle", new Animation(PathHelper.getFilePaths(spritesFolder + "idle")));
        animator.addAnimation("run", new Animation(PathHelper.getFilePaths(spritesFolder + "run")));

        animator.setPivot(Renderer.BOTTOM_PIVOT);

        roomMachine = room.getMachine();

        packageDropZone = room.getDropZone();
    }

    @Override
    public void init() {
        addChild(animator);
        transform.setScale(new Vector(50));
        transform.setPosition(new Vector(200, 0));
    }

    @Override
    public void onUpdate() {
        if (status == IDLE) {
            if(roomMachine.hasResource()){
                takeResource();
            }
            return;
        }

        Vector pos = transform.getPosition();
        double step = speed * GameManager.getDeltaTime();

        if (status == TAKE_RESOURCE) {
            if (pos.x > roomMachine.transform.getPosition().x) {
                // Move Towards the machine
                transform.move(Vector.LEFT.multiply(step));
            } else {
                // Take the package
                roomMachine.takeResource();
                deliverResource();
            }
            return;
        }

        if (status == DELIVER_RESOURCE) {
            if (pos.x < packageDropZone.getPosition().x) {
                // Move Towards the machine
                transform.move(Vector.RIGHT.multiply(step));
            } else {
                // Drop the package
                idle();
            }
            return;
        }

    }

    public void flipCharacter() {
        transform.setScale(Vector.multiplyVec(new Vector(-1, 1), transform.getScale()));
    }

    public void takeResource() {
        if(status != IDLE) return;
        flipCharacter();
        status = TAKE_RESOURCE;
        animator.play("run");
    }

    private void deliverResource() {
        status = DELIVER_RESOURCE;
        flipCharacter();
    }

    private void idle() {
        status = IDLE;
        animator.play("idle");
    }

    public int getStatus(){
        return status;
    }

}
