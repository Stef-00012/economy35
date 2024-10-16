import assets.scenes.MainScene;
import engine.core.*;
public class Main {
    public static void main(String[] args) {
        Game game = new Game("Graphics Playground", 1200, 800, "./logo.png", 1f, false);

        MainScene m = new MainScene();
        game.addScene(m);
        m.enable();
    }   
}
