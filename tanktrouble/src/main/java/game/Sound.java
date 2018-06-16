package game;

import javafx.scene.media.AudioClip;

import java.net.URL;

/**
 * class to generate sound effects
 */
public class Sound {

    private AudioClip destroy;//tank explode sound effect
    private AudioClip mud;//mud sound effect
    private AudioClip collide;//bush sound effect

    public Sound() {
        //initialize audio clip for explosion
        String soundResourceName = "explosion.wav";
        URL soundSourceResource = null;
        try {
            soundSourceResource = getClass().getClassLoader().getResource(soundResourceName);
        } catch (Exception e) {
        }
        destroy = new AudioClip(soundSourceResource.toExternalForm());

        //initialize audio clip for mud splashes
        soundResourceName = "mud.wav";
        try {
            soundSourceResource = getClass().getClassLoader().getResource(soundResourceName);
        } catch (Exception e) {
        }
        mud = new AudioClip(soundSourceResource.toExternalForm());

        //initialize audio clip for collision
        soundResourceName = "collide.wav";
        try {
            soundSourceResource = getClass().getClassLoader().getResource(soundResourceName);
        } catch (Exception e) {
        }
        collide = new AudioClip(soundSourceResource.toExternalForm());
    }

    /**
     * play explosion sound
     */
    public void playTankDestroy() {
        destroy.play();
    }

    /**
     * play mud splash sound
     */
    public void playMudSound() {
        if (!mud.isPlaying())//if mud sound is already playing
            mud.play(0.05);
    }

    /**
     * play bush sound effect
     */
    public void playCollideSound() {
        if (!collide.isPlaying())//if collision sound is already playing
            collide.play(0.1);
    }
}
