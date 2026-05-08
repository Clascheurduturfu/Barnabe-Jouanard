package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implementation of the 'look' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class LookCommand extends Command
{
    public LookCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        gameEngine.getGui().println(gameEngine.getPlayer().getCurrentRoom().getLongDescription());
        return false;
    }
}
