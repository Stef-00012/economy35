package engine.core.nodes.components.defaults;

import engine.Vector;
import engine.core.nodes.Node;

public class Transform extends Node {
    private Vector position = Vector.ONE;
    private Vector scale = Vector.ONE;
    private double rotation = 0;

    private Transform parentTransform;

    public Transform(){
        parentTransform = null;
    }

    public Transform(Transform parent){
        parentTransform = parent;
    }

    public void move(Vector v){
        position = position.add(v);
    }

    public void rotate(double rad){
        rotation += rad;
    }

    //#region Getters & Setters
    public Vector getPosition() {
        if(parentTransform != null) return parentTransform.position.add(position);
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getScale() {
        if(parentTransform != null) return parentTransform.scale.add(scale);
        return scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }

    public double getRotation() {
        if(parentTransform != null) return parentTransform.rotation + rotation;
        return rotation;
    }

    public void setRotation(double rad) {
        this.rotation = rad;
    }

    public Transform getParentTransform() {
        return parentTransform;
    }

    public void setParentTransform(Transform parentTransform) {
        this.parentTransform = parentTransform;
    }
    //#endregion
}
