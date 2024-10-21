package org.cup.assets.objects;

import java.awt.Color;

import org.cup.engine.core.nodes.components.Renderer;

public class Room extends Rectangle {

    public Room(int width, int height, int x, int y, int layer, Color c) {
        super(width, height, x, y, layer, c);
        sr.setPivot(Renderer.BOTTOM_LEFT_PIVOT);
    }
}
