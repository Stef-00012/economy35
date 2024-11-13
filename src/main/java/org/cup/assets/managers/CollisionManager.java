package org.cup.assets.managers;

import java.util.ArrayList;
import java.util.Collections;

import org.cup.assets.components.CircleCollider;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;

// Let's handle circle colliders only, for the scope of this project
// https://github.com/AlessTheDev/dough-canvas-engine/blob/main/src/engine/nodes/extensions/CollidersManager.ts

// NOTE: Consider using Space Partitioning in case more than 30 colliders are involved in different places
// https://youtu.be/eED4bSkYCB8?si=3tPfTsvjsHyBlY_L

public class CollisionManager extends GameNode {
    private static CollisionManager instance;
    private ArrayList<CircleCollider> colliders;

    public CollisionManager() {
        if (instance != null) {
            Debug.err("More than one building has been initialized");
        }

        instance = this;
        colliders = new ArrayList<>();
    }

    public void registerCollider(CircleCollider c) {
        colliders.add(c);
    }

    /*
     * SWEEP AND PURNE ALGORITHM
     * 
     * Average-case complexity: O(n), thanks to nearly sorted data and the early
     * exit optimization in the nested loop.
     * Worst-case complexity: O(n^2), if colliders are densely packed and the
     * sorting doesn’t benefit from being almost sorted.
     */
    @Override
    public void onUpdate() {
        // Sorting the objects (in this case, the CircleColliders) based on one of their
        // coordinates.
        Collections.sort(colliders,
                (c1, c2) -> Integer.compare(c1.transform.getPosition().getX(), c2.transform.getPosition().getX()));

        // Sweeping through the sorted list and checking for collisions only between
        // objects that are close enough to each other based on the sorted order. This
        // allows you to avoid unnecessary collision checks by pruning out distant
        // objects.
        int length = colliders.size();
        for (int i = 0; i < length; i++) {
            CircleCollider c1 = colliders.get(i);

            // Ignore the object if inactive
            if (!c1.isActive())
                continue;

            for (int j = i + 1; j < length; j++) {
                CircleCollider c2 = colliders.get(j);

                // Ignore the object if inactive
                if (!c2.isActive()) {
                    continue;
                }

                // Check if c1 and c2 are actually colliding
                if (Vector.distance(c1.transform.getPosition(), c2.transform.getPosition()) < c1.getRadius()
                        + c2.getRadius()) {
                    c1.notifyColliders(c2);
                    c2.notifyColliders(c1);
                }
            }
        }

        // Let colliders call events
        for (CircleCollider c : colliders) {
            c.updateCollisionState();
        }
    }

    public static CollisionManager get() {
        return instance;
    }

}