package Engine.Core;

public class GameManager {
    public static Game game;
    public static GraphicsManager graphicsManager;

    /**
     * Initialize the static variables
     * @param game - The Game (JFrame) instance
     * @param graphicsManager - The Graphics Manager instance
     */
    public static void initialize(Game game, GraphicsManager graphicsManager){
        GameManager.game = game;
        GameManager.graphicsManager = graphicsManager;
    }
}
