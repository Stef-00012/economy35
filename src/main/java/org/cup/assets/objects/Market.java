package org.cup.assets.objects;

import java.util.ArrayList;

import org.cup.assets.PathHelper;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class Market extends GameNode {
    private SpriteRenderer sr = new SpriteRenderer(PathHelper.sprites + "market.png", transform, 2);

    private ArrayList<Customer> customersQueue = new ArrayList<>();

    public Market() {
        sr.setPivot(Renderer.BOTTOM_PIVOT);
        transform.setPosition(new Vector(620, GameManager.game.getHeight() - 59));
        transform.setScale(new Vector(288, 175));
        addChild(sr);
    }

    @Override
    public void init() {

    }

    public void joinQueue(Customer c) {
        c.setPositionInQueue(customersQueue.size());
        customersQueue.add(c);
    }

    public void moveQueue(int servedIndex) {
        customersQueue.remove(servedIndex);
        for (int i = 0; i < customersQueue.size(); i++) {
            customersQueue.get(i).setPositionInQueue(i);
        }
    }
}
