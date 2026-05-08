package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.TransporterRoom;

/**
 * Implements the {@code alea} test command for forcing a transporter room destination.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class AleaCommand extends Command {
    /** Creates the command. */
    public AleaCommand() {}

    /**
     * Forces or resets the current transporter room destination while in test mode.
     *
     * @param pGameEngine the game engine containing the player and room state
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (pGameEngine.getPlayer().getCurrentRoom().isTeleporterRoom() && pGameEngine.isTest()) {
            final TransporterRoom vTransporterRoom =
                    (TransporterRoom) pGameEngine.getPlayer().getCurrentRoom();
            if (this.getSecondWord() == null) {
                vTransporterRoom.resetForcedRoom();
            } else {
                final String vRoom = this.getSecondWord();
                vTransporterRoom.setForcedRoom(vRoom);
            }
        } else {
            pGameEngine.getGui().println("You are not in the Transporter Room or in test mode.");
        }
        return false;
    }
}
