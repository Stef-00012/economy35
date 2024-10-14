package Scenes;

import Engine.Core.Scene;
import Objects.Test.TestObject;

public class GameScene extends Scene {

    @Override
    public void initObjects() {
        TestObject t = new TestObject();
        this.addChild(t);
    }
    
}
