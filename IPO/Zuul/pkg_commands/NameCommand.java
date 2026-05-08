package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implements the {@code name} command, changing the player's display name.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class NameCommand extends Command {
    /** Creates the command. */
    public NameCommand() {}

    /**
     * Sets the player's name to the second command word.
     *
     * @param pGameEngine the game engine containing player state
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("You need a name!");
            return false;
        }
        final String vName = this.getSecondWord();
        pGameEngine.getPlayer().setName(vName);
        pGameEngine.getGui().println("Your new name is " + pGameEngine.getPlayer().getName() + '!');
        pGameEngine.printWelcome();
        return false;
    }
}
