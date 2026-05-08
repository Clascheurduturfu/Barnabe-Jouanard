package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implementation of the 'inventory' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class InventoryCommand extends Command
{
    public InventoryCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (hasSecondWord()) {
            gameEngine.getGui().println("Inventory what?");
            return false;
        }
        gameEngine.getGui().println(gameEngine.getPlayer().getItemInventory());
        return false;
    }
}
