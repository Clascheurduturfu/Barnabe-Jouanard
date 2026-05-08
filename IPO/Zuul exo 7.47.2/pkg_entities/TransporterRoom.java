package pkg_entities;

import java.util.Random;
import java.util.ArrayList;

/**
 * A special type of {@link Room} that teleports the player to a random location
 *
 * @name Barnabe Jouanard 
 * @version 2026.05.01
 */


public class TransporterRoom extends Room
{
    private Room[] aTargetRooms;
    private Room aForcedRoom;

    public TransporterRoom(final String pDescription, final String pImage , final String pMapImage, final ArrayList<Room> pTargetRooms) 
    {
        super( pDescription, pImage , pMapImage );
        this.aTargetRooms = pTargetRooms.toArray(new Room[0]);
        this.aForcedRoom = null;
    }

    public Room getRandomRoom()
    {
        if (this.aForcedRoom != null) {
            return this.aForcedRoom;
        }
        Random vRandom = new Random();
        return aTargetRooms[vRandom.nextInt(this.aTargetRooms.length)];
    }

    public void setForcedRoom(final String pRoom){
        switch (pRoom){
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
    }

    public void resetForcedRoom(){
        this.aForcedRoom = null;
    }

}