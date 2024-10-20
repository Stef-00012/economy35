package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

public class Employee extends GameNode {
    private Animator animator = new Animator(transform, 1);
    private String spritesFolder = PathHelper.sprites + "placeholder-guy\\";

    public Employee(){
        animator.addAnimation("idle", new Animation(PathHelper.getFilePaths(spritesFolder + "idle")));
        animator.addAnimation("run", new Animation(PathHelper.getFilePaths(spritesFolder + "run")));

        animator.setPivot(Renderer.BOTTOM_PIVOT);
    }

    @Override
    public void init() {
        addChild(animator);
        transform.setScale(new Vector(200));
        transform.setPosition(new Vector(200, 720));
    }
}
