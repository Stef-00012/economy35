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
        transform.setPosition(new Vector(620, GameManager.game.getHeight() - 59));
        transform.setScale(new Vector(288, 175));
        
        initAnimator();
    }

    private void initAnimator(){
        animator.setPivot(Renderer.BOTTOM_PIVOT);
        addChild(animator);
    }

    @Override
    public void init() {
        String spritesFolder = PathHelper.sprites + "shop\\";

        Animation sellAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "sell"), false);
        sellAnimation.addLastFrameListener(() -> {
            animator.play("idle");
        });

        Animation idleAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "idle"));
        idleAnimation.setTimeBetweenFrames(200);

        animator.addAnimation("idle", idleAnimation);
        animator.addAnimation("sell", sellAnimation);
    }

    public void joinQueue(Customer c) {
        c.setPositionInQueue(customersQueue.size());
        customersQueue.add(c);
    }

    public void moveQueue(int servedIndex) {
        customersQueue.remove(servedIndex);
        for (int i = 0; i < customersQueue.size(); i++) {
            customersQueue.get(i).setPositionInQueue(i);
        }
    }

    public void playSellAnimation(){
        animator.play("sell");
    }
}
