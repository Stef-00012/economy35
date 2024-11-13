package org.cup.assets.scenes;

import org.cup.assets.objects.TutorialBackground;
import org.cup.assets.objects.TutorialSlides;
import org.cup.engine.core.nodes.Scene;

public class Tutorial extends Scene {
    @Override
    public void init() {
        addChild(new TutorialSlides());
        addChild(new TutorialBackground());
    }
    
}
