package org.cup.assets.objects;

import org.cup.assets.components.CircleCollider;
import org.cup.assets.components.CollisionBox;
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

    private CollisionBox collisionBox;

    double dampingFactor = 0.999999; // Reduce velocity after collision

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
        velocity = nextFrameVelocity;
        checkCollisionBox();
        transform.move(velocity.multiply(GameManager.getDeltaTime()));
    }
    
    private void checkCollisionBox(){
        if(collisionBox == null) return;
    
        if (collider.getRightBound() > collisionBox.getRightBound()) {
            transform.setPosition(new Vector(collisionBox.getRightBound() - collider.getRadius(), transform.getPosition().y));
            nextFrameVelocity = new Vector(-Math.abs(velocity.x), velocity.y);
        }
        if (collider.getLeftBound() < collisionBox.getLeftBound()) {
            transform.setPosition(new Vector(collisionBox.getLeftBound() + collider.getRadius(), transform.getPosition().y));
            nextFrameVelocity = new Vector(Math.abs(velocity.x), velocity.y);
        }
        if (collider.getTopBound() < collisionBox.getTopBound()) {
            transform.setPosition(new Vector(transform.getPosition().x, collisionBox.getTopBound() + collider.getRadius()));
            nextFrameVelocity = new Vector(velocity.x, Math.abs(velocity.y));
        }
        if (collider.getBottomBound() > collisionBox.getBottomBound()) {
            transform.setPosition(new Vector(transform.getPosition().x, collisionBox.getBottomBound() - collider.getRadius()));
            nextFrameVelocity = new Vector(velocity.x, -Math.abs(velocity.y));
        }
        nextFrameVelocity = nextFrameVelocity.multiply(dampingFactor);
    }

    private void resolveCollision(CircleCollider other) {
        if (!(other.getParent() instanceof Particle)) {
            return;
        }
        Particle p2 = (Particle) other.getParent();
    
        Vector c1 = collider.transform.getPosition();
        Vector c2 = other.transform.getPosition();
    
        Vector positionDifference = c1.subtract(c2);
        double distance = positionDifference.len();
        double minimumDistance = collider.getRadius() + p2.collider.getRadius();
    
        // Check if particles are overlapping
        if (distance < minimumDistance) {
            // Push particles apart by moving them to the minimum separation distance
            double overlap = minimumDistance - distance;
            Vector separationDirection = positionDifference.normalize();
    
            // Move each particle half the overlap distance
            transform.move(separationDirection.multiply(overlap / 2));
            p2.transform.move(separationDirection.multiply(-overlap / 2));
        }
    
        // Calculate velocity change after separation
        Vector v1 = velocity;
        Vector v2 = p2.velocity;
        Vector velocityDifference = v1.subtract(v2);
    
        double massWeight = (2 * p2.mass) / (this.mass + p2.mass);
        double projection = Vector.dot(velocityDifference, positionDifference) / Math.pow(positionDifference.len(), 2);
        nextFrameVelocity = v1.subtract(positionDifference.multiply(massWeight * projection));
        nextFrameVelocity = nextFrameVelocity.multiply(dampingFactor);
    }
    

    public void setCollisionBox(CollisionBox collisionBox){
        this.collisionBox = collisionBox;
    }
}
