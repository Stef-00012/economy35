package org.cup.engine.core.nodes;

import org.cup.engine.core.nodes.components.defaults.Transform;

public class GameNode extends Node {
    public final Transform transform = new Transform();

    public GameNode() {
        transform.setNodeName(getClass().getSimpleName());
    }
}
