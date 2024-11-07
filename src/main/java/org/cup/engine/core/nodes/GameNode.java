package org.cup.engine.core.nodes;

import org.cup.engine.core.nodes.components.defaults.Transform;

/**
 * Represents a basic game node in the engine, encapsulating position, rotation, 
 * and scaling information through its {@link Transform} component. 
 * A `GameNode` is a foundational class that can be extended to represent 
 * objects within the game scene.
 */
public class GameNode extends Node {
    /**
     * The {@link Transform} component of this node, which holds positional, rotational,
     * and scaling data for the node in the game world.
     */
    public final Transform transform = new Transform();

    /**
     * Constructs a new `GameNode` and assigns its `transform` component a default name
     * based on the class name of this node. The name is useful for debugging and
     * identifying nodes within the game engine.
     */
    public GameNode() {
        transform.setNodeName(getClass().getSimpleName());
    }
}
