package org.cup.assets.objects;

import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import org.cup.assets.PathHelper;
import org.cup.engine.Vector;

public class TaxText extends GameNode {
    private SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "tax-time.png", transform, 10);

    private float speed = 100;

    private Vector rightPos;
    private Vector leftPos;
    private Vector centerPos;

    private final int RIGHT_STATE = 0;
    private final int CENTER_STATE = 1;
    private final int LEFT_STATE = 2;

    private int state = RIGHT_STATE;

    private Vector targetPos;

    public TaxText() {
        transform.setPosition(rightPos);
        transform.setScale(new Vector(100));

        addChild(sr);

        int winHeight = (GameManager.game.getHeight() - 39) / 2;

        rightPos = new Vector(GameManager.game.getWidth() + transform.getScale().x, winHeight);
        centerPos = new Vector(0, winHeight);
        leftPos = new Vector(-transform.getScale().x, winHeight);
        disable();
    }

    @Override
    public void onEnable() {
        transform.setPosition(rightPos);
        centerState();
    }

    @Override
    public void onUpdate() {
        Vector lepedPos = Vector.lerp(transform.getPosition(), targetPos, GameManager.getDeltaTime() * speed);
        transform.setPosition(new Vector(transform.getPosition().x, lepedPos.y));

        if (transform.getPosition().getX() == targetPos.getX()) {
            if (state == LEFT_STATE) {
                disable();
            }

            if (state == CENTER_STATE) {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Debug.err(e.getMessage());
                    }
                    leftState();
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
}