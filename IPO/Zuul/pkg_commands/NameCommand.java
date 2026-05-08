package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implementation of the 'name' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class NameCommand extends Command
{
    public NameCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("You need a name!");
            return false;
        }
        String vName = getSecondWord();
        gameEngine.getPlayer().setName(vName);
        gameEngine.getGui().println("Your new name is " + gameEngine.getPlayer().getName() + '!');
        gameEngine.printWelcome();
        return false;
    }
}
