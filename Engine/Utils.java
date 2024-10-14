package Engine;

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
}
