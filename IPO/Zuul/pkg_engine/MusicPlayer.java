package pkg_engine;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

/** Plays an MP3 file in a loop on a background thread. */
public class MusicPlayer {
    /** MP3 file path to play on loop. */
    private String aFilePath;

    /** Active JLayer player instance for the current playback cycle. */
    private Player aPlayer;

    /** Background thread responsible for continuous playback. */
    private Thread aThread;

    /** Indicates whether playback loop should keep running. */
    private boolean aIsPlaying;

    /**
     * Creates a music player for the given MP3 path.
     *
     * @param pFilePath the local MP3 file path
     */
    public MusicPlayer(final String pFilePath) {
        this.aFilePath = pFilePath;
        this.aIsPlaying = false;
    } // MusicPlayer()

    /** Starts playing the MP3 in a loop on a background thread. */
    public void playLoop() {
        this.aIsPlaying = true;
        this.aThread =
                new Thread(
                        () -> {
                            while (this.aIsPlaying) {
                                try {
                                    FileInputStream vFIS = new FileInputStream(this.aFilePath);
                                    this.aPlayer = new Player(vFIS);
                                    this.aPlayer.play();
                                    this.aPlayer.close();
                                } catch (Exception e) { // playLoop()
                                    System.out.println("Error playing music: " + e.getMessage());
                                    this.aIsPlaying = false;
                                }
                            }
                        });
        this.aThread.setDaemon(true);
        this.aThread.start();
    } // playLoop()

    /** Stops the music. */
    public void stop() {
        this.aIsPlaying = false;
        if (this.aPlayer != null) {
            this.aPlayer.close();
    }
    } // stop()
}
