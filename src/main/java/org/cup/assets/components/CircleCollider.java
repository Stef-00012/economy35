package org.cup.assets.components;

import java.util.ArrayList;

import org.cup.assets.CollidersManager;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;

public abstract class CircleCollider extends GameNode {
    private interface CollisionListener {
        public void onCollisionStay();
    }

    private ArrayList<CollisionListener> listeners;

    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public CircleCollider(float radius) {
        this.radius = radius;
        CollidersManager.get().registerCollider(this);
    }

    @Override
    public void onEnable() {
        if (transform.getParentTransform() == null) {
            Debug.warn("This Circle Collider has no parent transform");
        }
    }

    public void addListener(CollisionListener listener) {
        listeners.add(listener);
    }

    public void notifyColliders() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onCollisionStay();
        }
    }
}