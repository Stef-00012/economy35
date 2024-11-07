package org.cup.engine.core.managers.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.cup.engine.core.Debug;

public class SoundManager {
    public SoundManager() {

    }

    public static Clip createClip(String filePath, boolean loop) {

        Clip clip;
        AudioInputStream audioInputStream;

        try {
            // create AudioInputStream object
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            // create clip reference
            clip = AudioSystem.getClip();
            // open audioInputStream to the clip
            clip.open(audioInputStream);
        } catch (Exception e) {
            Debug.engineLogErr(e.getMessage());
            return null;
        }
        if (loop)
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        return clip;
    }

    public static Clip createClip(String filePath) {
        return createClip(filePath, false);
    }

    public static void playClip(Clip c){
        c.setMicrosecondPosition(0);
        c.start();
    }
}
