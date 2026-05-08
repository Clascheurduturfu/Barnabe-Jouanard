package pkg_engine;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

/** Plays an MP3 file in a loop on a background thread. */
public class MusicPlayer {
    private String aFilePath;
    private Player aPlayer;
    private Thread aThread;
    private boolean aIsPlaying;

    /**
     * Creates a music player for the given MP3 path.
     *
     * @param pFilePath the local MP3 file path
     */
    public MusicPlayer(final String pFilePath) {
        this.aFilePath = pFilePath;
        this.aIsPlaying = false;
    }

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
                                } catch (Exception e) {
                                    System.out.println("Error playing music: " + e.getMessage());
                                    this.aIsPlaying = false;
                                }
                            }
                        });
        this.aThread.setDaemon(true);
        this.aThread.start();
    }

    /** Stops the music. */
    public void stop() {
        this.aIsPlaying = false;
        if (this.aPlayer != null) {
            this.aPlayer.close();
        }
    }
}
