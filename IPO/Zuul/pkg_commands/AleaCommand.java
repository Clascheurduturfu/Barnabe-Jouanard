package pkg_commands;
import pkg_engine.GameEngine;
import pkg_entities.Room;
import pkg_entities.TransporterRoom;

/**
 * Implementation of the 'alea' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class AleaCommand extends Command
{
    public AleaCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (gameEngine.getPlayer().getCurrentRoom().isTeleporterRoom() && gameEngine.isTest()) {
            TransporterRoom vTransporterRoom = (TransporterRoom) gameEngine.getPlayer().getCurrentRoom();
            if(getSecondWord() == null){
                vTransporterRoom.resetForcedRoom();
            } else{
                String vRoom = getSecondWord();
                vTransporterRoom.setForcedRoom(vRoom);
            }
        } else {
            gameEngine.getGui().println("you are not in the Transporter Room or in test mode");
        }
        return false;
    }
}
