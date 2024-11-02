package org.cup.assets;

import java.io.File;

import org.cup.engine.core.Debug;

/**
 * Provides constants and functions to commonly used folders
 */
public class PathHelper {
    private static final boolean IS_DEV = new File("src").exists();

    public static final String sprites = IS_DEV
            ? String.join(File.separator, System.getProperty("user.dir"), "src", "main", "java", "org", "cup", "assets",
                    "sprites") + File.separator
            : String.join(File.separator, System.getProperty("user.dir"), "assets", "sprites") + File.separator;

    public static final String fonts = IS_DEV
            ? String.join(File.separator, System.getProperty("user.dir"), "src", "main", "java", "org", "cup", "assets",
                    "UI", "fonts") + File.separator
            : String.join(File.separator, System.getProperty("user.dir"), "assets", "fonts") + File.separator;

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
            Debug.engineLogErr("The provided path(" + folderPath + ") is not a valid directory.");
        }

        return null;
    }

    public static String getSpritePath(String name) {
        return String.join(File.separator, sprites, name);
    }
}
