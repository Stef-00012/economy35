package org.cup.assets.objects;

import java.awt.Color;

import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SquareRenderer;
import org.cup.engine.core.nodes.components.defaults.Transform;
import org.cup.engine.Vector;

public class ElevatorZone extends GameNode {
    public ElevatorZone(Transform t){
        transform.setParentTransform(t);
        SquareRenderer renderer = new SquareRenderer(transform, -1);
        renderer.setColor(new Color(54, 42, 37)); 
        renderer.setPivot(Renderer.BOTTOM_RIGHT_PIVOT);
        transform.setScale(new Vector(120, Building.ROOM_HEIGHT));

        addChild(renderer);
    }
    
}
