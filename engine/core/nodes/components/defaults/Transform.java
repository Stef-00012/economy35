package engine.core.nodes.components.defaults;

import engine.Vector;
import engine.core.nodes.Node;

/**
 * Represents a transformation in a 2D space, including position, scale, and rotation.
 * It supports hierarchical transformations, allowing a child to inherit transformations from its parent.
 */
public class Transform extends Node {

    // Local position, scale, and rotation
    private Vector position = Vector.ONE;
    private Vector scale = Vector.ONE;    
    private double rotation = 0; // Rotation in radians       

    private Transform parentTransform;

    public Transform() {
        parentTransform = null; 
    }

    /**
     * Constructor for Transform with a parent transform.
     *
     * @param parent The parent Transform from which this Transform inherits position, scale, and rotation.
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
    }

    /**
     * Rotates the current transform by a given angle in radians.
     *
     * @param rad The angle to rotate by, in radians.
     */
    public void rotate(double rad) {
        rotation += rad;
    }

    //#region Getters & Setters

    /**
     * Gets the absolute position of this transform, including parent transform if present.
     *
     * @return The absolute position as a Vector.
     */
    public Vector getPosition() {
        if (parentTransform != null) {
            return parentTransform.getPosition().add(position);
        }
        return position;
    }

    /**
     * Sets the local position of this transform.
     *
     * @param position The new local position as a Vector.
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * Gets the absolute scale of this transform, combining with the parent's scale if present.
     *
     * @return The absolute scale as a Vector.
     */
    public Vector getScale() {
        if (parentTransform != null) {
            return Vector.multiplyVec(scale, parentTransform.getScale()); // Scaling is often multiplicative
        }
        return scale;
    }

    /**
     * Sets the local scale of this transform.
     *
     * @param scale The new local scale as a Vector.
     */
    public void setScale(Vector scale) {
        this.scale = scale;
    }

    /**
     * Gets the absolute rotation of this transform, including parent rotation if present.
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
     * Sets the parent transform for this transform.
     *
     * @param parentTransform The new parent Transform.
     */
    public void setParentTransform(Transform parentTransform) {
        this.parentTransform = parentTransform;
    }
    //#endregion
}
