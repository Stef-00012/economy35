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
            // Create AudioInputStream object
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            // Create clip reference
            clip = AudioSystem.getClip();
            // Open audioInputStream to the clip
            clip.open(audioInputStream);
        } catch (Exception e) {
            Debug.engineLogErr(e.getMessage());
            return null;
        }

        volume(clip, volume);

        // Set loop if required
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        return clip;
    }

    public static float volume(Clip clip, double volume){
        // Set volume
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (volume > 0 && volume <= 1) {
            // Convert volume (0.0 to 1.0) to decibels
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        } else if (volume > 1) {
            gainControl.setValue(gainControl.getMaximum());
        } else {
            gainControl.setValue(gainControl.getMinimum());
        }
        return (float) volume;
    }

    public static Clip createClip(String filePath) {
        return createClip(filePath, false, 1.0);
    }

    public static Clip createClip(String filePath, boolean loop) {
        return createClip(filePath, loop, 1.0);
    }

    public static void playClip(Clip c) {
        c.setMicrosecondPosition(0);
        c.start();

    }

    public static void stopClip(Clip c){
        new Thread(()-> {
            for(double i = 1; i > 0; i -= 0.1){
                try {
                    FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(volume(c, i));
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
        }).start();

        c.stop();
        System.out.println("Audio stopped");
    }
}
