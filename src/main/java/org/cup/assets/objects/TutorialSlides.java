package org.cup.assets.objects;

import org.cup.assets.UI.GameLabel;
import org.cup.assets.scenes.MainScene;
import org.cup.engine.Vector;
import org.cup.engine.core.managers.GameManager;
import org.cup.engine.core.nodes.GameNode;
import org.cup.engine.core.nodes.Scene;
import org.cup.engine.core.nodes.components.Renderer;
import org.cup.engine.core.nodes.components.defaults.SpriteRenderer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;

public class TutorialSlides extends GameNode implements KeyListener {
    private class Slide {
        public String image;
        public String text;

        public Slide(String image, String text) {
            this.image = image;
            this.text = text;
        }
    }

    private Slide[] slides = new Slide[] {
            new Slide(
                    "",
                    "hello")
    };

    private SpriteRenderer sr;
    private int currentSlide;

    private GameLabel textLabel;

    public TutorialSlides() {
        transform.setScale(GameManager.game.getWindowDimentions().multiply(0.4));
        transform.setPosition(
                GameManager.game.getWindowDimentions().divide(2).add(new Vector(0, -transform.getScale().y)));

                textLabel = new GameLabel("<html><center>TEST</html></center>");
        // Set the alignment to center horizontally and vertically
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.CENTER);
        textLabel.setBounds(transform.getPosition().getX() - transform.getScale().getX() / 2,
                transform.getPosition().getY() + transform.getScale().getY(), transform.getScale().getX(), 100);

        sr = new SpriteRenderer(slides[0].image, transform, 1);
        sr.setPivot(Renderer.TOP_PIVOT);
        addChild(sr);

        GameManager.game.addKeyListener(this);
    }

    @Override
    public void init() {
        GameManager.game.addUIElement(textLabel);
    }

    private void nextSlide() {
        currentSlide++;
        if (currentSlide == slides.length) {
            GameManager.game.addScene(new MainScene());
            ((Scene) getParent()).disable();
            GameManager.game.removeScene((Scene) getParent());
            return;
        }
        loadSlide(slides[currentSlide]);
    }

    private void loadSlide(Slide s) {
        sr.setSprite(s.image);
        textLabel.setText(s.text);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        nextSlide();
    }

    @Override
    public void onDisable() {
        GameManager.game.removeUIElement(textLabel);
        GameManager.game.removeKeyListener(this);
    }
}
