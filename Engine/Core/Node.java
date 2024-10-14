package Engine.Core;

import java.util.ArrayList;

public class Node extends Thread {
    private Node parentNode; // If null, this is the root node
    private ArrayList<Node> childNodes;

    private Component[] components;

    private boolean active;

    // #region Constructors
    public Node(Component[] components) {
        this.childNodes = new ArrayList<>();

        // If the passed components array is null, initialize an empty array
        if (components == null) {
            this.components = new Component[0];
        } else {
            this.components = components;
        }
    }
    // #endregion

    /**
     * Method to initialize the node and its components
     * It gets called on the thread start
     * YOU SHOULDN'T DIRECTLY CALL THIS METHOD
     */
    protected void setup() {
        System.out.println("Setup called " + getClass().getName());
        active = true;
        startChildNodes();

        // Initialize Components
        for (int i = 0; i < components.length; i++) {
            components[i].baseSetup();
        }
    }

    protected void update() {
        // Update Components
        for (int i = 0; i < components.length; i++) {
            components[i].baseUpdate();
        }
    }

    /**
     * Disable this node (set its active status to false)
     */
    public void disable() {
        active = false;
    }

    /**
     * Start all child node threads
     */
    private void startChildNodes() {
        for (int i = 0; i < childNodes.size(); i++) {
            childNodes.get(i).start(); // Start the thread for each child node.
        }
    }

    @Override
    public void run() {
        // If this node is not the root and the parent node is not active, throw an
        // error
        if (!isRoot() && !parentNode.active)
            throw new Error("Can't run node inside of a disabled object " + getClass().getName());

        setup(); // Call the setup method to initialize components.

        // Main loop: While the node and parent node are active, continue updating.
        while (active && (isRoot() || parentNode.active)) {
            update();
        }
    }

    /**
     * Check if this node is the root node (has no parent).
     * 
     * @return true if this node has a parent, false otherwise.
     */
    public boolean isRoot() {
        return parentNode == null;
    }

    public void addChild(Node node) {
        childNodes.add(node);
        if (active)
            node.start();
    }

}
