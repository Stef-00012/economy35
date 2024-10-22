package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.assets.logic.MachineThread;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class Machine extends GameNode {
    private final String normalSprite = PathHelper.getSpritePath("machine\\machine1.png"); 
    private final String errorSprite = PathHelper.getSpritePath("machine\\machineError.png"); 

    private SpriteRenderer spriteRenderer = new SpriteRenderer(normalSprite, transform, 5);

    @Override
    public void init() {
        addChild(spriteRenderer);
        spriteRenderer.setPivot(Renderer.BOTTOM_LEFT_PIVOT);

        transform.setScale(new Vector(200));
        transform.setPosition(new Vector(0, 720));

        // Create Thread
        new MachineThread(this).start();
    }

    public void error(){
        spriteRenderer.setSprite(errorSprite);
    }

    public void normal(){
        spriteRenderer.setSprite(normalSprite);
    }
}
