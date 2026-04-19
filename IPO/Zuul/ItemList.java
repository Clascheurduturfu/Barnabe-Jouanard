import java.util.HashMap;

/**
 * Stores a private collection of items keyed by name.
 *
 * The internal collection is fully encapsulated and cannot be manipulated
 * directly
 * from outside this class.
 *
 * @author Barnabe Jouanard
 * @version 2026.04.13
 */
public class ItemList {
    /** Internal storage keyed by item name. */
    private HashMap<String, Item> aItems;

    /**
     * Creates an empty item list.
     */
    public ItemList() {
        this.aItems = new HashMap<String, Item>();
    }

    /**
     * Returns the item associated with the given name, or {@code null}.
     *
     * @param pName the lookup key
     * @return the matching {@link Item}, or {@code null}
     */
    public Item getItem(final String pName) {
        return this.aItems.get(pName);
    }

    /**
     * Adds or replaces an item under the given key.
     *
     * @param pName short identifier used for later retrieval
     * @param pItem the {@link Item} to store
     */
    public void addItem(final String pName, final Item pItem) {
        this.aItems.put(pName, pItem);
    }

    /**
     * Removes the item associated with the given key.
     *
     * @param pName the lookup key
     */
    public void removeItem(final String pName) {
        this.aItems.remove(pName);
    }

    /**
     * Checks whether this item list contains no elements.
     *
     * @return {@code true} when no items are stored
     */
    public boolean isEmpty() {
        return this.aItems.isEmpty();
    }

    /**
     * Builds a multi-line string with each item's description,
     * or a default notice when the list is empty.
     *
     * @return formatted item descriptions
     */
    public String getItemsDescription() {
        if (this.aItems.isEmpty()) {
            return "There are no items here!";
        }
        String vResult = "";
        for (String vName : this.aItems.keySet()) {
            vResult += this.aItems.get(vName).getItemDescription() + "\n";
        }
        return vResult;
    }

    /**
     * Builds an inventory summary listing all item keys and the total value.
     *
     * @return formatted inventory text suitable for printing to the UI
     */
    public String getItemList() {
        if (this.aItems.isEmpty()) {
            return "You don't have any items!";
        }
        String vResult = "Your items: ";
        int vInventoryPrice = 0;
        for (String vItem : this.aItems.keySet()) {
            vResult += " " + vItem;
            vInventoryPrice += this.aItems.get(vItem).getItemPrice();
        }
        vResult += "\n\nTotal value: " + vInventoryPrice;
        return vResult;
    }
} // ItemList