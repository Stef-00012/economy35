package org.cup.assets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.cup.assets.components.CircleCollider;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;

// Let's handle circle colliders only, for the scope of this project
// https://github.com/AlessTheDev/dough-canvas-engine/blob/main/src/engine/nodes/extensions/CollidersManager.ts
public class CollidersManager extends GameNode{
    private static CollidersManager instance;
    private ArrayList<CircleCollider> colliders;

    public CollidersManager() {
        if (instance != null) {
            Debug.err("More than one building has been initialized");
        }

        instance = this;
        colliders = new ArrayList<>();
    }

    public void registerCollider(CircleCollider c) {
        colliders.add(c);
    }

    @Override
    public void onUpdate() {
        Collections.sort(colliders,
                (c1, c2) -> Integer.compare(c1.transform.getPosition().getX(), c2.transform.getPosition().getX()));

        // Iterate other the colliders
        int length = colliders.size();
        for (int i = 0; i < length; i++) {
            CircleCollider c1 = colliders.get(i);
            for (int k = i + 1; k < length; k++) {
                CircleCollider c2 = colliders.get(k);
                if (Vector.distance(c1.transform.getPosition(), c2.transform.getPosition()) < c1.getRadius() + c2.getRadius()) {
                    c1.notifyColliders();
                    c2.notifyColliders();
                }
            }
        }
    }

    public static CollidersManager get(){
        return instance;
    }

}
