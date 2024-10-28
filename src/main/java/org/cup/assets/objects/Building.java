package org.cup.assets.objects;

import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;

import java.awt.Color;

public class Building extends GameNode {

    int nRooms = 0;
    int roomHeight = 175;
    int roomWidth = 500;
    
    @Override
    public void init(){
        transform.setPosition(new Vector(120, 175*3));
    }


    public void addRoom(){
        Color col = nRooms % 2 == 0 ? new Color(221, 221, 221) : Color.GRAY;
        Room room = new Room(roomWidth, roomHeight, 0, (-roomHeight * nRooms), 1, col);
        room.transform.setParentTransform(transform);
        nRooms++;
        addChild(room);
    }

    public int getBuildingHeight(){
        return nRooms * roomHeight;
    }

}
