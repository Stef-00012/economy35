package org.cup;

import org.cup.assets.scenes.MainScene;
import org.cup.assets.scenes.TestScene;
import org.cup.engine.core.Game;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Game game = new Game("Graphics Playground", 1280, 720, "./logo.png", 1f, false);

        MainScene main = new MainScene();
        TestScene test = new TestScene();
        game.addScene(test);
    }
}