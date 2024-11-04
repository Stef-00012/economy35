package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.assets.UI.Floor;
import org.cup.engine.Vector;
import org.cup.engine.core.Debug;
import org.cup.engine.core.nodes.GameNode;

import java.util.ArrayList;

public class Building extends GameNode {
    private static Building instance; // Singleton pattern

    private Inventory inventory = new Inventory(3);
    private Elevator elevator = new Elevator();
    private Market market = new Market();

    private ArrayList<Floor> floors = new ArrayList<>();

    private int roomHeight = 175;
    int roomWidth = 500;

    public Building(){
        if(Building.instance != null) 
            Debug.err("More than one building has been initialized");
        Building.instance = this;

        floors.add(inventory);
    }

    public static Building get(){
        return instance;
    }

    @Override
    public void init(){
        transform.setPosition(new Vector(120-1, 175*3));

        addChild(elevator);
        addChild(inventory);
        addChild(market);
    }

    public void addRoom(){
        int nRooms = floors.size() - 1; // Ignore the inventory

        String basePath = PathHelper.sprites + "rooms\\room-background";
        String finalPath = basePath + (nRooms % 2 == 0 ? "-decorated" : "") + ".png";
        Room room = new Room(roomWidth, roomHeight, 0, (-roomHeight * nRooms), 1, finalPath);

        room.transform.setParentTransform(transform);

        floors.add(room);
        addChild(room);
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Elevator getElevator(){
        return elevator;
    }

    public Market getMarket(){
        return market;
    }

    public Floor getUpFloor(int currentFloor){
        if(currentFloor + 1 >= floors.size()){
            return null;
        }
        return floors.get(currentFloor + 1);
    }

    public Floor getDownFloor(int currentFloor){
        if(currentFloor - 1 < 0){
            return null;
        }
        return floors.get(currentFloor - 1);
    }

}
