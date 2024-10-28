package org.cup.engine.core.managers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import org.cup.engine.core.Debug;

/**
 * A resource management system that handles loading, caching, and scaling of
 * images.
 * This class provides efficient image resource management with the following
 * features:
 * <ul>
 * <li>Automatic caching of loaded images</li>
 * <li>On-demand image scaling</li>
 * <li>Thread-safe resource access</li>
 * <li>Memory usage monitoring</li>
 * </ul>
 * 
 * The manager uses two levels of caching:
 * <ul>
 * <li>Original images cache: Stores the initially loaded images</li>
 * <li>Scaled images cache: Stores images that have been scaled to specific
 * dimensions</li>
 * </ul>
 */
public class ResourceManager {

    /**
     * Inner class representing a unique key for cached images.
     * This class combines the image path and dimensions to create a unique
     * identifier
     * for cached image resources.
     */
    private static class ImageKey {
        final String path;
        final int width;
        final int height;

        /**
         * Constructs a new ImageKey with specified path and dimensions.
         *
         * @param path   The file path of the image
         * @param width  The desired width of the image
         * @param height The desired height of the image
         */
        ImageKey(String path, int width, int height) {
            this.path = path;
            this.width = width;
            this.height = height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof ImageKey))
                return false;
            ImageKey key = (ImageKey) o;
            return width == key.width &&
                    height == key.height &&
                    path.equals(key.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, width, height);
        }
    }

    /** Thread-safe cache for storing scaled images */
    private static final Map<ImageKey, Image> imageCache = new ConcurrentHashMap<>();

    /** Thread-safe cache for storing original, unscaled images */
    private static final Map<String, BufferedImage> originalImages = new ConcurrentHashMap<>();

    /**
     * Retrieves an image from the specified path using its original dimensions.
     *
     * @param path The file path to the image resource
     * @return The loaded image, or null if the image couldn't be loaded
     */
    public static Image getImage(String path) {
        return getImage(path, -1, -1);
    }

    /**
     * Retrieves an image from the specified path and scales it to the given
     * dimensions.
     * If the image exists in the cache, it is returned immediately. Otherwise, the
     * image
     * is loaded from disk, scaled if necessary, and cached for future use.
     *
     * @param path   The file path to the image resource
     * @param width  The desired width of the image (-1 for original width)
     * @param height The desired height of the image (-1 for original height)
     * @return The loaded and potentially scaled image, or null if the image
     *         couldn't be loaded
     */
    public static Image getImage(String path, int width, int height) {
        ImageKey key = new ImageKey(path, width, height);

        // Try to get from cache first
        Image img = imageCache.get(key);

        if (img != null) {
            return img;
        }

        // Load or get original image
        BufferedImage original = originalImages.computeIfAbsent(path, k -> {
            try {
                return ImageIO.read(new File(k));
            } catch (IOException e) {
                Debug.engineLogErr("Failed to load image: " + k);
                return null;
            }
        });

        if (original == null) {
            return null;
        }

        // Scale if dimensions are provided
        if (width > 0 && height > 0) {
            Debug.warn("Scaling: " + path + " " + width + "x" + height);
            img = createScaledImage(original, width, height);
        } else {
            img = original;
        }

        // Cache the result directly
        imageCache.put(key, img);
        return img;
    }

    /**
     * Creates a scaled version of the provided image using high-quality scaling
     * algorithms.
     * The scaling process uses SCALE_FAST for initial scaling and bilinear
     * interpolation
     * for final rendering.
     *
     * @param original The original image to scale
     * @param width    The target width
     * @param height   The target height
     * @return A new scaled instance of the original image
     */
    private static Image createScaledImage(BufferedImage original, int width, int height) {
        // Create scaled instance using better quality algorithm
        Image scaled = original.getScaledInstance(width, height, Image.SCALE_FAST);

        // Convert to BufferedImage for better performance
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();

        return buffered;
    }

    /**
     * Clears all cached images from both the scaled and original image caches.
     * This method should be called when resources need to be freed or when
     * reloading resources is necessary.
     */
    public static void clearAll() {
        imageCache.clear();
        originalImages.clear();
    }

    /**
     * Retrieves statistics about the current memory usage of the resource manager.
     *
     * @return A map containing the following statistics:
     *         <ul>
     *         <li>"CacheSize": Number of scaled images in cache</li>
     *         <li>"OriginalImagesSize": Number of original images in cache</li>
     *         </ul>
     */
    public static Map<String, Integer> getMemoryStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("CacheSize", imageCache.size());
        stats.put("OriginalImagesSize", originalImages.size());
        return stats;
    }
}