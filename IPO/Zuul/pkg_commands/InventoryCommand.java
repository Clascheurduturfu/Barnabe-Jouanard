package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Implements the {@code inventory} command, printing the player's current items.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class InventoryCommand extends Command {
    /** Creates the command. */
    public InventoryCommand() {}

    /**
     * Prints the inventory summary.
     *
     * @param pGameEngine the game engine containing the player inventory
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (this.hasSecondWord()) {
            pGameEngine.getGui().println("Inventory what?");
            return false;
        }
        pGameEngine.getGui().println(pGameEngine.getPlayer().getItemInventory());
        return false;
    }
}
