package org.cup.assets;

import java.io.File;

import org.cup.engine.core.Debug;

/**
 * Provides constants and functions to commonly used folders
 */
public class PathHelper {
    public static final String sprites = System.getProperty("user.dir")
            + "\\src\\main\\java\\org\\cup\\assets\\sprites\\";

    public static String getSpritePath(String name) {
        return sprites + name;
    }

    public static String[] getFilePaths(String folderPath) {

        // Create a File object for the directory
        File folder = new File(folderPath);

        // Check if the folder exists and is a directory
        if (folder.exists() && folder.isDirectory()) {
            // Get the list of files and directories in the folder
            File[] files = folder.listFiles();
            
            String[] filePaths = new String[files.length];
            
            if (files != null) {
                // Iterate through the files
                for (int i = 0; i < filePaths.length; i++) {
                    File file = files[i];
                    // If the File object is not a directory, add its path to the list
                    if (file.isFile()) {
                        filePaths[i] = file.getAbsolutePath();
                    }
                }
            }
            return filePaths;
        } else {
            Debug.engineLogErr("The provided path is not a valid directory.");
        }

        return null;
    }
}
