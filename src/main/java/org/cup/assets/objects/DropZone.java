package org.cup.assets.objects;

import org.cup.assets.PathHelper;
import org.cup.assets.logic.DropZoneThread;
import org.cup.engine.Vector;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;

public class DropZone extends GameNode {
    private final String dropZoneSprite = PathHelper.getSpritePath("dropzone/dropZone.png");
    private final String dropZoneSpriteFull = PathHelper.getSpritePath("dropzone/dropZoneWResource.png");
    private SpriteRenderer sr = new SpriteRenderer(dropZoneSprite, transform, 43);

    private DropZoneThread thread;

    public DropZone(Vector position) {
        transform.setPosition(position);
    }

    public void init() {
        sr.setPivot(Renderer.RIGHT_PIVOT);
        transform.setScale(new Vector(25, 50));
        addChild(sr);

        thread = new DropZoneThread(this);
        thread.start();
    }

    public boolean addResouce() {
        if (thread.placeResource()) {
            sr.setSprite(dropZoneSpriteFull);
            return true;
        }
        return false;
    }

    public void normalSprite() {
        sr.setSprite(dropZoneSprite);
    }
}
