package org.cup.assets.objects;
import org.cup.assets.PathHelper;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class DayCycle extends GameNode {
    public DayCycle(){
        transform.setScale(GameManager.game.getWindowDimentions());

        SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "day-night-cycle\\Day_Night_cycle-8.png", transform, -10);
        addChild(sr);
        sr.setPivot(Renderer.TOP_LEFT_PIVOT);
    }
}
