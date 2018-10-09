import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * The Audio class controls all in-game audio.
 *
 * 16310943 James Byrne
 * 16314763 Jakub Gajewski
 * 16305706 Mark Hartnett
 */

public class Audio {
    private Clip clip;

    /**
     * Creates an audio object which will play the specified sound if not muted
     * @param sound the sound specified by the enum Sounds
     */
    public Audio(Sounds sound) {
            String path = "audio/";
            /** Select audio file path based on specified enum */
            switch (sound) {
                case INTRO:     // Friends theme song
                    path += "Friends.wav";
                    break;
                case FAIL:      // Failure sounds
                    path += "removed.wav";
                    break;
                case PARTY:     // Party horn
                    path += "winner.wav";
                    break;
            }
            /** Open and start audio clip */
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
    }

    /**
     * Stop audio clip if one is running
     */
    public void stop(){
        if (clip.isRunning())
            clip.stop();
    }
}
