package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implements the {@code quit} command, ending the game when the syntax is valid.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class QuitCommand extends Command {
    /** Creates the command. */
    public QuitCommand() {}

    /**
     * Ends the game when no extra word is provided.
     *
     * @param pGameEngine the game engine to close
     * @return {@code true} when the game is ending
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (this.hasSecondWord()) {
            pGameEngine.getGui().println("Quit what?");
            return false;
    }
        pGameEngine.getGui().println("Thank you for playing! Goodbye.");
        pGameEngine.endGame();
        return true;
    } // execute()
}
