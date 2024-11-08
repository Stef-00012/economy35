package org.cup.dev;

import org.cup.engine.core.Debug;

import org.cup.assets.PathHelper;
import org.cup.assets.logic.Economy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is active only during development and 
 * contains some keyboard shortcuts
 */
public class DevCommands implements KeyListener {

    private float balanceIncrese = 50;
    private float valueIncrese = 50;

    @Override
    public void keyTyped(KeyEvent e) {
        // 'm': add money to the balance
        // 'v': increase the product value

        if (!PathHelper.IS_DEV)
            return;

        if (e.getKeyChar() == 'm'){
            Economy.setBalance(Economy.getBalance() + balanceIncrese);
            System.out.println(Debug.ANSI_CYAN + "[DEV] " + Debug.ANSI_RESET + " added " + balanceIncrese + " to the balance");
        } else if (e.getKeyChar() == 'v'){
            Economy.setProductValue(Economy.getProductValue() + valueIncrese);
            System.out.println(Debug.ANSI_CYAN + "[DEV] " + Debug.ANSI_RESET + " added " + valueIncrese + " to the product value");
        }

    }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }


}