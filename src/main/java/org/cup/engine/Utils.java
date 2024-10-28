package org.cup.engine;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cup.engine.core.Debug;

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
}
