package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implements the {@code look} command, reprinting the current room description.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class LookCommand extends Command {
    /** Creates the command. */
    public LookCommand() {}

    /**
     * Prints the current room description.
     *
     * @param pGameEngine the game engine containing player location
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        pGameEngine.getGui().println(pGameEngine.getPlayer().getCurrentRoom().getLongDescription());
        return false;
    } // execute()
}
