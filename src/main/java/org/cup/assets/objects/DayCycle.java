package org.cup.assets.objects;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.GameLabel;
import org.cup.engine.Utils;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.Animation;
import org.cup.engine.core.nodes.components.defaults.Animator;

public class DayCycle extends GameNode {
    private double minutesInRealLife = 1; // One minute irl
    private double minutesInGame = 480; // One minute irl = 60 min in game (480 is usually ok)
    private double timeInMinutesGame = 0; // Keeps track of in-game minutes

    Animator animator = new Animator(transform, -10);

    private GameLabel timeLabel;

    private boolean isNight;

    public DayCycle() {
        transform.setScale(GameManager.game.getWindowDimentions());

        timeInMinutesGame = 0;
        isNight = false;

        // Animator
        String[] sprites = PathHelper.getFilePaths(PathHelper.sprites + "day-night-cycle\\");
        Animation dayToNight = new Animation(sprites, false);

        Utils.reverseArray(sprites);
        Animation nightToDay = new Animation(sprites, false);

        animator.addAnimation("night-to-day", nightToDay);
        animator.addAnimation("day-to-night", dayToNight);

        addChild(animator);
        animator.setPivot(Renderer.TOP_LEFT_PIVOT);

        // UI
        timeLabel = new GameLabel("Time: 0h");
        timeLabel.setFontSize(20);
        timeLabel.setBounds(10, 10, 240, 20);
        GameManager.game.addUIElement(timeLabel);
    }

    @Override
    public void onUpdate() {
        // Calculate how many minutes in the game should elapse based on delta time
        double deltaTimeInMinutes = GameManager.getDeltaTime() / 60.0;
        double gameMinutesElapsed = deltaTimeInMinutes * (minutesInGame / minutesInRealLife);

        timeInMinutesGame += gameMinutesElapsed;

        timeInMinutesGame = timeInMinutesGame % (24 * 60);

        // Calculate the in-game hour
        int currentHour = (int) (timeInMinutesGame / 60);

        timeLabel.setText("Time: " + currentHour + "h");

        if (!isNight && currentHour == 20) {
            animator.play("day-to-night");
            timeLabel.setForeground(Color.WHITE);
            isNight = true;
        }

        if (isNight && currentHour == 7) {
            animator.play("night-to-day");
            timeLabel.setForeground(Color.BLACK);
            isNight = false;
        }
    }

}
