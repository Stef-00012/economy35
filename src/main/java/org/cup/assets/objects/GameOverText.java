package org.cup.assets.objects;

import javax.sound.sampled.Clip;

import org.cup.assets.PathHelper;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.managers.sound.SoundManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class GameOverText extends GameNode {
    private SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "bankrupt.png", transform, 10);

    private Vector targetScale;

    Clip gameOverTheme = SoundManager.createClip(PathHelper.music + "GameOver.wav", true);

    public GameOverText(){
        Vector ratio = new Vector(1280, 720);
        targetScale = ratio.divide(2);
        transform.setScale(ratio.divide(3));
        transform.setPosition(GameManager.game.getWindowDimentions().divide(2).add(new Vector(-300, 0)));
        
        addChild(sr);
    }

    @Override
    public void onEnable() {
        SoundManager.stopClip(MainScene.getTaxesMusic());
        SoundManager.playClip(gameOverTheme);
    }

    @Override
    public void onUpdate() {
        transform.setScale(Vector.lerp(transform.getScale(), targetScale, GameManager.getDeltaTime() * 10));
    }
}
