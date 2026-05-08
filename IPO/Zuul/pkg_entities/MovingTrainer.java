package pkg_entities;
import java.util.Random;

/**
 * Décrivez votre classe MovingTrainer ici.
 *
 * @author 
 * @version 
 */
public class MovingTrainer extends Trainer
{
    /** The room where this trainer is currently located. */
    private Room aCurrentRoom;

    public MovingTrainer(final String pName, final String pDialog, final Room pStartRoom) {
        super(pName, pDialog);
        this.aCurrentRoom = pStartRoom;
    }

    public Room getCurrentRoom() {
        return this.aCurrentRoom;
    }

    public void setCurrentRoom(Room pRoom) {
        this.aCurrentRoom = pRoom;
    }

    public void tryMove() {
        Random vRandom = new Random();
        if (vRandom.nextInt(6) != 0) {
            return;
        }

        java.util.ArrayList<String> vSafeExits = new java.util.ArrayList<>();
        for (String vExitKey : this.aCurrentRoom.getAllExits().keySet()) {
            Room vRoom = this.aCurrentRoom.getAllExits().get(vExitKey);
            if (!vRoom.isTeleporterRoom()) {
                vSafeExits.add(vExitKey);
            }
        }

        if (vSafeExits.isEmpty()) {
            return;
        }

        String vRandomExit = vSafeExits.get(vRandom.nextInt(vSafeExits.size()));
        Room vNextRoom = this.aCurrentRoom.getAllExits().get(vRandomExit);

        this.aCurrentRoom.removeTrainer(this.getName());
        vNextRoom.addTrainer(this);
        this.aCurrentRoom = vNextRoom;
    }

}
