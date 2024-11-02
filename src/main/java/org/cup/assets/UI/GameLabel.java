package org.cup.assets.UI;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class GameLabel extends JLabel {
    public GameLabel(String text) {
        super(text);

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("A.ttf")));
        } catch (Exception e) {
            // Handle exception
        }
    }
}
