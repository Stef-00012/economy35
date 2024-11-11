package org.cup.assets.scenes;

import org.cup.assets.CollidersManager;
import org.cup.assets.objects.tests.ColliderTest;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.Scene;

public class CollisionsTest extends Scene {

    @Override
    public void init() {
        addChild(new CollidersManager());;
        ColliderTest c1 = new ColliderTest(1);
        ColliderTest c2 = new ColliderTest(-1);
        addChild(c1);
        addChild(c2);

        c1.transform.setPosition(new Vector(200, 100));
        c2.transform.setPosition(new Vector(300, 100));
    }
    
}
