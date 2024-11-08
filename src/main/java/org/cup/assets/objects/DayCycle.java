package org.cup.assets.objects;

import java.awt.Font;

import javax.swing.JLabel;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.GameLabel;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class DayCycle extends GameNode {
    private double minutesInRealLife = 1; // One minute irl
    private double minutesInGame = 480; // One minute irl = 60 min in game
    private double timeInMinutesGame = 0; // Keeps track of in-game minutes

    private String[] sprites = PathHelper.getFilePaths(PathHelper.sprites + "day-night-cycle\\");
    SpriteRenderer sr = new SpriteRenderer(sprites[0], transform, -10);

    private GameLabel timeLabel;

    public DayCycle(){
        transform.setScale(GameManager.game.getWindowDimentions());

        addChild(sr);
        sr.setPivot(Renderer.TOP_LEFT_PIVOT);

        timeInMinutesGame = 0;

        // UI
        timeLabel = new GameLabel("Time: 0h");
        timeLabel.setFontSize(20);
        timeLabel.setBounds(10, 10, 240, 20);
        //GameManager.game.addUIElement(timeLabel);
    }

    @Override
    public void onUpdate() {
        /* 
        // Calculate how many minutes in the game should elapse based on delta time
        double deltaTimeInMinutes = GameManager.getDeltaTime() / 60.0;
        double gameMinutesElapsed = deltaTimeInMinutes * (minutesInGame / minutesInRealLife);

        timeInMinutesGame += gameMinutesElapsed;

        timeInMinutesGame = timeInMinutesGame % (24 * 60);

        // Calculate the in-game hour
        int currentHour = (int) (timeInMinutesGame / 60);

        timeLabel.setText("Time: " + currentHour + "h");

        // Determine the sprite index based on current hour and sprite count
        int hoursPerSprite = 24 / sprites.length;
        int spriteIndex = currentHour / hoursPerSprite;

        // Update the sprite
        sr.setSprite(sprites[spriteIndex]);

        */
    }
}
