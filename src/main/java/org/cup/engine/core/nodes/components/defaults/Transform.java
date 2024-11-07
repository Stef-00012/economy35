package org.cup.engine.core.nodes.components.defaults;

import java.util.ArrayList;
import org.cup.engine.Vector;

/**
 * Represents a transformation in 2D space that defines an object's position, scale, and rotation.
 * This class supports hierarchical transformations where child transforms inherit and combine
 * transformations from their parent transforms.
 * 
 * The Transform class uses a caching mechanism to optimize performance when calculating
 * absolute transformations in a hierarchy. When a transform property changes, it marks
 * itself and its children as "dirty", requiring a recalculation of absolute values
 * only when they are next requested.
 */
public class Transform {
    private Vector position = Vector.ZERO;
    private Vector scale = Vector.ONE;
    private double rotation = 0;

    private boolean isScaleDirty = true;
    private boolean isPositionDirty = true;
    
    /** Guard flag to prevent recursive updates in transform hierarchies */
    private boolean isUpdating = false;

    private Vector cachedPosition = Vector.ONE;
    private Vector cachedScale = Vector.ONE;

    private Transform parentTransform;
    
    private ArrayList<Transform> children = new ArrayList<>();

    private String nodeName;

    /**
     * Creates a new transform with no parent (root transform).
     */
    public Transform() {
        parentTransform = null;
    }

    /**
     * Creates a new transform with the specified parent.
     * The new transform will automatically be added as a child to the parent.
     *
     * @param parent The parent transform from which this transform will inherit transformations
     */
    public Transform(Transform parent) {
        parentTransform = parent;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    

    /**
     * Moves the transform by adding the specified vector to its current position.
     * This will mark the position as dirty, requiring a recalculation of absolute positions
     * for this transform and its children.
     *
     * @param v The vector to add to the current position
     */
    public void move(Vector v) {
        position = position.add(v);
        markPositionDirty();
    }

    /**
     * Rotates the transform by adding the specified angle to its current rotation.
     *
     * @param rad The angle to add in radians
     */
    public void rotate(double rad) {
        rotation += rad;
    }

    /**
     * Marks this transform's position as dirty and propagates the dirty state to all children.
     * This is called whenever a transform's position or its parent's transform changes.
     */
    private void markPositionDirty() {
        if (!isPositionDirty) {
            isPositionDirty = true;
            // Mark all children as dirty
            for (Transform child : children) {
                child.markPositionDirty();
            }
        }
    }

    /**
     * Marks this transform's scale as dirty and propagates the dirty state to all children.
     * This is called whenever a transform's scale or its parent's transform changes.
     */
    private void markScaleDirty() {
        if (!isScaleDirty) {
            isScaleDirty = true;
            // Mark all children as dirty
            for (Transform child : children) {
                child.markScaleDirty();
            }
        }
    }

    /**
     * Gets the absolute position of this transform in world space.
     * This includes all parent transformations (position, rotation, and scale).
     * Uses caching to optimize performance in transform hierarchies.
     *
     * @return The absolute position vector
     */
    public Vector getPosition() {
        if (isPositionDirty && !isUpdating) {
            updatePositionCache();
        }
        return cachedPosition;
    }

    /**
     * Updates the cached absolute position by combining local position with parent transformations.
     * Includes parent's position, rotation, and scale effects on this transform's position.
     * Uses an updating flag to prevent recursive updates in circular references.
     */
    private void updatePositionCache() {
        if (isUpdating) return;  // Prevent recursive updates
        
        isUpdating = true;
        try {
            if (parentTransform != null) {
                Vector parentPos = parentTransform.getPosition();
                cachedPosition = parentPos.add(position);
            } else {
                cachedPosition = position;
            }
            isPositionDirty = false;
        } finally {
            isUpdating = false;
        }
    }

    /**
     * Gets the absolute scale of this transform in world space.
     * This combines all parent scales through multiplication.
     * Uses caching to optimize performance in transform hierarchies.
     *
     * @return The absolute scale vector
     */
    public Vector getScale() {
        if (isScaleDirty && !isUpdating) {
            updateScaleCache();
        }
        return cachedScale;
    }

    /**
     * Updates the cached absolute scale by multiplying local scale with parent scale.
     * Uses an updating flag to prevent recursive updates in circular references.
     */
    private void updateScaleCache() {
        if (isUpdating) return;  // Prevent recursive updates
        
        isUpdating = true;
        try {
            if (parentTransform != null) {
                cachedScale = Vector.multiplyVec(scale, parentTransform.getScale());
            } else {
                cachedScale = scale;
            }
            isScaleDirty = false;
        } finally {
            isUpdating = false;
        }
    }

    /**
     * Sets the local position of this transform relative to its parent.
     * Marks the position as dirty to trigger recalculation of absolute positions.
     *
     * @param position The new local position vector
     */
    public void setPosition(Vector position) {
        this.position = position;
        markPositionDirty();
    }

    /**
     * Sets the local scale of this transform relative to its parent.
     * Marks the scale as dirty to trigger recalculation of absolute scales.
     *
     * @param scale The new local scale vector
     */
    public void setScale(Vector scale) {
        this.scale = scale;
        markScaleDirty();
    }

    /**
     * Gets the absolute rotation of this transform in world space.
     * This adds all parent rotations to the local rotation.
     *
     * @return The absolute rotation in radians
     */
    public double getRotation() {
        return parentTransform != null ? parentTransform.getRotation() + rotation : rotation;
    }

    /**
     * Sets the local rotation of this transform relative to its parent.
     *
     * @param rad The new local rotation in radians
     */
    public void setRotation(double rad) {
        this.rotation = rad;
    }

    /**
     * Gets the parent transform of this transform.
     *
     * @return The parent transform, or null if this is a root transform
     */
    public Transform getParentTransform() {
        return parentTransform;
    }

    /**
     * Sets the parent transform of this transform.
     * This method handles removing this transform from its previous parent's children
     * and adding it to the new parent's children. It also marks position and scale
     * as dirty to ensure proper updating of absolute transformations.
     *
     * @param parentTransform The new parent transform, or null to make this a root transform
     */
    public void setParentTransform(Transform parentTransform) {
        if (this.parentTransform != null) {
            this.parentTransform.removeChild(this);
        }
        this.parentTransform = parentTransform;
        if (parentTransform != null) {
            parentTransform.addChild(this);
        }
        markPositionDirty();
        markScaleDirty();
    }

    /**
     * Adds a child transform to this transform's list of children.
     * Prevents duplicate additions of the same child.
     *
     * @param t The transform to add as a child
     */
    private void addChild(Transform t) {
        if (!children.contains(t)) {
            children.add(t);
        }
    }

    /**
     * Removes a child transform from this transform's list of children.
     *
     * @param t The transform to remove
     */
    private void removeChild(Transform t) {
        children.remove(t);
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * Prints the transform hierarchy starting from this transform.
     * The output will include the node name and the transform information (position, scale, rotation) for each transform in the hierarchy.
     */
    public void printTransformHierarchy() {
        printTransformHierarchy(0);
    }

    private void printTransformHierarchy(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        sb.append(nodeName)
          .append("\n");
        System.out.print(sb.toString());

        for (Transform child : children) {
            child.printTransformHierarchy(indent + 1);
        }
    }
}