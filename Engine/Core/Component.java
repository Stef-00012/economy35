package Engine.Core;

public abstract class Component {
    // This flag tracks whether the component has been initialized (to prevent multiple setups)
    private boolean isInitialized = false;

    /**
     * This method is the base setup method, ensuring that initialization happens only once
     * DO NOT OVERRIDE THIS METHIOD, OVERRIDE `setup()` INSTEAD
     */
    protected void baseSetup() {
        if (!isInitialized) {
            setup();  // Custom setup logic implemented by the subclass
            isInitialized = true;
        }

        // Call onEnable() every time the node is activated 
        onEnable(); 
    }

    public void baseUpdate() {
        update();  // Custom update logic implemented by the subclass
    }

    /**
     * This method is used for one-time setup or initialization of the component
     */
    public abstract void setup();

    /**
     * This method is called every time the node containing this component is activated
     */
    public abstract void onEnable();

    /**
     * Method for updating the component every frame (or on every update cycle)
     */
    public abstract void update();
}
