package org.cup.assets.logic;

import org.cup.assets.objects.Building;
import org.cup.assets.objects.DropZone;
import org.cup.engine.core.Debug;

/**
 * A thread responsible for managing resources placed in a drop zone.
 * The thread checks periodically if a resource has been placed in the drop
 * zone, processes it,
 * and updates the inventory accordingly.
 */
public class DropZoneThread extends Thread {
    private boolean hasResource;
    private DropZone dropZone;

    /**
     * Constructs a DropZoneThread to manage a drop zone.
     * 
     * @param dropZone The drop zone where resources will be processed.
     */
    public DropZoneThread(DropZone dropZone) {
        hasResource = false;
        this.dropZone = dropZone;
    }

    /**
     * Attempts to place a resource in the drop zone.
     * If the drop zone is already occupied, it will return false.
     * 
     * @return true if the resource was placed successfully, false if the drop zone
     *         is occupied.
     */
    public synchronized boolean placeResource() {
        // If there is already a resource in the drop zone, fail to place the new one
        if (hasResource)
            return false;

        // Place the resource in the drop zone
        hasResource = true;

        return true;
    }

    /**
     * Checks if the drop zone currently holds a resource.
     * 
     * @return true if the drop zone has a resource, false otherwise.
     */
    public boolean hasResource() {
        return hasResource;
    }

    /**
     * Runs the thread, periodically checking the drop zone for resources to
     * process.
     * When a resource is placed in the drop zone, the inventory is updated and the
     * drop zone's sprite is reset.
     */
    @Override
    public void run() {
        while (true) {
            // If there is a resource in the drop zone, process it
            if (hasResource) {
                // no need to say it, but we know it deep down
                // Debug.log("im groot");

                // Simulate a delay of 2 seconds for processing
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Debug.err(e.getMessage());
                }

                // Add the resource to the inventory of the building
                Building.get().getInventory().addResource();

                // Reset the drop zone's sprite to indicate it's ready for a new resource
                dropZone.normalSprite();

                // Resource has been processed, reset the flag
                hasResource = false;
            }

            // Prevent CPU hogging
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Debug.err(e.getMessage());
            }
        }
    }

}