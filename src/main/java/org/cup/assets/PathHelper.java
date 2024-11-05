package org.cup.assets;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

import org.cup.engine.core.Debug;

/**
 * Provides constants and functions to commonly used folders
 */
public class PathHelper {
    private static final boolean IS_DEV = new File("src").exists();

    public static final String assets = IS_DEV
            ? String.join(File.separator, System.getProperty("user.dir"), "src", "main", "java", "org", "cup", "assets")
                    + File.separator
            : String.join(File.separator, System.getProperty("user.dir"), "main") + File.separator;

    public static final String sprites = Paths.get(assets, "sprites") + File.separator;

    public static final String icons = Paths.get(sprites, "icons") + File.separator;

    public static final String fonts = Paths.get(assets, "UI", "fonts") + File.separator;

    public static final String audio = Paths.get(assets, "audio") + File.separator;
    public static final String SFX = Paths.get(audio, "SFX") + File.separator;
    public static final String music = Paths.get(audio, "music") + File.separator;

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

            // Make sure the files are sorted based on the number after the - (es. Sprite-10, Sprite-11)
            Arrays.sort(filePaths, new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    int numA = extractNumber(a);
                    int numB = extractNumber(b);
                    return Integer.compare(numA, numB);
                }

                private int extractNumber(String filePath) {
                    String[] parts = filePath.split("-|\\.png");
                    return Integer.parseInt(parts[parts.length - 1]);
                }
            });
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
