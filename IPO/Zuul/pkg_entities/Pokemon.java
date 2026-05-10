package pkg_entities;

import java.util.HashMap;

/**
 * Stores a Pokemon's combat name, hit points, and available moves.
 *
 * <p>Moves are kept as a map from move name to damage so battle code can label buttons and resolve
 * attacks quickly.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class Pokemon {
    /** Pokemon display name. */
    private String aName;

    /** Maximum hit points for this Pokemon. */
    private int aHp;

    /** Move names mapped to their Attack objects. */
    private HashMap<String, Attack> aMoves;

    /**
     * Creates a Pokemon with its name, hit points, and move list.
     *
     * @param pName the Pokemon name
     * @param pHp the maximum hit points
     * @param pMoves attacks available to this Pokemon
     */
    public Pokemon(final String pName, final int pHp, final Attack[] pMoves) {
        this.aName = pName;
        this.aHp = pHp;
        this.aMoves = new HashMap<>();
        for (Attack vMove : pMoves) {
            this.aMoves.put(vMove.getName(), vMove);
        }
    } // Pokemon()

    /**
     * Returns this Pokemon's maximum hit points.
     *
     * @return the maximum hit points
     */
    public int getHp() {
        return this.aHp;
    } // getHp()

    /**
     * Returns this Pokemon's name.
     *
     * @return the Pokemon name
     */
    public String getName() {
        return this.aName;
    } // getName()

    /**
     * Reduces this Pokemon's stored hit points.
     *
     * @param pDamage the damage to subtract
     */
    public void reduceHp(final int pDamage) {
        this.aHp -= pDamage;
    } // reduceHp()

    /**
     * Returns the move table used by the battle UI.
     *
     * @return move names mapped to their Attack objects
     */
    public HashMap<String, Attack> getMoves() {
        return this.aMoves;
    } // getMoves()
}
