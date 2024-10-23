package org.cup.engine.core.nodes;

import org.cup.engine.core.errors.ParentDisabledException;

import java.util.ArrayList;

/**
 * The {@code Node} class represents a node in a hierarchical structure of game
 * objects.
 * Nodes can have parent and child nodes, and each node can be activated,
 * updated,
 * or disabled. This class provides the fundamental functionality to manage game
 * object lifecycles, including setup, update, and enable/disable behavior.
 * <p>
 * Subclasses should implement the key methods such as {@code init()},
 * {@code onEnable()}, {@code onUpdate()}, and {@code onDisable()} to define
 * specific
 * node behavior.
 * </p>
 */
public abstract class Node {
    private Node parentNode; // If null, this is the root node
    private final ArrayList<Node> childNodes;

    private boolean active;
    private boolean hasBeenInitialized;

    public Node() {
        this.childNodes = new ArrayList<>();
        hasBeenInitialized = false;
        active = false;
    }

    /**
     * Initializes this node and its child nodes. This method is called when the
     * node
     * is activated.
     * IT SHOULD NOT BE CALLED DIRECTLY BY THE USER CODE.
     * <p>
     * If the node is not the root and its parent is not active, an error is thrown.
     * After initialization, the node's {@code onEnable()} method is called,
     * followed
     * by the initialization of all child nodes.
     * </p>
     * <b>Note:</b> This method is intended to be called by the engine.
     *
     * @throws ParentDisabledException if this node's parent is inactive.
     */
    public void _setup() throws ParentDisabledException {
        if (!isRoot() && !parentNode.active)
            throw new ParentDisabledException("Can't run node inside of a disabled object " + getClass().getName());
            
        // Initialize the node only once
        if (!hasBeenInitialized) {
            init();
            hasBeenInitialized = true;
        }

        active = true;

        onEnable();

        // Setup child nodes
        for (int i = 0; i < childNodes.size(); i++) {
            childNodes.get(i)._setup();
        }
    }

    /**
     * Updates the node and its child nodes. It is called during the update phase
     * of the game loop.
     * IT SHOULD NOT BE CALLED DIRECTLY BY THE USER CODE.
     * <p>
     * The {@code onUpdate()} method is called for this node, followed by updating
     * child nodes if this node and its parent are both active.
     * </p>
     */
    public void _update() {
        if (active && parentNode.active) {
            onUpdate();
            // Update child nodes
            updateChildNodes();
        }
    }

    /**
     * Calls the _update() method on all the child nodes
     */
    protected void updateChildNodes() {
        for (int i = 0; i < childNodes.size(); i++) {
            childNodes.get(i)._update();
        }
    }

    /**
     * Disables this node by setting its {@code active} status to {@code false}.
     * The {@code onDisable()} method is called for this node and all of its child
     * nodes.
     */
    public void disable() {
        onDisable();
        for (int i = 0; i < childNodes.size(); i++) {
            childNodes.get(i).onDisable();
        }
        active = false;
    }

    /**
     * Checks if this node is the root node (i.e., it has no parent).
     *
     * @return {@code true} if this node has no parent, otherwise {@code false}.
     */
    public boolean isRoot() {
        return parentNode == null;
    }

    /**
     * Gets the parent node of this node.
     *
     * @return the parent node, or {@code null} if this node is the root.
     */
    public Node getParent() {
        return parentNode;
    }

    /**
     * Adds a child node to this node. If this node is already active, the child
     * node
     * is immediately activated via its {@code _setup()} method.
     *
     * @param node The child node to add.
     */
    public void addChild(Node node) {
        node.parentNode = this;
        childNodes.add(node);
        if (active)
            try {
                node._setup();
            } catch (ParentDisabledException e) {
                e.printStackTrace();
            }
    }

    /**
     * Removes a child node from this node.
     *
     * @param node The child node to remove.
     */
    public void removeChild(Node node) {
        childNodes.remove(node);
        node.parentNode = null;
    }

    public boolean isActive(){
        return active;
    }

    // #region Methods filled by the subclasses

    /**
     * Called once when the node is initialized. Subclasses should override this
     * method to initialize variables and other resources. This is similar to a
     * constructor but occurs within the game object lifecycle.
     */
    public void init() {
    }

    /**
     * Called when the node becomes active. This can happen when the node is first
     * initialized or when it is re-enabled after being disabled. Subclasses should
     * override this method to define specific behavior upon activation.
     */
    public void onEnable() {
    }

    /**
     * Called before the node is disabled. Subclasses should override this method to
     * handle any cleanup or state changes before the node becomes inactive.
     */
    public void onDisable() {
    }

    /**
     * Called every frame while the node is active. Subclasses should override this
     * method to implement per-frame behavior (e.g., moving game objects, processing
     * inputs, etc.).
     */
    public void onUpdate() {
    }

    // #endregion
}
