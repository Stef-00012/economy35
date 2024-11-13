package org.cup.assets.objects;

import org.cup.assets.components.CircleCollider;
import org.cup.assets.components.CircleCollider.CollisionListener;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class Particle extends GameNode {
    private Vector velocity;
    private Vector nextFrameVelocity;
    private float mass;

    private SpriteRenderer sr;

    private CircleCollider collider;

    int n = 0;

    public Particle(String sprite, Vector velocity, float mass) {
        this.velocity = velocity;
        this.nextFrameVelocity = velocity;
        this.mass = mass;

        sr = new SpriteRenderer(sprite, transform, 2);
        collider = new CircleCollider();
        collider.transform.setParentTransform(transform);

        collider.addListener(new CollisionListener() {

            @Override
            public void onCollisionEnter(CircleCollider other) {
            }

            @Override
            public void onCollisionStay(CircleCollider other) {
                resolveCollision(other);
            }

            @Override
            public void onCollisionExit(CircleCollider other) {
            }

        });

        addChild(sr);
        addChild(collider);
    }

    @Override
    public void onUpdate() {
        n++;
        velocity = nextFrameVelocity;
        transform.move(velocity.multiply(GameManager.getDeltaTime()));
    }

    private void resolveCollision(CircleCollider other) {
        if (other.getParent().getClass() != this.getClass()) {
            return;
        }
        Particle p2 = (Particle) other.getParent();

        Vector c1 = collider.transform.getPosition();
        Vector c2 = other.transform.getPosition();

        Vector v1 = velocity;
        Vector v2 = p2.velocity;

        Vector positionDifference = c1.subtract(c2);
        Vector velocityDifference = v1.subtract(v2);

        double massWeight = (2 * p2.mass) / (this.mass + p2.mass);
        double projection = Vector.dot(velocityDifference, positionDifference) / Math.pow(positionDifference.len(), 2);
        nextFrameVelocity = v1.subtract(positionDifference.multiply(massWeight * projection));

        transform.move(nextFrameVelocity.multiply(GameManager.getDeltaTime()));
    }
}
