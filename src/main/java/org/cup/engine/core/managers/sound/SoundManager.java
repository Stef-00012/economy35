package org.cup.engine.core.managers.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.cup.engine.core.Debug;

public class SoundManager {
    public SoundManager() {

    }

    public static Clip createClip(String filePath, boolean loop, double volume) {
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


        if(volume > 0 && volume < 1){
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
        
        return clip;
    }

    public static Clip createClip(String filePath) {
        return createClip(filePath, false, 1);
    }

    public static Clip createClip(String filePath, boolean loop) {
        return createClip(filePath, loop, 1);
    }

    public static void playClip(Clip c){
        c.setMicrosecondPosition(0);
        c.start();
    }

}
