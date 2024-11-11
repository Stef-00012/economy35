package org.cup.assets.components;

import java.util.ArrayList;
import org.cup.assets.CollidersManager;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;

/**
 * Represents a circular collider component that can detect and manage collision states.
 * This component can register collision events and notify listeners about changes in collision state.
 */
public class CircleCollider extends GameNode {

    /**
     * Interface for listening to collision events. Provides methods to handle
     * events when a collision starts, stays, or ends.
     */
    public interface CollisionListener {
        /** Called when a collision is first detected. */
        void onCollisionEnter();

        /** Called continuously while the collision persists. */
        void onCollisionStay();

        /** Called when the collision ends. */
        void onCollisionExit();
    }

    private ArrayList<CollisionListener> listeners;
    private boolean isColliding = false;
    private boolean wasColliding = false;

    /**
     * Creates a new CircleCollider instance and initializes its listener list.
     */
    public CircleCollider() {
        listeners = new ArrayList<>();
    }

    /**
     * Initializes the collider by registering it with the {@link CollidersManager}.
     * This method is automatically called upon component initialization.
     */
    @Override
    public void init() {
        CollidersManager.get().registerCollider(this);
    }

    /**
     * Called when this collider is enabled. Verifies that the collider has a
     * parent transform and issues a warning if it does not.
     */
    @Override
    public void onEnable() {
        if (transform.getParentTransform() == null) {
            Debug.warn("This Circle Collider has no parent transform");
        }
    }

    /**
     * Adds a new listener to be notified about collision events.
     *
     * @param listener The listener to be added.
     */
    public void addListener(CollisionListener listener) {
        listeners.add(listener);
    }

    /**
     * Marks this collider as currently colliding. This method is intended to be
     * called by an external collision detection system.
     */
    public void notifyColliders() {
        isColliding = true;
    }

    /**
     * Updates the collision state and notifies listeners about state changes.
     * Triggers the appropriate callback on listeners based on whether a collision
     * has started, is ongoing, or has ended.
     */
    public void updateCollisionState() {
        for (CollisionListener listener : listeners) {
            if (isColliding) {
                if (!wasColliding) {
                    listener.onCollisionEnter();
                    wasColliding = true;
                }
                listener.onCollisionStay();
            } else if (wasColliding) {
                listener.onCollisionExit();
                wasColliding = false;
            }
        }
        isColliding = false;
    }

    /**
     * Calculates and returns the radius of the circle collider based on the scale
     * of its transform.
     *
     * @return The radius of the collider.
     */
    public double getRadius() {
        return transform.getScale().getX() / 2;
    }
}
