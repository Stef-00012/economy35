package org.cup.engine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cup.engine.core.Debug;
import org.cup.engine.core.managers.GameManager;

public class Utils {
    /**
     * Helper function for linear interpolation.
     * @param a - Start.
     * @param b - End.
     * @param t - Time.
     * @return The lerped value
     */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static Image tryGetImage(String imageSrc){
        try {
            return ImageIO.read(new File(imageSrc));
        } catch (IOException e) {
            Debug.engineLogErr("Failed to load " + imageSrc);
            System.exit(-1);
        }
        return null;
    }

    public static BufferedImage tryGetBufferedImage(String imageSrc){
        try {
            return ImageIO.read(new File(imageSrc));
        } catch (IOException e) {
            Debug.engineLogErr("Failed to load " + imageSrc);
            System.exit(-1);
        }
        return null;
    }

    public static <T> void reverseArray(T[] a){
        // Reverse the array
        int left = 0;
        int right = a.length - 1;
        
        while (left < right) {
            // Swap the elements
            T temp = a[left];
            a[left] = a[right];
            a[right] = temp;
            
            left++;
            right--;
        }
    }

    public static int getGameWindowHeight(){
        final int TITLE_BAR = 39;
        return GameManager.game.getHeight() - TITLE_BAR;
    }
}
