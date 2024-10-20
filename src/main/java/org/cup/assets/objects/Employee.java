package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
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
    private float speed = 200;

    // State Machine
    private static final int IDLE = 0;
    private static final int TAKE_RESOURCE = 1;
    private static final int DELIVER_RESOURCE = 2;

    private int status = IDLE;

    // TODO: These are for debugging purposes, we'll have to set reference to the
    // floor object which will have information aboiut the machine and the delivery
    Transform machine = new Transform();
    Transform packageDropZone = new Transform();

    public Employee() {
        animator.addAnimation("idle", new Animation(PathHelper.getFilePaths(spritesFolder + "idle")));
        animator.addAnimation("run", new Animation(PathHelper.getFilePaths(spritesFolder + "run")));

        animator.setPivot(Renderer.BOTTOM_PIVOT);

        // TODO: Replace with actual objects
        machine.setPosition(new Vector(50, 720));
        packageDropZone.setPosition(new Vector(1000, 720));
    }

    @Override
    public void init() {
        addChild(animator);
        transform.setScale(new Vector(200));
        transform.setPosition(new Vector(200, 720));

        // TEST
        takeResource();
        new Timer(10000, e -> {
            if (status == IDLE) {
                takeResource();
            }
        }).start();
        ;
    }

    @Override
    public void onUpdate() {
        if (status == IDLE) {
            // Wait to get the product from the machine
            return;
        }

        Vector pos = transform.getPosition();
        double step = speed * GameManager.getDeltaTime();

        if (status == TAKE_RESOURCE) {
            if (pos.x > machine.getPosition().x) {
                // Move Towards the machine
                transform.move(Vector.LEFT.multiply(step));
            } else {
                // Take the package
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

    private void takeResource() {
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
}
