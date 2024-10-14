package Objects.Test;

import Engine.Core.Node;
import Engine.Core.Component;

public class TestObject extends Node {
    public TestObject() {
        super(new Component[] { new TestController() });
    }
}
