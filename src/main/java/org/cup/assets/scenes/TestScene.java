package org.cup.assets.scenes;

import org.cup.assets.objects.Employee;
import org.cup.engine.core.nodes.Scene;

public class TestScene extends Scene {

    @Override
    public void init() {
        addChild(new Employee());
    }
    
}
