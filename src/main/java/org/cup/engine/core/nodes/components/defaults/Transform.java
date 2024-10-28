package org.cup.engine.core.nodes.components.defaults;

import org.cup.engine.Vector;

/**
 * Represents a transformation in a 2D space, including position, scale, and
 * rotation.
 * It supports hierarchical transformations, allowing a child to inherit
 * transformations from its parent.
 */
public class Transform {

    // Local position, scale, and rotation
    private Vector position = Vector.ONE;
    private Vector scale = Vector.ONE;
    private double rotation = 0; // Rotation in radians

    private boolean isScaleDirty = true;
    private boolean isPositionDirty = true;

    private Vector cachedPosition = Vector.ONE;
    private Vector cachedScale = Vector.ONE;

    private Transform parentTransform;

    public Transform() {
        parentTransform = null;
    }

    /**
     * Constructor for Transform with a parent transform.
     *
     * @param parent The parent Transform from which this Transform inherits
     *               position, scale, and rotation.
     */
    public Transform(Transform parent) {
        parentTransform = parent;
    }

    /**
     * Moves the current transform by a given vector.
     *
     * @param v The vector by which to move the transform.
     */
    public void move(Vector v) {
        position = position.add(v);
        isPositionDirty = true;
    }

    /**
     * Rotates the current transform by a given angle in radians.
     *
     * @param rad The angle to rotate by, in radians.
     */
    public void rotate(double rad) {
        rotation += rad;
    }

    // #region Getters & Setters

    /**
     * Gets the absolute position of this transform, including parent transform if
     * present.
     *
     * @return The absolute position as a Vector.
     */
    public Vector getPosition() {
        if (isPositionDirty || (parentTransform != null && parentTransform.isPositionDirty)) {
            updatePositionCache();
        }
        return cachedPosition;
    }

    private void updatePositionCache() {
        if (parentTransform != null) {
            cachedPosition = parentTransform.getPosition().add(position);
        } else {
            cachedPosition = position;
        }

        isPositionDirty = false;
    }

    /**
     * Sets the local position of this transform.
     *
     * @param position The new local position as a Vector.
     */
    public void setPosition(Vector position) {
        this.position = position;
        isPositionDirty = true;
    }

    /**
     * Gets the absolute scale of this transform, combining with the parent's scale
     * if present.
     *
     * @return The absolute scale as a Vector.
     */
    public Vector getScale() {
        if (isScaleDirty || (parentTransform != null && parentTransform.isScaleDirty)) {
            updateScaleCache();
        }
        return cachedScale;
    }

    private void updateScaleCache() {
        if (parentTransform != null) {
            cachedScale = Vector.multiplyVec(scale, parentTransform.getScale());
        } else {
            cachedScale = scale;
        }

        isScaleDirty = false;
    }

    /**
     * Sets the local scale of this transform.
     *
     * @param scale The new local scale as a Vector.
     */
    public void setScale(Vector scale) {
        this.scale = scale;
        isScaleDirty = true;
    }

    /**
     * Gets the absolute rotation of this transform, including parent rotation if
     * present.
     *
     * @return The absolute rotation in radians.
     */
    public double getRotation() {
        if (parentTransform != null) {
            return parentTransform.getRotation() + rotation;
        }
        return rotation;
    }

    /**
     * Sets the local rotation of this transform.
     *
     * @param rad The new local rotation in radians.
     */
    public void setRotation(double rad) {
        this.rotation = rad;
    }

    /**
     * Gets the parent transform of this transform.
     *
     * @return The parent Transform, or null if none is set.
     */
    public Transform getParentTransform() {
        return parentTransform;
    }

    /**
     * The position is dirty if it changed, therefore it needs to update the cache
     * 
     * @return true if the position variable has changed
     */
    public boolean isPositionDirty() {
        return isPositionDirty;
    }

    /**
     * The scale is dirty if it changed, therefore it needs to update the cache
     * 
     * @return true if the scale variable has changed
     */
    public boolean isScaleDirty() {
        return isScaleDirty;
    }

    /**
     * Sets the parent transform for this transform.
     *
     * @param parentTransform The new parent Transform.
     */
    public void setParentTransform(Transform parentTransform) {
        this.parentTransform = parentTransform;

        isPositionDirty = true;
        isScaleDirty = true;
    }
    // #endregion
}
