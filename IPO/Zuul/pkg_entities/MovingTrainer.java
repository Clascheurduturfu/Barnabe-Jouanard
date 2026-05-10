package pkg_entities;

import java.util.Random;

/**
 * A trainer that can randomly move between connected rooms while the player explores.
 *
 * <p>Movement avoids teleporter rooms so wandering trainers stay reachable in normal map locations.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class MovingTrainer extends Trainer {
    /** The room where this trainer is currently located. */
    private Room aCurrentRoom;

    /**
     * Creates a moving trainer with a starting room.
     *
     * @param pName the trainer name
     * @param pDialog the room description for the trainer
     * @param pStartRoom the first room occupied by the trainer
     */
    public MovingTrainer(final String pName, final String pDialog, final Room pStartRoom) {
        super(pName, pDialog);
        this.aCurrentRoom = pStartRoom;
    } // MovingTrainer()

    /**
     * Returns the trainer's current room.
     *
     * @return the current room
     */
    public Room getCurrentRoom() {
        return this.aCurrentRoom;
    } // getCurrentRoom()

    /**
     * Updates the trainer's current room.
     *
     * @param pRoom the new current room
     */
    public void setCurrentRoom(final Room pRoom) {
        this.aCurrentRoom = pRoom;
    } // setCurrentRoom()

    /** Gives the trainer a small chance to move through a non-teleporter exit. */
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
    } // tryMove()
}
