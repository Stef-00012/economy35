package Engine.Core;

public class GameManager {
    public static Game game;
    public static GraphicsManager graphicsManager;

    public static void initialize(Game game, GraphicsManager graphicsManager){
        GameManager.game = game;
        GameManager.graphicsManager = graphicsManager;
    }
}
