## Overview of the Core Engine

The engine organizes the game into a **hierarchical scene graph** using **nodes**. Each node represents a game object, encapsulating transformations (position, rotation, scaling) and behaviors. Nodes can have children, creating a parent-child structure that allows properties and updates to cascade through the hierarchy.

The engine provides specialized managers, rendering utilities, and a game loop to simplify game creation while ensuring efficiency and scalability.

---

## Class Structure and Responsibilities

### Utility and Core Classes

#### `Utils`
A general-purpose utility class providing helper functions that can be used throughout the engine for miscellaneous operations.

#### `Vector`
Represents a mathematical 2D vector with operations for movement, rotation, scaling, and other vector-based calculations. This is a fundamental class used in the **Transform** system for positioning, scaling, and rotating objects in 2D space.

---

### Core Package (`core`)

#### **`Game`**
- The main interface for the engine.
- Extends `JFrame` to initialize the game window.
- Responsible for:
  - Creating and managing the **RootNode** (entry point for the scene graph).
  - Integrating Swing UI components and game scenes.
  - Starting the game loop using the **RootNode**.
- **Key Features**:
  - Manages game scenes and their transitions.
  - Provides methods for native Swing integration.

#### **`GameManager`**
- Centralized management of core game functionalities such as:
  - Timekeeping for fixed and delta time-based updates.
  - Interfacing with **GraphicsManager** for rendering coordination.
- Manages global state and links critical systems like graphics and sound to the game.

#### **`PerformanceMonitor`**
- Tracks performance metrics, such as CPU usage, for debugging optimization.

#### **`Debug`**
- Provides logging utilities for developers:
  - `Debug.log`: General-purpose logging.
  - `Debug.warn`: Warnings that may require attention.
  - `Debug.err`: Critical errors needing immediate action.

---

### Node System (`core.nodes`)

#### **`Node`**
- Base class for all game objects.
- Supports hierarchical structures:
  - Can have parent and child nodes.
  - Parent transformations cascade to children.
- Lifecycle methods:
  - `init`: Called during initialization.
  - `onEnable`: Called when the node is activated.
  - `onUpdate`: Called during each frame update.
  - `onDisable`: Called when the node is deactivated.

#### **`GameNode`**
- Extends `Node` with game-specific functionality.
- Designed for common use cases where objects need position, rotation, and scaling via a **Transform** component.

#### **`RootNode`**
- Specialized `Node` that serves as the root of the scene graph.
- Responsibilities:
  - Manages the game loop by implementing `Runnable`.
  - Updates all child nodes and propagates changes through the graph.
  - Interfaces with the `GameManager` for game-wide updates.

#### **`Scene`**
- Abstract class representing a game scene.
- Contains lifecycle methods:
  - `init`: Define the initial setup of the scene.
  - `onEnable`: Activate the scene.
  - `onDisable`: Deactivate the scene.
- Designed to contain multiple nodes and manage their interactions.

---

### Components System (`core.nodes.components`)

#### **`Renderer`**
- Abstract class for rendering game objects.
- Works with **GraphicsManager** and **Painter** to draw objects.
- Assigns each renderer a layer to control draw order (higher layers are drawn last).
- Subclasses must implement:
  - `render(Graphics2D g)`: Defines the specific rendering behavior.

---

### Specialized Components (`core.nodes.components.defaults`)

#### **`Transform`**
- Manages position, scale, and rotation of a node in 2D space.
- Supports hierarchical transformations:
  - Child nodes inherit and combine transformations from their parent nodes.
- Optimized using a **dirty flag** mechanism:
  - Changes trigger recalculation only when absolute values are requested.

#### **`SpriteRenderer`**
- Renders a static image (sprite) on the screen.
- Supports layer-based ordering for draw priority.

#### **`SquareRenderer`**
- Renders simple geometric shapes (squares).
- Ideal for debugging or minimalist designs.

#### **`Animation`**
- Represents a frame-based animation.
- Stores an array of image paths or frame objects.

#### **`Animator`**
- Plays and manages animations for a node.

---

### Managers (`core.managers`)

#### **`ResourceManager`**
- Handles resource loading, caching, and scaling for images.
- Key Features:
  - Two-level caching:
    - **Original Cache**: Stores raw, unscaled images.
    - **Scaled Cache**: Stores scaled versions of images.
  - Thread-safe access and memory monitoring.
  - Scales images on demand to optimize memory usage.

#### **`RenderingQueue`**
- Manages a prioritized list of renderers.
- Ensures objects are drawn in the correct order using a layer-based system.
- Optimized with binary search for efficient insertion and retrieval.

#### **`GraphicsManager`**
- Coordinates rendering operations.
- Manages a **Painter** instance to interface with the Swing rendering system.
- Works with the rendering queue to sort and draw objects.

#### **`Painter`**
- Custom `JPanel` responsible for rendering game components.
- Supports scaling, anti-aliasing, and frame rendering.

#### **`SoundManager`**
- Manages sound effects and music.
- Features:
  - Load and play audio files (`.wav` format).
  - Adjust volume and loop settings.
  - Reuse instances for memory efficiency.

---

### Additional Concepts

#### **Threading**
- The main game loop runs on a separate thread to prevent blocking UI operations.
- Graphics rendering uses `SwingUtilities.invokeLater` for thread safety.
- Resource loading uses thread-safe mechanisms like `ConcurrentHashMap`.

#### **Best Practices**
1. **Scene Organization**:
   - Use `Scene` objects to encapsulate and separate game logic.
2. **Resource Management**:
   - Leverage `ResourceManager` for efficient image and sound handling.
3. **Transform Hierarchies**:
   - Utilize parent-child relationships for logical grouping and cascading transformations.
4. **Layered Rendering**:
   - Assign appropriate layers to renderers to control visual stacking.

### Getting Started
**Basic Setup**

```java
public class Main {
    public static void main(String[] args) {
        // Initialize the game window
        Game game = new Game("My Game", 1280, 720, "path/to/icon.png");

        // Create and add a scene
        MainScene mainScene = new MainScene();
        game.addScene(mainScene);
    }
}
```

**Creating a Scene**

```java
public class MyScene extends Scene {
    @Override
    public void init() {
        // Initialize scene objects
        GameNode player = new GameNode();
        addChild(player);
    }

    @Override
    public void onEnable() {
        // Called when the scene becomes active
    }
}
```

**Adding Sprites and Animations**

```java
// Create a sprite renderer
SpriteRenderer sprite = new SpriteRenderer("path/to/sprite.png", transform, 1);
gameNode.addChild(sprite);

// Create an animator
Animator animator = new Animator(transform, 1);
animator.addAnimation("idle", new Animation(new String[]{"frame1.png", "frame2.png"}));
animator.play("idle");
gameNode.addChild(animator);
```

**Transform**

```java
Transform transform = new Transform();
transform.setPosition(new Vector(100, 100));
transform.setScale(new Vector(2, 2));
transform.setRotation(Math.PI / 4);
```

**Audio**
```java
// pathToFile, loop?, volume					
Clip musicClip = SoundManager.createClip("path/to/music.wav", true, 0.5);
SoundManager.setVolume(musicClip, 0.8);
SoundManager.stopClip(musicClip);
```