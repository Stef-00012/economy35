package org.cup.assets.logic;

import org.cup.assets.objects.DropZone;
import org.cup.assets.scenes.MainScene;
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

        Debug.log("denis-3.14159255398");
        return true;
    }

    public boolean hasResource(){
        return hasResource;
    }

    @Override
    public void run() {
        while (true) {
            if (hasResource) {
                Debug.log("im groot");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Debug.err(e.getMessage());
                }
                MainScene.inventory.addResource();
                dropZone.normalSprite();
                hasResource = false;
                Debug.log("Resource processed");
            }

            // prevent CPU hogging
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Debug.err(e.getMessage());
            }
        }
    }

}