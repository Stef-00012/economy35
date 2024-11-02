package org.cup.assets.UI;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import org.cup.assets.PathHelper;
import org.cup.engine.core.Debug;

public class GameLabel extends JLabel {
    public GameLabel(String text) {
        super(text);

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font f = Font.createFont(Font.TRUETYPE_FONT, new File(PathHelper.fonts + "Font.ttf"));
            ge.registerFont(f);
            setFont(new Font(f.getFontName(), Font.PLAIN, 30));
            
            System.out.println(getFont());
        } catch (Exception e) {
            Debug.engineLogErr(e.getMessage());
        }

    }
}
