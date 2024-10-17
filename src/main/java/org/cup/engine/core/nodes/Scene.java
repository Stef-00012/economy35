package org.cup.engine.core.nodes;

import org.cup.engine.core.errors.ParentDisabledException;

/**
 * Represents a scene in the engine, typically containing multiple nodes.
 * This abstract class provides lifecycle methods for initialization and enabling.
 * Subclasses should define specific behavior in the `init()` method.
 */
public abstract class Scene extends Node {

    /**
     * Initializes the scene. Subclasses should override this method
     * to set up any required objects or configurations for the scene.
     */
    public abstract void init();

    /**
     * Enables the scene by calling the `_setup` method, which may involve
     * setting up child nodes or other dependencies. If the parent node
     * is disabled, it will catch a `ParentDisabledException`.
     */
    public void enable() {
        try {
            _setup();
        } catch (ParentDisabledException e) {
            System.out.println(e.getMessage());
        }
    }
}
