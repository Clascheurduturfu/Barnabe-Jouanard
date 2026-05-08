package pkg_commands;

import pkg_engine.GameEngine;
import pkg_entities.MovingTrainer;

/**
 * Implementation of the 'back' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class BackCommand extends Command
{
    public BackCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (hasSecondWord()) {
            // If there is a second word, we don't know where to go.
            gameEngine.getGui().println("Back what?");
            return false;
        }

        if (!gameEngine.getPlayer().canGoBack()) {
            gameEngine.getGui().println("Can't go back, you just started!");
        } else if (gameEngine.getPlayer().getCurrentRoom().isExit(gameEngine.getPlayer().getPreviousRoom()) == false) {
            gameEngine.getGui().println("There is no door!");
            return false;
        } else {
            gameEngine.getPlayer().goBack();
            gameEngine.getGui().println(gameEngine.getPlayer().getCurrentRoom().getLongDescription());
            if (gameEngine.getPlayer().getCurrentRoom().getImageName() != null) {
                gameEngine.getGui().showImage(gameEngine.getPlayer().getCurrentRoom().getImageName());
                if (gameEngine.getPlayer().getItem("map") != null) {
                    gameEngine.getGui().showMap(gameEngine.getPlayer().getCurrentRoom().getMapImageName());
                }
            }
            for (MovingTrainer vMovingTrainer : gameEngine.getMovingTrainers()) {
                vMovingTrainer.tryMove();
            }
            if (gameEngine.getPlayer().getItem("delta-orb") != null && gameEngine.getPlayer().getCurrentRoom().isWinningRoom()) {
                gameEngine.getGui().println("\n" + "You want to keep winning, don't you?");
            }
        }
        return false;
    }
}
