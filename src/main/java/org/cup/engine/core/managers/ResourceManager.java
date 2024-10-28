package org.cup.engine.core.managers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.cup.engine.core.Debug;

public class ResourceManager {
    private static class ImageKey {
        final String path;
        final int width;
        final int height;
        
        ImageKey(String path, int width, int height) {
            this.path = path;
            this.width = width;
            this.height = height;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ImageKey)) return false;
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
    
    private static final Map<ImageKey, Image> imageCache = new ConcurrentHashMap<>();
    private static final Map<String, BufferedImage> originalImages = new ConcurrentHashMap<>();
    
    public static Image getImage(String path) {
        return getImage(path, -1, -1);
    }
    
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
    
    private static Image createScaledImage(BufferedImage original, int width, int height) {
        // Create scaled instance using better quality algorithm
        Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        
        // Convert to BufferedImage for better performance
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        
        return buffered;
    }
    
    public static void clearAll() {
        imageCache.clear();
        originalImages.clear();
    }
    
    // Get memory usage statistics
    public static Map<String, Integer> getMemoryStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("CacheSize", imageCache.size());
        stats.put("OriginalImagesSize", originalImages.size());
        return stats;
    }
}