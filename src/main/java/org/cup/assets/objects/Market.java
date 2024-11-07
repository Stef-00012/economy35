package org.cup.assets.objects;

import java.util.ArrayList;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

public class Market extends GameNode {
    private Animator animator = new Animator(transform, 2);

    private ArrayList<Customer> customersQueue = new ArrayList<>();

    public Market() {
        transform.setPosition(new Vector(630, GameManager.game.getHeight() - 59));
        transform.setScale(new Vector(240, 175));

        initAnimator();
    }

    private void initAnimator() {
        animator.setPivot(Renderer.BOTTOM_PIVOT);
        addChild(animator);
    }

    @Override
    public void init() {
        // Define the sprites folder path for the animations
        String spritesFolder = PathHelper.sprites + "shop\\1\\";

        // Create a sell animation that transitions back to idle after it finishes
        Animation sellAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "sell"), false);
        sellAnimation.addLastFrameListener(() -> {
            animator.play("idle"); // Play idle animation after sell animation
        });

        // Create an idle animation with a specified frame delay
        Animation idleAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "idle"));
        idleAnimation.setTimeBetweenFrames(200); // Adjust the time between frames to slow down the idle animation

        // Add the animations to the animator
        animator.addAnimation("idle", idleAnimation);
        animator.addAnimation("sell", sellAnimation);
    }

    /**
     * Adds a customer to the market's queue and sets their position in the queue.
     * 
     * @param c The customer to add
     */
    public void joinQueue(Customer c) {
        c.setPositionInQueue(customersQueue.size()); // Set the customer's queue position based on the current size
        customersQueue.add(c); // Add the customer to the queue
    }

    /**
     * Moves the customers in the queue after one is served.
     * 
     * @param servedIndex The index of the served customer to be removed
     */
    public void moveQueue(int servedIndex) {
        customersQueue.remove(servedIndex); // Remove the served customer from the queue
        // Update the position of each customer in the queue
        for (int i = 0; i < customersQueue.size(); i++) {
            customersQueue.get(i).setPositionInQueue(i); // Update their position based on their index
        }
    }

    public void playSellAnimation() {
        animator.play("sell");
    }
}
