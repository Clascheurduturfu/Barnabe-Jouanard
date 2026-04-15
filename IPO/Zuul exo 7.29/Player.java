import java.util.Stack;

/**
 * Player - stores the state that belongs to the player.
 *
 * @author Barnabe Jouanard
 * @version 2026.04.13
 */
public class Player
{
    private String aName;
    private Room aCurrentRoom;
    private Stack<Room> aPreviousRooms;

    /**
     * Create a player with a name and a starting room.
     * @param pName the player name (can be empty or null)
     * @param pStartRoom the initial room
     */
    public Player(final String pName, final Room pStartRoom)
    {
        this.aName = pName;
        this.aCurrentRoom = pStartRoom;
        this.aPreviousRooms = new Stack<Room>();
    }

    /**
     * Return the player's current room.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Change the current room, saving the previous one.
     * This is called by the GameEngine after it validates the direction.
     * @param pNextRoom the room to move to
     */
    public void moveTo(final Room pNextRoom)
    {
        if (pNextRoom == null || pNextRoom == this.aCurrentRoom) {
            return;
        }
        if (this.aCurrentRoom != null) {
            this.aPreviousRooms.push(this.aCurrentRoom);
        }
        this.aCurrentRoom = pNextRoom;
    }

    /**
     * Return true if the player can go back to a previous room.
     */
    public boolean canGoBack()
    {
        return !this.aPreviousRooms.isEmpty();
    }

    /**
     * Move back to the previous room if possible.
     * @return the new current room (or current room if no previous room)
     */
    public Room goBack()
    {
        if (!this.canGoBack()) {
            return this.aCurrentRoom;
        }
        this.aCurrentRoom = this.aPreviousRooms.pop();
        return this.aCurrentRoom;
    }

    /**
     * Return the player name.
     */
    public String getName()
    {
        return this.aName;
    }
    
    /**
     * Set the player name.
     */
    public void setName(final String pName)
    {
        this.aName = pName;
    }
}
