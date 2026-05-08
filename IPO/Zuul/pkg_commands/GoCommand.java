package pkg_commands;
import pkg_engine.GameEngine;
import pkg_entities.Room;
import pkg_entities.TransporterRoom;
import pkg_entities.MovingTrainer;

/**
 * Implementation of the 'look' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class GoCommand extends Command
{
    public GoCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            // If there is no second word, we don't know where to go.
            gameEngine.getGui().println("Go where?");
            return false;
        }

        String vDirection = getSecondWord();

        Room vNextRoom = gameEngine.getPlayer().getCurrentRoom().getExit(vDirection);

        if (vNextRoom == null && !gameEngine.getPlayer().getCurrentRoom().isTeleporterRoom()) {
            gameEngine.getGui().println("There is no door!");
        } else {
            if (gameEngine.getPlayer().getCurrentRoom().isTeleporterRoom()) {
                TransporterRoom vTransporterRoom = (TransporterRoom)gameEngine.getPlayer().getCurrentRoom();
                gameEngine.getPlayer().moveTo(vTransporterRoom.getRandomRoom());
            } else {
                gameEngine.getPlayer().moveTo(vNextRoom);
            }
            gameEngine.getGui().println(gameEngine.getPlayer().getCurrentRoom().getLongDescription());
            if (gameEngine.getPlayer().getCurrentRoom().getImageName() != null) {
                gameEngine.getGui().showImage(gameEngine.getPlayer().getCurrentRoom().getImageName());
                if (gameEngine.getPlayer().getItem("map") != null) {
                    gameEngine.getGui().showMap(gameEngine.getPlayer().getCurrentRoom().getMapImageName());
                }
            }

            if (gameEngine.getPlayer().getItem("delta-orb") != null && gameEngine.getPlayer().getCurrentRoom().isWinningRoom()) {
                gameEngine.getGui().println("\n" + "Congratulations! You just won! Youve stopped rayquazza from destroying the world!" + "\n" + "Thank you for playing the whole game!");
                gameEngine.getPlayer().setHasWon(true);
            }
            
            for (MovingTrainer vMovingTrainer : gameEngine.getMovingTrainers()) {
                vMovingTrainer.tryMove();
            }
        }
        return false;
    }
}
