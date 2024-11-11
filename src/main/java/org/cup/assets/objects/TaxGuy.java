package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.assets.logic.Economy;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

// the guy who will take a tax every night  
public class TaxGuy extends GameNode {
    private Animator animator = new Animator(transform, 5);
    private Vector outPosition;
    private Vector restPosition; // Where the guy should stay while talking to you

    private int state = 0;
    private final int OUT = 0; // The guy is inactive
    private final int WALK_IN = 1; // The guy is walingin
    private final int WALK_OUT = 2; // The guy is walking out
    private final int IDLE = 3; // The guy is staying still

    private float speed = 100;

    public TaxGuy() {
        transform.setScale(new Vector(102, 116));

        // positions
        outPosition = GameManager.game.getWindowDimentions().add(new Vector(transform.getScale().x, -20));
        restPosition = GameManager.game.getWindowDimentions().add(new Vector(-500, -20));

        // Animator
        animator.setPivot(Renderer.BOTTOM_PIVOT);
        Animation idle = new Animation(PathHelper.getFilePaths(PathHelper.sprites + "tax-guy\\idle"));
        Animation walk = new Animation(PathHelper.getFilePaths(PathHelper.sprites + "tax-guy\\walk"));
        idle.setLoopType(Animation.PING_PONG_LOOP);

        addChild(animator);

        animator.addAnimation("idle", idle);
        animator.addAnimation("walk", walk);

        disable();
    }

    public void show() {
        transform.setPosition(outPosition);

        walkInState();

        if (transform.getScale().x < 0) {
            animator.flipHorizontally();
        }
    }

    public void onUpdate() {
        double step = speed * GameManager.getDeltaTime();
        // Debug.log("Scale: " + transform.getScale());
        // Debug.log("Pos: " + transform.getPosition());

        if (state == WALK_IN) {
            if (transform.getPosition().x > restPosition.x) {
                transform.move(Vector.LEFT.multiply(step));
            } else {
                idleState();

                new Thread(() -> {
                    Debug.warn("NEW THREAD: TAX GUY");

                    takeTaxes();
                }).start();
            }
            return;
        }

        if (state == WALK_OUT) {
            if (transform.getPosition().x < outPosition.x) {
                transform.move(Vector.RIGHT.multiply(step));
            } else {
                outState();
            }
            return;
        }
    }

    private void idleState() {
        state = IDLE;
        animator.play("idle");
    }

    private void walkInState() {
        state = WALK_IN;
        animator.play("walk");
    }

    private void walkOutState() {
        state = WALK_OUT;
        animator.play("walk");
        animator.flipHorizontally();
    }

    private void outState() {
        state = OUT;
        disable();
        MainScene.resumeDayNightCycle();
    }

    private void takeTaxes() {
        try {
            Thread.sleep(1000);
            if (Economy.takeTaxes()) {
                Thread.sleep(1000);
                walkOutState();
            } else {
                // Failed to pay taxes
            }
        } catch (InterruptedException e) {
            Debug.err(e.getMessage());
        }
    }
}
