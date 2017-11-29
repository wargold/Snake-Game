import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A music player that contains all our songs
 * that would be played in the Game.
 *
 * @author War & Miro
 */
public class MusicPlayer {

    public String mainSong = "music/GunNRoses.wav";
    public String impactSong = "music/impact.wav";
    private InputStream musicStream;

    private AudioStream audioStream;
    private boolean musicPlaying;

    public MusicPlayer() {
        musicPlaying = false;
    }

    /**
     * @return true if the main song is playing.
     */
    public boolean ifPlaying() {
        return musicPlaying;
    }

    /**
     * Plays the mainSong
     */
    public void playMainSong() {
        playSound(mainSong);
        musicPlaying = true;
    }

    /**
     * Plays the ImpactSong
     */
    public void playImpactSong() {
        playSound(impactSong);
    }

    /**
     * @param audioFileIn the audio file that will be played
     */
    private void playSound(String audioFileIn) {
        String audioFile = audioFileIn;
        InputStream in = null;
        try {
            in = new FileInputStream(audioFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (audioFileIn.equals(mainSong)) {
            musicStream = audioStream;
        }

        AudioPlayer.player.start(audioStream);
    }

    /**
     * Stops the music from playing.
     */
    public void stopMusic() {
        AudioPlayer.player.stop(musicStream);
        musicPlaying = false;
    }
}
