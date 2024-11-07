package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;
import org.cup.engine.core.nodes.components.defaults.Transform;
import org.cup.assets.logic.CustomerThread;
import org.cup.assets.logic.Economy;

public class Customer extends GameNode {
    private Animator animator = new Animator(transform, 3);
    private float speed = 100;

    private int positionInQueue;

    // State Machine
    private int status = TAKE_RESOURCE;
    public static final int TAKE_RESOURCE = 0;
    public static final int WAITING_FOR_RESOURCE = 1;
    public static final int GO_AWAY = 2;

    private CustomerThread thread;

    private Transform seller;

    public Customer() {
        String spritesFolder = PathHelper.sprites + "customer\\";

        animator.addAnimation("idle", new Animation(PathHelper.getFilePaths(spritesFolder + "idle")));
        animator.addAnimation("walk", new Animation(PathHelper.getFilePaths(spritesFolder + "walk")));
        animator.addAnimation("walk-package", new Animation(PathHelper.getFilePaths(spritesFolder + "walk-package")));

        animator.setPivot(Renderer.BOTTOM_PIVOT);

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
        transform.setPosition(GameManager.game.getWindowDimentions().add(new Vector(10, -15)));
        animator.setLayer(2);
        takeResource();

        seller = Building.get().getMarket().transform;
    }

    @Override
    public void onUpdate() {
        double step = speed * GameManager.getDeltaTime();

        Vector pos = transform.getPosition();

        if (status == WAITING_FOR_RESOURCE || status == TAKE_RESOURCE) {
            double waitingPos = seller.getPosition().x + 70;
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
            if (pos.x < GameManager.game.getWidth() + transform.getScale().x / 2) {
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
        Building.get().getMarket().playSellAnimation();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        status = GO_AWAY;
        animator.play("walk-package");
        animator.setLayer(4);
        //transform.setPosition(transform.getPosition().subtract(Vector.DOWN.multiply(15)));

        Economy.setBalance(Economy.getBalance() + Economy.getProductValue()); // update UI counters
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
