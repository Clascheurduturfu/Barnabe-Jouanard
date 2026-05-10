package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.MovingTrainer;
import pkg_entities.Room;
import pkg_entities.TransporterRoom;

/**
 * Implements the {@code go} command for room navigation and transporter movement.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class GoCommand extends Command {
    /** Creates the command. */
    public GoCommand() {}

    /**
     * Moves the player through an exit named by the second command word.
     *
     * @param pGameEngine the game engine containing player and room state
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("Go where?");
            return false;
        }

        String vDirection = this.getSecondWord();
        Room vNextRoom = pGameEngine.getPlayer().getCurrentRoom().getExit(vDirection);

        if (vNextRoom == null && !pGameEngine.getPlayer().getCurrentRoom().isTeleporterRoom()) {
            pGameEngine.getGui().println("There is no door!");
        } else {
            if (pGameEngine.getPlayer().getCurrentRoom().isTeleporterRoom()) {
                TransporterRoom vTransporterRoom = (TransporterRoom) pGameEngine.getPlayer().getCurrentRoom();
                pGameEngine.getPlayer().moveTo(vTransporterRoom.getRandomRoom());
            } else {
                pGameEngine.getPlayer().moveTo(vNextRoom);
            }
            pGameEngine.getGui().println(pGameEngine.getPlayer().getCurrentRoom().getLongDescription());
            if (pGameEngine.getPlayer().getCurrentRoom().getImageName() != null) {
                pGameEngine.getGui().showImage(pGameEngine.getPlayer().getCurrentRoom().getImageName());
                if (pGameEngine.getPlayer().getItem("map") != null) {
                    pGameEngine.getGui().showMap(pGameEngine.getPlayer().getCurrentRoom().getMapImageName());
                }
            }

            if (pGameEngine.getPlayer().hasWon() && pGameEngine.getPlayer().getCurrentRoom().isWinningRoom()) {
                pGameEngine.getGui().println("\n" + "It's here that you've stopped Rayquaza from destroying the" + " world!");
            }

            for (MovingTrainer vMovingTrainer : pGameEngine.getMovingTrainers()) {
                vMovingTrainer.tryMove();
            }
        }
        return false;
    } // execute()
}
