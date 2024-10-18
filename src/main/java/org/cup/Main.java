package org.cup;

import org.cup.assets.scenes.MainScene;
import org.cup.engine.core.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // maintain a 16:9 screen ratio
        int width = 1400;
        int height = width / 16 * 9;

        Game game = new Game("Graphics Playground", width, height, "./logo.png", 1f, false);

        MainScene m = new MainScene();
        game.addScene(m);
    }
}