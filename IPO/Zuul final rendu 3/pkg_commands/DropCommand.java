package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.Item;

/**
 * Implements the {@code drop} command, moving an item from the inventory to the current room.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class DropCommand extends Command {
    /** Creates the command. */
    public DropCommand() {}

    /**
     * Drops the named item and refunds its value to the player.
     *
     * @param pGameEngine the game engine containing the player inventory
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("Drop what?");
            return false;
        }
        final String vItemName = this.getSecondWord();
        final Item vItem = pGameEngine.getPlayer().getItem(vItemName);
        if (vItem == null) {
            pGameEngine.getGui().println("I can't find any " + vItemName + "!");
            return false;
        }
        pGameEngine.getPlayer().getCurrentRoom().addItem(vItemName, vItem);
        pGameEngine.getPlayer().removeItem(vItemName);
        pGameEngine.getGui().println("Dropped " + vItemName + "!");
        pGameEngine.getPlayer().setMoney(pGameEngine.getPlayer().getMoney() + vItem.getItemPrice());
        if (pGameEngine.getPlayer().getItem("map") == null) {
            pGameEngine.getGui().showMap("no map.jpeg");
        }
        return false;
    }
}
