package org.cup;

import org.cup.assets.scenes.CollisionsTest;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.core.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String logo = System.getProperty("user.dir")
            + "\\src\\main\\java\\org\\cup\\logo.png";
        Game game = new Game("Graphics Playground", 1280, 720, logo, 1f, false);

        MainScene main = new MainScene();
        //game.addScene(main);
        game.addScene(new CollisionsTest());
    }
}