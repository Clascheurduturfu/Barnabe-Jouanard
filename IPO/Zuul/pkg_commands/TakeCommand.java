package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.Item;

/**
 * Implements the {@code take} command, buying or collecting an item from the current room.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class TakeCommand extends Command {
    /** Creates the command. */
    public TakeCommand() {}

    /**
     * Moves the named item from the current room to the player's inventory when affordable.
     *
     * @param pGameEngine the game engine containing player and room state
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("Take what?");
            return false;
        }
        final String vItemName = this.getSecondWord();
        final Item vItem = pGameEngine.getPlayer().getCurrentRoom().getItem(vItemName);

        if (vItem == null) {
            pGameEngine.getGui().println("I can't find any " + vItemName + "!");
            return false;
        }
        if (pGameEngine.getPlayer().getMoney() >= vItem.getItemPrice()) {
            pGameEngine
                    .getPlayer()
                    .setMoney(pGameEngine.getPlayer().getMoney() - vItem.getItemPrice());
            pGameEngine.getPlayer().getCurrentRoom().removeItem(vItemName);
            pGameEngine.getPlayer().addItem(vItemName, vItem);
            pGameEngine.getGui().println("Took " + vItemName + "!");
            if (pGameEngine.getPlayer().getItem("map") != null) {
                pGameEngine
                        .getGui()
                        .showMap(pGameEngine.getPlayer().getCurrentRoom().getMapImageName());
            }
        } else {
            pGameEngine.getGui().println(vItemName + " is too expensive for you right now!");
        }
        return false;
    }
}
