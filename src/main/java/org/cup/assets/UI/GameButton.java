package org.cup.assets.UI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GameButton extends JButton {
    public GameButton(String text){
        super(text);
        this.setFont(new Font("Arial", Font.BOLD, 12));
        this.setBackground(new Color(240, 240, 240));
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setFocusable(false);
    }
}
