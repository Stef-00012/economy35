package org.cup.assets.objects;

import javax.management.monitor.GaugeMonitor;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;
import org.cup.engine.core.nodes.components.defaults.Transform;
import org.cup.assets.logic.CustomerThread;

public class Customer extends GameNode {
    private Animator animator = new Animator(transform, 2);
    private float speed = 100;

    private int positionInQueue;

    // State Machine
    private int status = TAKE_RESOURCE;
    public static final int TAKE_RESOURCE = 0;
    public static final int WAITING_FOR_RESOURCE = 1;
    public static final int GO_AWAY = 2;

    private CustomerThread thread;

    // TODO: Replace with actual object reference
    private Transform seller = new Transform();

    public Customer() {
        String spritesFolder = PathHelper.sprites + "customer\\";

        animator.addAnimation("idle", new Animation(PathHelper.getFilePaths(spritesFolder + "idle")));
        animator.addAnimation("walk", new Animation(PathHelper.getFilePaths(spritesFolder + "walk")));
        animator.addAnimation("walk-package", new Animation(PathHelper.getFilePaths(spritesFolder + "walk-package")));

        animator.setPivot(Renderer.BOTTOM_PIVOT);

        seller.setPosition(new Vector(500, GameManager.game.getWindowDimentions().y));

        thread = new CustomerThread(this, Building.get().getInventory());
    }

    @Override
    public void init() {
        addChild(animator);
        transform.setScale(new Vector(51, 58).multiply(2));
        transform.setPosition(new Vector(200, 0));
    }

    @Override
    public void onEnable() {
        transform.setPosition(GameManager.game.getWindowDimentions().add(Vector.RIGHT.multiply(10)));

        if (transform.getScale().x > 0) {
            animator.flip();
        }
        takeResource();
    }

    @Override
    public void onUpdate() {
        double step = speed * GameManager.getDeltaTime();

        Vector pos = transform.getPosition();

        if (status == WAITING_FOR_RESOURCE || status == TAKE_RESOURCE) {
            double waitingPos = seller.getPosition().x;
            waitingPos += Math.abs(transform.getScale().x) / 3 * positionInQueue;
            if (pos.x > waitingPos) {
                // Move Towards the store
                transform.move(Vector.LEFT.multiply(step));
            }

            if (status == TAKE_RESOURCE) {
                if (pos.x <= waitingPos) {
                    animator.play("idle");
                    status = WAITING_FOR_RESOURCE;
                    thread.takePackage(1);
                }
                return;
            }
        }

        if (status == GO_AWAY) {
            if (pos.x < GameManager.game.getWidth() + 20) {
                // Move Towards the machine
                transform.move(Vector.RIGHT.multiply(step));
            } else {
                disable();
            }
            return;
        }

    }

    public void takeResource() {
        Building.get().getMarket().joinQueue(this);

        status = TAKE_RESOURCE;
        animator.play("walk");
    }

    public void goAway() {
        status = GO_AWAY;
        animator.flip();
        animator.play("walk-package");

        Building.get().getMarket().moveQueue(positionInQueue);
    }

    @Override
    public void onDisable() {
        ((CustomerSpawner) getParent()).addBackToQueue(this);
    }

    public void setPositionInQueue(int value) {
        positionInQueue = value;
    }
}
