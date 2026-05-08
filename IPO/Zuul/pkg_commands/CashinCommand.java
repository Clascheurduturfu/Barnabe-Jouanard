package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.Item;

import java.util.HashMap;

/**
 * Implements the {@code cashin} command, converting selected quest items into money.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class CashinCommand extends Command {
    /** Creates the command. */
    public CashinCommand() {}

    /**
     * Cashes in the named item when it is accepted by the game.
     *
     * @param pGameEngine the game engine containing player inventory and money
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("You need something to cash in!");
            return false;
        }
        final String vItemName = this.getSecondWord();
        final HashMap<String, Integer> vCashableItem = new HashMap<String, Integer>();
        vCashableItem.put("grant", 75);
        vCashableItem.put("wallet", 25);

        if (vCashableItem.containsKey(vItemName)) {
            final Item vItem = pGameEngine.getPlayer().getItem(vItemName);
            if (vItem != null) {
                final int vReward = vCashableItem.get(vItemName);
                pGameEngine.getPlayer().setMoney(pGameEngine.getPlayer().getMoney() + vReward);
                pGameEngine.getPlayer().removeItem(vItemName);
                pGameEngine
                        .getGui()
                        .println("You have cashed in your " + vItemName + " and are now richer!");
                pGameEngine
                        .getGui()
                        .println("Your current money is: " + pGameEngine.getPlayer().getMoney());
            } else {
                pGameEngine.getGui().println("You don't have a " + vItemName + " to cash in.");
            }
        } else {
            pGameEngine.getGui().println("You can't cash that in.");
        }
        return false;
    }
}
