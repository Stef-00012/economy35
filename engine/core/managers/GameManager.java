package engine.core.managers;

import engine.core.Game;
import engine.core.managers.graphics.GraphicsManager;

/**
 * The {@code GameManager} is a central class responsible for managing core game functionalities, 
 * such as timekeeping and interaction with the {@code GraphicsManager}.
 * <p>
 * It handles the global game state and provides access to important systems 
 * like the {@code GraphicsManager} and the game window (JFrame).
 * </p>
 */
public class GameManager {
    public static Game game;
    public static GraphicsManager graphicsManager;

    private static double deltaTime;
    private static long lastUpdate;

    /**
     * Initializes the static variables of the {@code GameManager}.
     * This method is typically called during the game setup process.
     * 
     * @param game The {@code Game} (JFrame) instance.
     * @param graphicsManager The {@code GraphicsManager} instance.
     */
    public static void initialize(Game game, GraphicsManager graphicsManager){
        GameManager.game = game;
        GameManager.graphicsManager = graphicsManager;
    }

    /**
     * Returns the time between frames as a {@code float}.
     * This is useful for time-dependent calculations, such as animation or movement speed.
     * 
     * @return The delta time (time between frames) as a {@code float}, in seconds.
     */
    public static float getFloatDeltaTime(){
        return (float)deltaTime;
    }

    /**
     * Returns the time between frames as a {@code double}.
     * 
     * @return The delta time (time between frames) as a {@code double}, in seconds.
     */
    public static double getDeltaTime(){
        return deltaTime;
    }

    /**
     * This method is called every frame by the {@code RootNode} to compute the 
     * time passed since the last frame. It updates the {@code deltaTime} value 
     * to be used for time-based operations such as movement or animations.
     */
    public static void onTick(){
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastUpdate) / 1_000_000_000.0;
        lastUpdate = currentTime;
    }
}