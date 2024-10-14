import Engine.Core.Game;
import Scenes.GameScene;

public class Main{
    public static void main(String[] args) {
        // Create base Game Object
        Game g = new Game("Graphics Playground", 1200, 800, "./img/logo.png");

        // Main Game Scene
        GameScene s = new GameScene();
        g.addScene(s);
        
        g.startGame();
    }
}