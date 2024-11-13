package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class GameOverText extends GameNode {
    private SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "tax-time.png", transform, 10);

    private Vector targetScale;

    public GameOverText(){
        Vector ratio = new Vector(1280, 720);
        targetScale = ratio;
        transform.setScale(ratio.divide(2));
        transform.setPosition(GameManager.game.getWindowDimentions().divide(2));
        
        addChild(sr);
    }

    @Override
    public void onUpdate() {
        transform.setScale(Vector.lerp(transform.getScale(), targetScale, GameManager.getDeltaTime() * 10));
    }
}
