package org.cup.assets.objects;

import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.managers.sound.SoundManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

import javax.sound.sampled.Clip;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;

public class TaxText extends GameNode {
    private SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "tax-time.png", transform, 10);

    private float speed = 5;

    private Vector rightPos;
    private Vector leftPos;
    private Vector centerPos;

    private final int RIGHT_STATE = 0;
    private final int CENTER_STATE = 1;
    private final int LEFT_STATE = 2;
    private final int TMP = 3;

    private int state = LEFT_STATE;

    private Vector targetPos;

    private Clip taxAlarm = SoundManager.createClip(PathHelper.music + "TaxTime.wav", false, 1);

    public TaxText() {
        transform.setScale(new Vector(1280, 720).divide(2));

        addChild(sr);

        int winHeight = (GameManager.game.getHeight() - 39) / 2 + 100;

        rightPos = new Vector(GameManager.game.getWidth() + transform.getScale().x, winHeight);
        centerPos = new Vector(GameManager.game.getWidth() / 2, winHeight);
        leftPos = new Vector(-transform.getScale().x, winHeight);

        targetPos = leftPos;

        disable();
    }
    public void show(){
        transform.setPosition(leftPos);
        state = LEFT_STATE;
        SoundManager.playClip(taxAlarm);
        centerState();
    }

    @Override
    public void onUpdate() {
        Vector lerpedPos = Vector.lerp(transform.getPosition(), targetPos, GameManager.getDeltaTime() * speed);
        transform.setPosition(lerpedPos);

        if (transform.getPosition().getX() <= targetPos.getX() + 10
                && transform.getPosition().getX() >= targetPos.getX() - 10) {
            if (state == RIGHT_STATE) {
                disable();
            }

            if (state == CENTER_STATE) {
                state = TMP;
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Debug.err(e.getMessage());
                    }
                    rightState();
                }).start();

            }
        }
    }

    private void centerState() {
        state = CENTER_STATE;
        targetPos = centerPos;
    }

    private void leftState() {
        state = LEFT_STATE;
        targetPos = leftPos;
    }

    private void rightState() {
        state = RIGHT_STATE;
        targetPos = rightPos;
    }
}