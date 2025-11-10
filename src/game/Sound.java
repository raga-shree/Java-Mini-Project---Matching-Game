package game;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;


/**
 * Improved Sound utility class.
 * Simplifies audio playback using Clip (short sound effects).
 * 
 * Features:
 * - play(), stop(), loop()
 * - adjustable volume
 * - supports loading from URL or File
 */

public class Sound {

    private Clip clip;
    private FloatControl volumeControl;

    /** Load a sound from a resource URL */
    public Sound(URL url) {
        if (url == null) {
            System.out.println("Sound: null URL provided.");
            return;
        }
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            initClip(audioIn);
        } catch (Exception e) {
            System.out.println("Sound: failed to load from URL - " + e.getMessage());
        }
    }
    
    /** Initialize clip and controls */
    private void initClip(AudioInputStream audioIn) throws LineUnavailableException, IOException {
        clip = AudioSystem.getClip();
        clip.open(audioIn);

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
    }

    /** Play once from start */
    public void play() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /** Loop continuously until stop() is called */
    public void loop() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** Stop the sound */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    /** Adjust volume in decibels (-80 to +6). 0 = default volume */
    public void setVolume(float dB) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float clamped = Math.max(min, Math.min(dB, max));
            volumeControl.setValue(clamped);
        }
    }

    /** Close the clip and release resources */
    public void close() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
