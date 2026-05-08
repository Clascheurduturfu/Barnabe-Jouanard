package pkg_commands;

import pkg_engine.GameEngine;
import pkg_entities.Item;

import java.util.HashMap;

/**
 * Implementation of the 'cashin' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class CashinCommand extends Command
{
    public CashinCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("You need something to cash in!");
            return false;
        }
        String vItemName = getSecondWord();
        HashMap<String, Integer> vCashableItem = new HashMap<String, Integer>();
        vCashableItem.put("grant", 75);
        vCashableItem.put("wallet", 25);

        if (vCashableItem.containsKey(vItemName)) {
            Item vItem = gameEngine.getPlayer().getItem(vItemName);
            if (vItem != null) {
                int vReward = vCashableItem.get(vItemName);
                gameEngine.getPlayer().setMoney(gameEngine.getPlayer().getMoney() + vReward);
                gameEngine.getPlayer().removeItem(vItemName);
                gameEngine.getGui().println("You have cashed in your " + vItemName + " and are now richer!");
                gameEngine.getGui().println("Your current money is: " + gameEngine.getPlayer().getMoney());
            } else {
                gameEngine.getGui().println("You don't have a " + vItemName + " to cash in.");
            }
        } else {
            gameEngine.getGui().println("You can't cash that in.");
        }
        return false;
    }
}
