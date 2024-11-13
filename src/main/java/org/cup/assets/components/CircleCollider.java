package org.cup.assets.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        void onCollisionEnter(CircleCollider other);

        /** Called continuously while the collision persists. */
        void onCollisionStay(CircleCollider other);

        /** Called when the collision ends. */
        void onCollisionExit(CircleCollider other);
    }

    private ArrayList<CollisionListener> listeners;
    private Set<CircleCollider> currentCollisions = new HashSet<>();
    private Set<CircleCollider> previousCollisions = new HashSet<>();

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
    public void notifyColliders(CircleCollider c) {
        currentCollisions.add(c);
    }

    /**
     * Updates collision state and notifies listeners of state changes.
     */
    public void updateCollisionState() {
        // Check for new collisions (Enter)
        for (CircleCollider collider : currentCollisions) {
            if (!previousCollisions.contains(collider)) {
                for (CollisionListener listener : listeners) {
                    listener.onCollisionEnter(collider);
                }
            } else {
                for (CollisionListener listener : listeners) {
                    listener.onCollisionStay(collider);
                }
            }
        }

        // Check for ended collisions (Exit)
        for (CircleCollider collider : previousCollisions) {
            if (!currentCollisions.contains(collider)) {
                for (CollisionListener listener : listeners) {
                    listener.onCollisionExit(collider);
                }
            }
        }

        // Update previousCollisions for the next frame
        previousCollisions.clear();
        previousCollisions.addAll(currentCollisions);

        // Clear currentCollisions for the next frame
        currentCollisions.clear();
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
