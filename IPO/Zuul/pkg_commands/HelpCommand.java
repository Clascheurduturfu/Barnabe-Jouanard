package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implements the {@code help} command, printing the game goal and command list.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class HelpCommand extends Command {
    /** Creates the command. */
    public HelpCommand() {}

    /**
     * Prints help text and all available commands.
     *
     * @param pGameEngine the game engine used for output and player state
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        pGameEngine.getGui().println("Hello " + pGameEngine.getPlayer().getName() + "!");
        pGameEngine.getGui().println("You are in the wonderful world of Pokémon.");
        pGameEngine.getGui().println("You are trying to stop Rayquaza from destroying Hoenn.");
        pGameEngine.getGui().println("Your command words are:");
        final Parser vParser = new Parser();
        pGameEngine.getGui().println(vParser.getCommandString());
        return false;
    }
}
