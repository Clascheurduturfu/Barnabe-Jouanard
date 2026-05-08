package pkg_commands;

import pkg_engine.GameEngine;
import pkg_entities.Item;

/**
 * Implementation of the 'take' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class TakeCommand extends Command
{
    public TakeCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("Take what?");
            return false;
        }
        String vItemName = getSecondWord();
        Item vItem = gameEngine.getPlayer().getCurrentRoom().getItem(vItemName);

        if (vItem == null) {
            gameEngine.getGui().println("I can't find any " + vItemName + "!");
            return false;
        }
        if (gameEngine.getPlayer().getMoney() >= vItem.getItemPrice()) {
            gameEngine.getPlayer().setMoney(gameEngine.getPlayer().getMoney() - vItem.getItemPrice());
            gameEngine.getPlayer().getCurrentRoom().removeItem(vItemName);
            gameEngine.getPlayer().addItem(vItemName, vItem);
            gameEngine.getGui().println("Took " + vItemName + "!");
            if (gameEngine.getPlayer().getItem("map") != null) {
                gameEngine.getGui().showMap(gameEngine.getPlayer().getCurrentRoom().getMapImageName());
            }
        } else {
            gameEngine.getGui().println(vItemName + " is too expensive for you right now!");
        }
        return false;
    }
}
