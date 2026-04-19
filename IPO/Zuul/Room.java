import java.util.HashMap;
import java.util.Set;

/**
 * Models one location in the game world: narrative text, image, exits to other
 * rooms,
 * and a keyed collection of {@link Item}s the player can examine.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Room {
    /** Short phrase used in descriptions, e.g. "in the kitchen". */
    private String aDescription;
    /** Maps direction names to neighboring {@link Room} instances. */
    private HashMap<String, Room> aExits;
    /** Classpath resource path for the room illustration. */
    private String aImageName;
    /** Classpath resource path for the room's map illustration. */
    private String aMapImageName;
    /** Items present in this room, keyed by a short lookup name. */
    private ItemList aItems;
    /**
     * Marks this room as a win condition when the player holds the required item.
     */
    private boolean isWinningRoom;

    /**
     * Creates a room with a description, image path, and empty exit/item maps.
     *
     * @param pDescription human-readable location phrase shown to the player
     * @param pImage       resource path for the picture, or {@code null} if none
     * @param pMapImage    resource path for the map image, or {@code null} if none
     */
    public Room(final String pDescription, final String pImage, final String pMapImage) {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aImageName = pImage;
        this.aMapImageName = pMapImage;
        this.aItems = new ItemList();
    } // Room()

    /**
     * Composes the room description, exit list, and either item lines or a default
     * notice.
     *
     * @return multi-line description suitable for printing or appending to the log
     */
    public String getLongDescription() {
        return "You are " + this.getDescription() + ".\n" + this.getExitString() + "\n\n"
                + this.aItems.getItemsDescription();
    } // getLongDescription()

    /**
     * Returns the short description supplied at construction time.
     *
     * @return the base location phrase
     */
    public String getDescription() {
        return this.aDescription;
    } // getDescription()

    /**
     * Looks up the neighboring room reachable via {@code pDirection}.
     *
     * @param pDirection exit key such as {@code "north"} or {@code "up"}
     * @return the linked {@link Room}, or {@code null} if no exit exists
     */
    public Room getExit(final String pDirection) {
        return this.aExits.get(pDirection);
    } // getExit()

    /**
     * Checks if a room is an exit from this room.
     *
     * @param pRoom the room to check
     * @return {@code true} if the room is an exit; {@code false} otherwise
     */
    public boolean isExit(final Room pRoom) {
        return this.aExits.containsValue(pRoom);
    }

    /**
     * Registers a one-way exit from this room to {@code pExit}.
     *
     * @param pDirection compass or vertical direction used by the parser
     * @param pExit      destination room; may be {@code null} to remove an exit
     */
    public void setExit(final String pDirection, final Room pExit) {
        this.aExits.put(pDirection, pExit);
    } // setExit()

    /**
     * Formats all exit directions as a single line.
     *
     * @return text like {@code Exits: north east}
     */
    public String getExitString() {
        StringBuilder vReturnString = new StringBuilder("Exits:");
        for (String vExit : this.aExits.keySet()) {
            vReturnString.append(" ").append(vExit);
        }
        return vReturnString.toString();
    } // getExitString()

    /**
     * Returns the image resource path associated with this room.
     *
     * @return classpath-relative image filename, or {@code null}
     */
    public String getImageName() {
        return this.aImageName;
    } // getImageName()

    /**
     * Returns the map image resource path associated with this room.
     *
     * @return classpath-relative image filename, or {@code null}
     */
    public String getMapImageName() {
        return this.aMapImageName;
    } // getMapImageName()

    /**
     * Retrieves an item currently stored in the room.
     *
     * @param pName lookup key used when the item was {@link #addItem(String, Item)
     *              added}
     * @return the matching {@link Item}, or {@code null}
     */
    public Item getItem(final String pName) {
        return this.aItems.getItem(pName);
    }

    /**
     * Places (or replaces) an item in this room under the given key.
     *
     * @param pName short identifier used for later retrieval
     * @param pItem the {@link Item} instance to store
     */
    public void addItem(final String pName, final Item pItem) {
        this.aItems.addItem(pName, pItem);
    }

    /**
     * Removes an item from this room's item list.
     *
     * @param pName lookup key of the item to remove
     */
    public void removeItem(final String pName) {
        this.aItems.removeItem(pName);
    }

    /**
     * Marks this room as a winning room.
     * <p>
     * The win condition is evaluated by the engine (for example: being in a winning
     * room while holding a specific item).
     * </p>
     */
    public void setAsWinningRoom() {
        this.isWinningRoom = true;
    }

    /**
     * Indicates whether this room is flagged as a winning room.
     *
     * @return {@code true} if the room is configured as a winning room
     */
    public boolean isWinningRoom() {
        return this.isWinningRoom;
    }
} // Room
