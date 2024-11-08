package org.cup.assets.UI;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

import javax.swing.JLabel;

import org.cup.assets.PathHelper;
import org.cup.engine.core.Debug;

public class GameLabel extends JLabel {
    private Font baseFont;

    public GameLabel(String text) { 
        super(text);

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            baseFont = Font.createFont(Font.TRUETYPE_FONT, new File(PathHelper.fonts + "Itim-Regular.ttf"));
            ge.registerFont(baseFont);
            setFont(baseFont.deriveFont(Font.BOLD, 30));  // Default size 30
        } catch (Exception e) {
            Debug.engineLogErr(e.getMessage());
        }
    }

    /**
     * Sets the font size for this label.
     * @param size The new font size in points.
     */
    public void setFontSize(int size) {
        if (baseFont != null) {
            setFont(baseFont.deriveFont(Font.BOLD, size));
        }
    }
}
