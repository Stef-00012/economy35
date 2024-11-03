package org.cup.assets.logic;

import org.cup.assets.objects.Building;
import org.cup.assets.objects.DropZone;
import org.cup.engine.core.Debug;

public class DropZoneThread extends Thread {
    private boolean hasResource;
    private DropZone dropZone;

    public DropZoneThread(DropZone dropZone) {
        hasResource = false;
        this.dropZone = dropZone;
    }

    /**
     * Try to place a resource in the dropzone
     * 
     * @return False if it's occupied by another reource
     */
    public synchronized boolean placeResource() {
        if (hasResource)
            return false;

        hasResource = true;

        return true;
    }

    public boolean hasResource(){
        return hasResource;
    }

    @Override
    public void run() {
        while (true) {
            if (hasResource) {
                // no need to say it, but we know it deep down 
                // Debug.log("im groot");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Debug.err(e.getMessage());
                }

                Building.get().getInventory().addResource();
                dropZone.normalSprite();

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