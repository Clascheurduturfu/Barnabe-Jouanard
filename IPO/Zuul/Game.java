/**
 * Application entry point for the Zuul-style Pokémon adventure game.
 * <p>
 * This class wires the {@link GameEngine} to the {@link UserInterface} so that
 * player input is displayed and processed through a single {@code main}-less
 * bootstrap (typical BlueJ style: instantiate {@code Game} to start).
 * </p>
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Game {
    /** Swing-based view that shows text, images, and command controls. */
    private UserInterface aGui;
    /** Core game logic: rooms, parser, and command interpretation. */
    private GameEngine aEngine;
    /** Asset manager responsible for downloading missing files at startup. */
    private AssetManager aAssetManager;

    /**
     * Constructs the game by creating the engine and GUI, then linking them.
     * <p>
     * The GUI receives a reference to the engine; the engine is given the GUI
     * so it can print messages and update the display after construction.
     * </p>
     */
    public Game() {
        this.aAssetManager = new AssetManager();
        if (!aAssetManager.allAssetsCached()) {
            LoadingScreen vLoading = new LoadingScreen(aAssetManager);
            vLoading.startAndWait();
        }

        this.aEngine = new GameEngine();
        this.aGui = new UserInterface(this.aEngine);
        this.aEngine.setGUI(this.aGui);
    } // Game()
} // Game
