package org.cup.engine.core;

import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.managers.graphics.GraphicsManager;
import org.cup.engine.core.managers.graphics.Painter;
import org.cup.engine.core.nodes.RootNode;
import org.cup.engine.core.nodes.Scene;

import java.awt.event.KeyListener;

import javax.swing.*;

/**
 * The {@code Game} class serves as the main interface for the core engine
 * package.
 * It extends {@code JFrame} and is responsible for initializing the game
 * window,
 * managers, and the root node of the game scene graph.
 * <p>
 * The {@code Game} class also allows adding game scenes to the root node, which
 * is responsible for managing the game objects and their behaviors.
 * </p>
 */
public class Game extends JFrame {
    // The root node of the game, which manages all scenes and game objects.
    private RootNode root;

    /**
     * Constructs a {@code Game} object with the specified window title, dimensions,
     * and icon.
     *
     * @param title  The title of the game window.
     * @param width  The width of the game window in pixels.
     * @param height The height of the game window in pixels.
     * @param icon   The relative path to the icon image file for the window.
     */
    public Game(String title, int width, int height, String icon) {
        this(title, width, height, icon, 1, false);
    }

    /**
     * Constructs a {@code Game} object with the specified window parameters,
     * rendering scale, and optional antialiasing.
     *
     * @param title        The title of the game window.
     * @param width        The width of the game window in pixels.
     * @param height       The height of the game window in pixels.
     * @param icon         The relative path to the icon image file for the window.
     * @param scale        The scale factor for rendering game graphics.
     * @param antialiasing A flag indicating whether antialiasing should be enabled.
     *                     If {@code true}, antialiasing is enabled for smoother
     *                     rendering.
     */
    public Game(String title, int width, int height, String icon, float scale, boolean antialiasing) {
        this.setTitle(title);


        // ! the 39 is for the best OS in the world: Microsoft Windows who needs 39px (not 40) for the title bar
        this.setSize(width, height + 39);
        this.setResizable(false); // For the scope of this game it's better not to handle responsivenes

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon iconImage = new ImageIcon(icon);
        this.setIconImage(iconImage.getImage());

        this.setVisible(true);

        // Initialize Managers
        GraphicsManager graphicsManager = new GraphicsManager();
        GameManager.initialize(this, graphicsManager);

        // Initialize the Painter (which will draw graphics on the screen)
        Painter p = graphicsManager.getPainter();
        this.getContentPane().add(p);
        p.setScale(scale);
        p.setAntialiasing(antialiasing);

        // Create and start the root node
        root = new RootNode();
        root._setup();
        new Thread(root).start();
    
        // create a performance monitor
        new PerformanceMonitor(true).start();
    }

    /**
     * Adds a {@code Scene} to the root node.
     * To remove a scene just disable or remove it.
     *
     * @param scene The {@code Scene} to be added to the root node.
     */
    public void addScene(Scene scene) {
        root.addChild(scene);
    }

    /**
     * Removes a {@code Scene} from the root node.
     * It's reccomended not to remove a scene, disable it instead.
     *
     * @param scene The {@code Scene} to be removed from the root node.
     */
    public void removeScene(Scene scene) {
        root.removeChild(scene);
    }

}
