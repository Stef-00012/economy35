package engine.core.nodes.components.defaults;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import engine.Debug;
import engine.Vector;
import engine.core.managers.GameManager;
import engine.core.nodes.components.Renderer;

public class Sprite extends Renderer {
    public boolean useFastResizing = false;

    private Transform transform;
    private Image image;
    private JPanel observer;

    private Vector previousScale;

    public Sprite(String imageSrc, Transform transform, int layer) {
        super(layer);
        try {
            image = ImageIO.read(new File(imageSrc));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        this.transform = transform;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        observer = GameManager.graphicsManager.getPainter();
    }

    @Override
    public void render(Graphics2D g) {
        Vector scale = transform.getScale();
        Vector pos = transform.getPosition().subtract(scale.divide(2)); // Center the square
        double rotation = transform.getRotation();
        
        if(scale != previousScale){
            image = image.getScaledInstance(scale.getX(), scale.getY(), useFastResizing ? Image.SCALE_FAST : Image.SCALE_SMOOTH);
            previousScale = scale;
        }
        
        g.rotate(rotation);
        ((Graphics) g).drawImage(image, pos.getX(), pos.getY(), scale.getX(), scale.getY(), observer);

        // Reset color and rotation
        g.rotate(-rotation);
        g.setColor(Color.black);
    }
}
