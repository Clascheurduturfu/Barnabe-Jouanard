/**
 * Describes a collectible or scenery object with a textual blurb and an
 * arbitrary weight
 * or price value surfaced to the player.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Item {
    /** Narrative name or sentence shown in room listings. */
    private String aDescription;
    /** Numeric attribute (treated as price in user-facing strings). */
    private int aPrice;

    /**
     * Stores the immutable description and numeric metadata for an item.
     *
     * @param pDescription text presented inside {@link #getItemDescription()}
     * @param pPrice       non-negative value interpreted as the item's price
     */
    public Item(final String pDescription, final int pPrice) {
        this.aDescription = pDescription;
        this.aPrice = pPrice;
    }

    /**
     * Returns the numeric price associated with this item.
     *
     * @return the configured price
     */
    public int getItemPrice() {
        return this.aPrice;
    } // getItemPrice()

    /**
     * Builds a short multi-line summary combining description and price.
     *
     * @return two-line English text suitable for room descriptions
     */
    public String getItemDescription() {
        return "There is " + this.aDescription + '\n' + "Item price: " + this.aPrice;
    }
} // Item
