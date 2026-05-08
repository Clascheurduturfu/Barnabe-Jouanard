package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implementation of the 'alea' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class QuitCommand extends Command
{
    public QuitCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (hasSecondWord()) {
            gameEngine.getGui().println("Quit what?");
            return false;
        }
        gameEngine.getGui().println("Thank you for playing! Goodbye.");
        gameEngine.endGame();
        return true;
    }
}
