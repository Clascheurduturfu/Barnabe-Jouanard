package pkg_entities;

import java.util.ArrayList;
import java.util.Random;

/**
 * Special room that teleports the player to one of several target rooms.
 *
 * <p>In test mode, the destination can be forced by room id so scripted test files can make the
 * teleporter deterministic.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class TransporterRoom extends Room {
    /** Rooms that can be selected as random teleport destinations. */
    private Room[] aTargetRooms;

    /** Optional fixed destination used by the {@code alea} test command. */
    private Room aForcedRoom;

    /**
     * Creates a transporter room with its display assets and possible targets.
     *
     * @param pDescription room description shown to the player
     * @param pImage room image filename
     * @param pMapImage map image filename
     * @param pTargetRooms destinations available to the teleporter
     */
    public TransporterRoom(final String pDescription,final String pImage,final String pMapImage,final ArrayList<Room> pTargetRooms) {
        super(pDescription, pImage, pMapImage);
        this.aTargetRooms = pTargetRooms.toArray(new Room[0]);
        this.aForcedRoom = null;
    }

    /**
     * Returns either the forced destination or a random destination.
     *
     * @return the selected destination room
     */
    public Room getRandomRoom() {
        if (this.aForcedRoom != null) {
            return this.aForcedRoom;
        }
        Random vRandom = new Random();
        return this.aTargetRooms[vRandom.nextInt(this.aTargetRooms.length)];
    } // getRandomRoom()

    /**
     * Forces the teleporter to send the player to a specific room id.
     *
     * @param pRoom the room id, or any unknown value to clear the forced room
     */
    public void setForcedRoom(final String pRoom) {
        switch (pRoom) {
            case "house":
                this.aForcedRoom = this.aTargetRooms[0];
                break;
            case "littleroot":
                this.aForcedRoom = this.aTargetRooms[1];
                break;
            case "route101":
                this.aForcedRoom = this.aTargetRooms[2];
                break;
            case "oldale":
                this.aForcedRoom = this.aTargetRooms[3];
                break;
            case "route102":
                this.aForcedRoom = this.aTargetRooms[4];
                break;
            case "petalburg":
                this.aForcedRoom = this.aTargetRooms[5];
                break;
            case "petalburgWoods":
                this.aForcedRoom = this.aTargetRooms[6];
                break;
            case "rustboro":
                this.aForcedRoom = this.aTargetRooms[7];
                break;
            default:
                this.aForcedRoom = null;
    }
    } // setForcedRoom()

    /** Clears the forced destination so the teleporter becomes random again. */
    public void resetForcedRoom() {
        this.aForcedRoom = null;
    } // resetForcedRoom()
}
