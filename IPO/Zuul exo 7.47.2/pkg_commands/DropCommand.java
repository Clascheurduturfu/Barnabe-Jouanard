package pkg_commands;

import pkg_engine.GameEngine;
import pkg_entities.Item;

/**
 * Implementation of the 'drop' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class DropCommand extends Command
{
    public DropCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("Drop what?");
            return false;
        }
        String vItemName = getSecondWord();
        Item vItem = gameEngine.getPlayer().getItem(vItemName);
        if (vItem == null) {
            gameEngine.getGui().println("I can't find any " + vItemName + "!");
            return false;
        }
        gameEngine.getPlayer().getCurrentRoom().addItem(vItemName, vItem);
        gameEngine.getPlayer().removeItem(vItemName);
        gameEngine.getGui().println("Dropped " + vItemName + "!");
        gameEngine.getPlayer().setMoney(gameEngine.getPlayer().getMoney() + vItem.getItemPrice());
        if (gameEngine.getPlayer().getItem("map") == null) {
            gameEngine.getGui().showMap("no map.jpeg");
        }
        return false;
    }
}
