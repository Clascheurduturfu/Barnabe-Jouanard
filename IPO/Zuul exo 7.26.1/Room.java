import java.util.HashMap;

/**
 * Models one location in the game world: narrative text, image, exits to other rooms,
 * and a keyed collection of {@link Item}s the player can examine.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Room
{
    /** Short phrase used in descriptions, e.g. "in the kitchen". */
    private String aDescription;
    /** Maps direction names to neighboring {@link Room} instances. */
    private HashMap<String, Room> aExits;
    /** Classpath resource path for the optional room illustration. */
    private String aImageName;
    /** Items present in this room, keyed by a short lookup name. */
    private HashMap<String, Item> aItems;

    /**
     * Creates a room with a description, image path, and empty exit/item maps.
     *
     * @param pDescription human-readable location phrase shown to the player
     * @param pImage       resource path for the picture, or {@code null} if none
     */
    public Room( final String pDescription, final String pImage )
    {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aImageName = pImage;
        this.aItems = new HashMap<String, Item>();
    } // Room()

    /**
     * Returns the short description supplied at construction time.
     *
     * @return the base location phrase
     */
    public String getDescription()
    {
        return this.aDescription;
    } // getDescription()

    /**
     * Looks up the neighboring room reachable via {@code pDirection}.
     *
     * @param pDirection exit key such as {@code "north"} or {@code "up"}
     * @return the linked {@link Room}, or {@code null} if no exit exists
     */
    public Room getExit( final String pDirection )
    {
        return this.aExits.get( pDirection );
    } // getExit()

    /**
     * Registers a one-way exit from this room to {@code pExit}.
     *
     * @param pDirection compass or vertical direction used by the parser
     * @param pExit      destination room; may be {@code null} to remove an exit
     */
    public void setExit( final String pDirection, final Room pExit )
    {
        this.aExits.put( pDirection, pExit );
    } // setExit()

    /**
     * Formats all exit directions as a single line.
     *
     * @return text like {@code Exits: north east}
     */
    public String getExitString()
    {
        StringBuilder vReturnString = new StringBuilder( "Exits:" );
        for ( String vExit : this.aExits.keySet() ) {
            vReturnString.append( " " ).append( vExit );
        }
        return vReturnString.toString();
    } // getExitString()

    /**
     * Composes the room description, exit list, and either item lines or a default notice.
     *
     * @return multi-line description suitable for printing or appending to the log
     */
    public String getLongDescription()
    {
        String vDescription = "You are " + this.getDescription() + ".\n" + this.getExitString();
        if ( ! this.aItems.isEmpty() ) {
            for ( String vName : this.aItems.keySet() ) {
                vDescription += "\n" + this.aItems.get( vName ).getItemDescription();
            }
        }
        else {
            vDescription += '\n' + "There are no items here!";
        }
        return vDescription;
    } // getLongDescription()

    /**
     * Returns the image resource path associated with this room.
     *
     * @return classpath-relative image filename, or {@code null}
     */
    public String getImageName()
    {
        return this.aImageName;
    } // getImageName()

    /**
     * Retrieves an item currently stored in the room.
     *
     * @param pName lookup key used when the item was {@link #addItem(String, Item) added}
     * @return the matching {@link Item}, or {@code null}
     */
    public Item getItem( final String pName )
    {
        return this.aItems.get( pName );
    }

    /**
     * Places (or replaces) an item in this room under the given key.
     *
     * @param pName short identifier used for later retrieval
     * @param pItem the {@link Item} instance to store
     */
    public void addItem( final String pName, final Item pItem )
    {
        this.aItems.put( pName, pItem );
    }
} // Room
