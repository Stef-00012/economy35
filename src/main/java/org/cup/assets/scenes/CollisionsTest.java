package org.cup.assets.scenes;

import java.awt.Color;
import java.util.Random;

import org.cup.assets.CollidersManager;
import org.cup.assets.PathHelper;
import org.cup.assets.components.CollisionBox;
import org.cup.assets.objects.Particle;
import org.cup.assets.objects.Rectangle;
import org.cup.assets.objects.tests.ColliderTest;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.Scene;
import org.cup.engine.core.nodes.components.Renderer;

public class CollisionsTest extends Scene {

    @Override
    public void init() {
        addChild(new CollidersManager());

        // initColl();
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

        Vector pos = GameManager.game.getWindowDimentions().divide(2);
        Vector scale = GameManager.game.getWindowDimentions();
        CollisionBox c = new CollisionBox(pos, scale);

        addChild(c);

        Random r = new Random();

        for(int i = 0; i < 20; i++){
            int size = 40 + r.nextInt(40);

            Particle p1 = new Particle(s, new Vector(100 * (r.nextInt(2) == 0 ? 1 : -1)), size);
            addChild(p1);
            p1.setCollisionBox(c);

            p1.transform.setPosition(new Vector(i * size + 10, r.nextInt(1920)));
            p1.transform.setScale(new Vector(size));
        }

    }

}
