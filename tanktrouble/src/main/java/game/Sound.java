package game;

import javafx.scene.media.AudioClip;

import java.net.URL;


public class Sound {

    private AudioClip destroy;
    private AudioClip mud;
    private AudioClip collide;

    public Sound() {
        String soundResourceName = "explosion.wav";
        URL soundSourceResource = null;
        try {
            soundSourceResource = getClass().getClassLoader().getResource(soundResourceName);
        } catch (Exception e) {
        }
        destroy = new AudioClip(soundSourceResource.toExternalForm());

        soundResourceName = "mud.wav";
        try {
            soundSourceResource = getClass().getClassLoader().getResource(soundResourceName);
        } catch (Exception e) {
        }
        mud = new AudioClip(soundSourceResource.toExternalForm());

        soundResourceName = "collide.wav";
        try {
            soundSourceResource = getClass().getClassLoader().getResource(soundResourceName);
        } catch (Exception e) {
        }
        collide = new AudioClip(soundSourceResource.toExternalForm());
    }

    public void playTankDestroy() {
        destroy.play();
    }

    public void playMudSound() {
        if (!mud.isPlaying())
            mud.play(0.05);
    }

    public void playCollideSound() {
        if (!collide.isPlaying())
            collide.play(0.1);
    }
}
