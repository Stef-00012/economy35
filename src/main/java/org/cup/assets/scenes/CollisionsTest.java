package org.cup.assets.scenes;

import org.cup.assets.CollidersManager;
import org.cup.assets.PathHelper;
import org.cup.assets.objects.Particle;
import org.cup.assets.objects.tests.ColliderTest;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.Scene;

public class CollisionsTest extends Scene {

    @Override
    public void init() {
        addChild(new CollidersManager());
        
        //initColl();
        initParticles();
    }

    private void initColl() {
        ColliderTest c1 = new ColliderTest(1);
        ColliderTest c2 = new ColliderTest(-1);
        addChild(c1);
        addChild(c2);

        c1.transform.setPosition(new Vector(200, 100));
        c2.transform.setPosition(new Vector(300, 100));
    }

    private void initParticles() {
        String s = PathHelper.sprites + "circle.png";

        Particle p1 = new Particle(s, new Vector(100), 5);
        Particle p2 = new Particle(s, new Vector(-100, 100), 3);
        addChild(p1);
        addChild(p2);

        p1.transform.setPosition(new Vector(200, 100));
        p2.transform.setPosition(new Vector(500, 100));

        p1.transform.setScale(new Vector(50));
        p2.transform.setScale(new Vector(50));
    }

}
