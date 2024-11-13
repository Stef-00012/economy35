package org.cup.assets.objects.tests;

import org.cup.assets.PathHelper;
import org.cup.assets.components.CircleCollider;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import org.cup.engine.core.nodes.components.defaults.Transform;

public class ColliderTest extends GameNode {
    private String normalSprite = PathHelper.sprites + "circle.png";
    private String colliderSprite = PathHelper.sprites + "circle-coll.png";

    CircleCollider collider; 
    
    private Transform childTransform = new Transform();
    SpriteRenderer sr = new SpriteRenderer(normalSprite, childTransform, 0);

    private int dir = 0;

    public ColliderTest(int dir){
        this.dir = dir;
        collider = new CircleCollider();
        transform.setScale(new Vector(100));

        addChild(sr);
        addChild(collider);
        collider.transform.setParentTransform(childTransform);
        childTransform.setParentTransform(transform);

        collider.addListener(new CircleCollider.CollisionListener() {

            @Override
            public void onCollisionStay(CircleCollider other) {
                
            }

            @Override
            public void onCollisionEnter(CircleCollider other) {
                sr.setSprite(colliderSprite);
            }

            @Override
            public void onCollisionExit(CircleCollider other) {
                sr.setSprite(normalSprite);
            }
            
        });
    }

    private double t = 0;
    @Override
    public void onUpdate() {
        t += GameManager.getDeltaTime() * 3;

        childTransform.setPosition(Vector.RIGHT.multiply(Math.sin(t) * 100 * dir));
    }

        
}
