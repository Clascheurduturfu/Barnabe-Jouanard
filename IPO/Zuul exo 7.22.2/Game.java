/**
 * Class Game - the main engine of the Zuul adventure game.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Game
{
    private UserInterface aGui;
    private GameEngine aEngine;
   
    /**
     * Default constructor: initializes the engine and the gui.
     */
    public Game()
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    } // Game()
} // Game
