package org.cup.assets;

/**
 * Provides constants and functions to commonly used folders
 */
public class PathHelper {
    public static final String sprites = System.getProperty("user.dir") + "\\src\\main\\java\\org\\cup\\assets\\sprites\\";
    public static String getSpritePath(String name){
        return sprites + name;
    }
}
