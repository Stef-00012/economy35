package Engine.Core;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends JFrame {
    private Node root;

    public Game(String title, int width, int height, String icon) {
        this.setTitle(title);

        this.setSize(width, height);
        this.setResizable(false); // For the scope of this game it's better not to handle responsivenes

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon iconImage = new ImageIcon(icon);
        this.setIconImage(iconImage.getImage());

        GraphicsManager graphicsManager = new GraphicsManager();
        GameManager.initialize(this, graphicsManager);
        this.add(graphicsManager.getPainter());
        this.setVisible(true);

        root = new Node(new Component[] { graphicsManager });

        paint(getGraphics());
    }

    public void addScene(Scene scene) {
        root.addChild(scene);
    }

    public void startGame() {
        root.start();
    }
}
