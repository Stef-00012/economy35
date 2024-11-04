package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.assets.logic.Economy;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

import java.util.HashMap;
import java.util.Random;

public class Machine extends GameNode {
    public class MachineUpgrade {
        public int probability;
        public int interval;
        public int cost;

        public MachineUpgrade(int probability, int interval, int cost) {
            this.probability = probability;
            this.interval = interval;
            this.cost = cost;
        }
    }

    private int currentLevel;

    private HashMap<Integer, MachineUpgrade> upgrades = new HashMap<>() {
        {
            put(1, new MachineUpgrade(40, 3000, 0));
            put(2, new MachineUpgrade(40, 2000, 10));
            put(3, new MachineUpgrade(75, 2000, 150));
            put(4, new MachineUpgrade(90, 2000, 300));
            put(5, new MachineUpgrade(100, 2000, 600));
            put(6, new MachineUpgrade(100, 1000, 1200));
        }
    };
    private final Animator animator = new Animator(transform, 1);

    private Random randomGen = new Random();

    private int probability = 0; // Probability to drop the resource

    private double lastAttempt; // The time passed since the last time it has been chec
    private double interval; // Time of the interval(in milliseconds)

    private boolean hasProducedResource;

    public Machine(){
        currentLevel = 1;
        loadUpgrade(upgrades.get(currentLevel));
    }

    @Override
    public void init() {
        addChild(animator);
        animator.setPivot(Renderer.BOTTOM_LEFT_PIVOT);

        addAnimationsToAnimator();

        transform.setScale(new Vector(150));
    }

    private void addAnimationsToAnimator() {

        String spritesFolder = PathHelper.sprites + "machine\\1\\";

        Animation failAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "fail"), false);
        failAnimation.addLastFrameListener(() -> {
            animator.play("loading");
        });

        Animation packageOutAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "success\\package-out"),
                false);
        packageOutAnimation.addLastFrameListener(() -> {
            animator.play("success-idle");
        });

        animator.addAnimation("fail", failAnimation);
        animator.addAnimation("loading", new Animation(PathHelper.getFilePaths(spritesFolder + "loading")));
        animator.addAnimation("success-package-out", packageOutAnimation);
        animator.addAnimation("success-idle", new Animation(PathHelper.getFilePaths(spritesFolder + "success\\idle")));
    }

    @Override
    public void onUpdate() {
        if (!hasResource() && System.currentTimeMillis() - lastAttempt > interval) {
            lastAttempt = System.currentTimeMillis();
            attemptToCreateResource();
        }
    }

    public void error() {
        animator.play("fail");
    }

    public void success() {
        animator.play("success-package-out");
        hasProducedResource = true;
    }

    public void attemptToCreateResource() {
        // Drop the resoure randomly based on the value of the probability, the higher
        // it gets the more probability there is to get a resource
        int r = randomGen.nextInt(100);
        if (r <= probability) {
            success();
        } else {
            error();
        }
    }

    // return if the machine ha a resource
    public boolean hasResource() {
        return hasProducedResource;
    }

    // Reset the machine taking away its resource
    public void takeResource() {
        animator.play("loading");
        hasProducedResource = false;
        lastAttempt = System.currentTimeMillis();
    }

    public void upgrade(){
        if(!canUpgrade()) return;
        currentLevel++;
        MachineUpgrade nextUpgrade = upgrades.get(currentLevel);
        loadUpgrade(nextUpgrade);
    }

    public boolean canUpgrade(){
        return getNextUpgrade() != null;
    }

    public MachineUpgrade getNextUpgrade(){
        return upgrades.get(currentLevel + 1);
    }

    private void loadUpgrade(MachineUpgrade upgrade){
        Economy.spendMoney(upgrade.cost);
        probability = upgrade.probability;
        interval = upgrade.interval;
    }

    public int getLevel(){
        return currentLevel;
    }

    public MachineUpgrade getCurrentUpgrade(){
        return upgrades.get(currentLevel);
    }

}
