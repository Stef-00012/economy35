package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.assets.logic.Economy;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.sound.SoundManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

import java.util.HashMap;
import java.util.Random;

import javax.sound.sampled.Clip;

public class Machine extends GameNode {

    /*
     * Represents the upgrade configuration for each machine level
     */
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
            // Upgrade configuration: Level -> (Probability, Interval, Cost)
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
    private double lastAttempt; // Timestamp of the last attempt to create a resource
    private double interval; // Interval between attempts (in milliseconds)

    private boolean hasProducedResource; // Flag to check if the machine has produced a resource

    // SFX (volume value between -80 and 6.0206)
    private Clip successSfx = SoundManager.createClip(PathHelper.SFX + "Success.wav", false, 0.2);
    private Clip errorSfx = SoundManager.createClip(PathHelper.SFX + "Error.wav", false, 0.7);

    public Machine() {
        currentLevel = 1;
        loadUpgrade(upgrades.get(currentLevel));
    }

    @Override
    public void init() {
        addChild(animator);
        animator.setPivot(Renderer.BOTTOM_LEFT_PIVOT);

        addAnimationsToAnimator();

        transform.setScale(new Vector(150));
        transform.setPosition(new Vector(0, -5));
    }

    private void addAnimationsToAnimator() {
        String spritesFolder = PathHelper.sprites + "machine\\1\\";

        // Animation for failure (when resource creation fails)
        Animation failAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "fail"), false);
        failAnimation.addLastFrameListener(() -> {
            animator.play("loading"); // Transition to loading animation after failure
        });

        // Animation for successful resource creation
        Animation packageOutAnimation = new Animation(PathHelper.getFilePaths(spritesFolder + "success\\package-out"),
                false);
        packageOutAnimation.addLastFrameListener(() -> {
            animator.play("success-idle"); // Transition to idle after package-out animation
        });

        // Add animations to the animator
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
        SoundManager.playClip(errorSfx);
    }

    public void success() {
        animator.play("success-package-out");
        hasProducedResource = true;
        SoundManager.playClip(successSfx);
    }

    /**
     * Attempts to create a resource based on the machine's probability.
     * If successful, triggers the success method, else triggers the error method.
     */
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

    /**
     * Checks if the machine has produced a resource.
     *
     * @return true if the machine has produced a resource, false otherwise
     */
    public boolean hasResource() {
        return hasProducedResource;
    }
/**
     * Resets the machine, removing any produced resource and setting it back to a loading state
     */
    public void takeResource() {
        animator.play("loading");
        hasProducedResource = false;
        lastAttempt = System.currentTimeMillis(); // Reset the last attempt time
    }

    /**
     * Upgrades the machine to the next level, if possible.
     * This will change the machine's properties based on the next level's upgrade.
     */
    public void upgrade() {
        if (!canUpgrade()) return; // If the machine cannot be upgraded, exit
        currentLevel++; // Increment the level
        MachineUpgrade nextUpgrade = upgrades.get(currentLevel); // Get the next upgrade
        loadUpgrade(nextUpgrade); // Apply the next upgrade
    }

    /**
     * Checks if the machine can be upgraded to the next level.
     *
     * @return true if the machine can be upgraded, false otherwise
     */
    public boolean canUpgrade() {
        return getNextUpgrade() != null;
    }

    /**
     * Retrieves the upgrade configuration for the next level, if available.
     *
     * @return the upgrade configuration for the next level, or null if no more upgrades are available
     */
    public MachineUpgrade getNextUpgrade() {
        return upgrades.get(currentLevel + 1);
    }

    /**
     * Loads the properties for the next upgrade, such as probability, interval, and cost.
     *
     * @param upgrade the upgrade configuration to be applied
     */
    private void loadUpgrade(MachineUpgrade upgrade) {
        Economy.spendMoney(upgrade.cost); 
        probability = upgrade.probability; 
        interval = upgrade.interval; 
    }

    /**
     * Gets the current level of the machine.
     *
     * @return the current level of the machine
     */
    public int getLevel() {
        return currentLevel;
    }

    /**
     * Retrieves the current upgrade configuration of the machine.
     *
     * @return the current machine upgrade configuration
     */
    public MachineUpgrade getCurrentUpgrade() {
        return upgrades.get(currentLevel);
    }

}
