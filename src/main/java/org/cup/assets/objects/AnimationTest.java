package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

public class AnimationTest extends GameNode {
    Animator animator = new Animator(transform, 5);

    @Override
    public void init() {
        String firstAnimationPath = PathHelper.sprites + "test-animation-1\\";
        animator.addAnimation("idle", new Animation(new String[]{
            firstAnimationPath + "1.png",
            firstAnimationPath + "2.png",
            firstAnimationPath + "3.png",
        }));

        animator.addAnimation("reverse", new Animation(new String[]{
            firstAnimationPath + "3.png",
            firstAnimationPath + "2.png",
            firstAnimationPath + "1.png",
        }));

        addChild(animator);
        transform.setScale(new Vector(200, 200));
        transform.setPosition(new Vector(400, 200));
    }

    @Override
    public void onEnable() {
        Debug.log("Animation test enabled");

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            animator.play("reverse");
        }).start();
    }
}
