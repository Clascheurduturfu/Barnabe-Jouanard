package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implementation of the 'inventory' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class HelpCommand extends Command
{
    public HelpCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        gameEngine.getGui().println("Hello " + gameEngine.getPlayer().getName() + "!");
        gameEngine.getGui().println("You are in the wonderful world of Pokémon.");
        gameEngine.getGui().println("You are trying to stop Rayquaza from destroying Hoenn.");
        gameEngine.getGui().println("Your command words are:");
        Parser vParser = new Parser();
        gameEngine.getGui().println(vParser.getCommandString());
        return false;
    }
}
