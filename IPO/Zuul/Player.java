import java.util.Stack;

/**
 * Stores the state that belongs to the player: identity, current position,
 * history,
 * and a personal item list.
 *
 * @author Barnabe Jouanard
 * @version 2026.04.13
 */
public class Player {
    /** Player display name (may be {@code null}). */
    private String aName;
    /** Current room occupied by the player. */
    private Room aCurrentRoom;
    /** Stack of previously visited rooms, used to support {@code back}. */
    private Stack<Room> aPreviousRooms;
    /** Items currently held by the player, keyed by a short lookup name. */
    private ItemList aItems;
    /** Current money balance used when buying items. */
    private int aMoney;

    /**
     * Creates a player with a name and a starting room.
     *
     * @param pName      the player name (can be empty or {@code null})
     * @param pStartRoom the initial room
     */
    public Player(final String pName, final Room pStartRoom) {
        this.aName = pName;
        this.aCurrentRoom = pStartRoom;
        this.aPreviousRooms = new Stack<Room>();
        this.aItems = new ItemList();
        this.aMoney = 150;
    }

    /**
     * Returns the player's current room.
     *
     * @return the current room (may be {@code null} if not initialized)
     */
    public Room getCurrentRoom() {
        return this.aCurrentRoom;
    }

    /**
     * Changes the current room, saving the previous one to the history stack.
     * <p>
     * Intended to be called by the engine after validating the intended direction.
     * </p>
     *
     * @param pNextRoom the room to move to (may be {@code null})
     */
    public void moveTo(final Room pNextRoom) {
        if (this.aCurrentRoom != null) {
            this.aPreviousRooms.push(this.aCurrentRoom);
        }
        this.aCurrentRoom = pNextRoom;
    }

    /**
     * Indicates whether the player can go back to a previous room.
     *
     * @return {@code true} if a previous room exists in the history stack
     */
    public boolean canGoBack() {
        return !this.aPreviousRooms.isEmpty();
    }

    /**
     * Returns the previous room.
     *
     * @return the previous room
     */
    public Room getPreviousRoom() {
        return this.aPreviousRooms.peek();
    }

    /**
     * Moves back to the previous room.
     * <p>
     * This method assumes {@link #canGoBack()} is {@code true}. If the stack is
     * empty, {@link Stack#pop()} will throw an exception.
     * </p>
     */
    public void goBack() {
        this.aCurrentRoom = this.aPreviousRooms.pop();
    }

    /**
     * Returns the player name.
     *
     * @return the current name (may be {@code null})
     */
    public String getName() {
        return this.aName;
    }

    /**
     * Sets the player name.
     *
     * @param pName the new name (may be {@code null})
     */
    public void setName(final String pName) {
        this.aName = pName;
    }

    /**
     * Retrieves an item from the player's inventory.
     *
     * @param pName lookup key used when the item was added
     * @return the matching {@link Item}, or {@code null}
     */
    public Item getItem(final String pName) {
        return this.aItems.getItem(pName);
    }

    /**
     * Adds (or replaces) an item in the player's inventory.
     *
     * @param pName lookup key used for later retrieval
     * @param pItem the item to store
     */
    public void addItem(final String pName, final Item pItem) {
        this.aItems.addItem(pName, pItem);
    }

    /**
     * Removes an item from the player's inventory.
     *
     * @param pName lookup key of the item to remove
     */
    public void removeItem(final String pName) {
        this.aItems.removeItem(pName);
    }

    /**
     * Returns the player's current money balance.
     *
     * @return the amount of money the player has
     */
    public int getMoney() {
        return this.aMoney;
    }

    /**
     * Sets the player's money balance.
     *
     * @param pNewMoney the new amount of money
     */
    public void setMoney(final int pNewMoney) {
        this.aMoney = pNewMoney;
    }

    /**
     * Returns a human-readable inventory summary.
     *
     * @return formatted item list including total value
     */
    public String getItemInventory() {
        return this.aItems.getItemList();
    }
} // Player