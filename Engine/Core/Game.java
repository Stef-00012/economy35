package Engine.Core;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Class that serves as interface for core engine package
 */
public class Game extends JFrame {
    private Node root;

    /**
     * Creates an instance of the Game class
     * @param title - The window title
     * @param width - The width of the window
     * @param height - The height of the window
     * @param icon - The icon of the image relative to the root project folder
     */
    public Game(String title, int width, int height, String icon) {
        this.setTitle(title);

        this.setSize(width, height);
        this.setResizable(false); // For the scope of this game it's better not to handle responsivenes

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon iconImage = new ImageIcon(icon);
        this.setIconImage(iconImage.getImage());

        GraphicsManager graphicsManager = new GraphicsManager();
        GameManager.initialize(this, graphicsManager);

        this.add(graphicsManager.getPainter()); // Adds the JPanel managed by the GraphicsManager to the JFrame

        this.setVisible(true);

        root = new Node(new Component[] { graphicsManager });

        paint(getGraphics());
    }

    /**
     * Adds a Scene as a child of the root node
     * @param scene - The scene to add
     */
    public void addScene(Scene scene) {
        root.addChild(scene);
    }

    /**
     * Starts the root node and all its subnodes
     */
    public void startGame() {
        root.start();
    }
}
