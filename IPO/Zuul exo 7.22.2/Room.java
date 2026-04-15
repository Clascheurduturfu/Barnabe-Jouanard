import java.util.HashMap;

/**
 * Classe Room - a place of the game Zuul.
 *
 * @author Barnabe Jouanarrd
 * @version 2026.02.17
 */
public class Room
{
    private String aDescription;
    private HashMap<String, Room> aExits;
    private String aImageName;
    private HashMap<String, Item> aItems;
    
    /**
     * Create a room described by "pDescription". Initially, it has no exits. 
     * "pDescription" is something like "a kitchen" or "an open courtyard".
     * @param pDescription The room's description.
     */
    public Room (final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aImageName = pImage;
        this.aItems = new HashMap<String, Item>();
    } // Room()
    
    /**
     * Return the short description of the room (the one that was 
     * defined in the constructor).
     * @return The description of the room.
     */
    public String getDescription()
    {
        return this.aDescription;
    } // getDescription()
    
    /**
     * Return the room that is reached if we go from this room in the direction 
     * "pDirection". If there is no room in that direction, return null.
     * @param pDirection The exit's direction.
     * @return The room in the given direction or null.
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
    } // getExit()
    
    /**
     * Define an exit from this room. Every direction either leads 
     * to another room or is null (no exit there).
     * @param pDirection The direction of the exit.
     * @param pExit The room in the given direction.
     */
    public void setExit (final String pDirection,final Room pExit)
    {
        this.aExits.put(pDirection, pExit);
    } // setExit()
     
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString()
    {
        StringBuilder vReturnString = new StringBuilder("Exits:");
        for (String vExit : this.aExits.keySet()) {
            vReturnString.append(" ").append(vExit);
        }
        return vReturnString.toString();
    } // getExitString()
    
    /**
     * Return a long description of this room, including the room's 
     * description and its exits.
     * @return A full description of the room.
     */
    public String getLongDescription()
    {
        String vDescription = "You are " + this.getDescription() + ".\n" + this.getExitString();
        if(!this.aItems.isEmpty()){
            for (String vName : this.aItems.keySet()) {
                vDescription += "\n" + this.aItems.get(vName).getItemDescription();
            }
        }
        else {
            vDescription += '\n' + "There are no Items here!";
        }
        return vDescription;
    } // getLongDescription()
    
    public String getImageName()
    {
        return this.aImageName;
    } // getImageName()
    
    public Item getItem(final String pName){
        return this.aItems.get(pName);
    }
    
    public void addItem(final String pName, final Item pItem) {
        this.aItems.put(pName, pItem);
    }
} // Room