package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.MovingTrainer;

/**
 * Implements the {@code back} command, moving the player to the previous room when possible.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class BackCommand extends Command {
    /** Creates the command. */
    public BackCommand() {}

    /**
     * Moves the player to the previous room and refreshes room output.
     *
     * @param pGameEngine the game engine containing player history
     * @return always {@code false}; this command does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (this.hasSecondWord()) {
            pGameEngine.getGui().println("Back what?");
            return false;
    }

        if (!pGameEngine.getPlayer().canGoBack()) {
            pGameEngine.getGui().println("Can't go back, you just started!");
        } else if (pGameEngine.getPlayer().getCurrentRoom().isExit(pGameEngine.getPlayer().getPreviousRoom()) == false) {
            pGameEngine.getGui().println("There is no door!");
            return false;
        } else {
            pGameEngine.getPlayer().goBack();
            pGameEngine.getGui().println(pGameEngine.getPlayer().getCurrentRoom().getLongDescription());
            if (pGameEngine.getPlayer().getCurrentRoom().getImageName() != null) {
                pGameEngine.getGui().showImage(pGameEngine.getPlayer().getCurrentRoom().getImageName());
                if (pGameEngine.getPlayer().getItem("map") != null) {
                    pGameEngine.getGui().showMap(pGameEngine.getPlayer().getCurrentRoom().getMapImageName());
                }
            }
            for (MovingTrainer vMovingTrainer : pGameEngine.getMovingTrainers()) {
                vMovingTrainer.tryMove();
            }
            if (pGameEngine.getPlayer().getItem("delta-orb") != null && pGameEngine.getPlayer().getCurrentRoom().isWinningRoom()) {
                pGameEngine.getGui().println("\n" + "You want to keep winning, don't you?");
            }
        }
        return false;
    } // execute()
}
