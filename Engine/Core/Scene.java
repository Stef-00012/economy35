package Engine.Core;

/**
 * 
 */
public abstract class Scene extends Node {
    public Scene() {
        super(null);
        initObjects();
    }

    abstract public void initObjects();
}
