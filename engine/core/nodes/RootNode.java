package engine.core.nodes;

import engine.Debug;
import engine.core.errors.ParentDisabledException;
import engine.core.managers.GameManager;
import engine.core.managers.graphics.GraphicsManager;

/**
 * The {@code RootNode} class represents the root of the node tree in the game.
 * It extends {@link Node} and serves as the entry point for all node-based
 * operations. The root node manages the update cycle of the game and is
 * responsible
 * for handling graphics updates and child nodes.
 * <p>
 * The {@code RootNode} implements {@link Runnable} to run the main game loop
 * in a separate thread. This loop calls the {@code onTick} method from the
 * {@code GameManager} and updates both the root node and its children.
 * </p>
 */
public class RootNode extends Node implements Runnable {
    private GraphicsManager graphicsManager;

    @Override
    public void _setup() {
        graphicsManager = GameManager.graphicsManager;
        try {
            super._setup();
        } catch (ParentDisabledException e) {
            Debug.warn("This should never happen.");
        }
    }

    @Override
    public void _update() {
        updateChildNodes();
        graphicsManager.updateGraphics();
    }

    @Override
    public void onEnable() {
        Debug.engineLog("Root Node enabled.");
    }

    @Override
    public void run() {
        while (true) {
            // Call the GameManager's onTick method to manage game timing and events
            GameManager.onTick();

            // Update the root node and all child nodes
            _update();
        }
    }

}
