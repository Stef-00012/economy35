package org.cup.assets.components;

import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;

public class CollisionBox extends GameNode {
    public CollisionBox(Vector position, Vector scale) {
        transform.setPosition(position);
        transform.setScale(scale);
    }

    public double getBottomBound() {
        return transform.getPosition().y + transform.getScale().y / 2;
    }

    public double getTopBound() {
        return transform.getPosition().y - transform.getScale().y / 2;
    }

    public double getRightBound() {
        return transform.getPosition().x + transform.getScale().x / 2;
    }

    public double getLeftBound() {
        return transform.getPosition().x - transform.getScale().x / 2;
    }
}

