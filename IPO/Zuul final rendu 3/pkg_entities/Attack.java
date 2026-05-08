package pkg_entities;

import java.util.Random;

/**
 * Represents a Pokemon attack with a name, base damage, and hit precision.
 *
 * <p>The damage returned by this class already includes the accuracy check: a missed attack returns
 * zero damage.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class Attack {
    /** Display name of the attack. */
    private String aName;

    /** Base damage dealt when the attack hits. */
    private int aDamage;

    /** Hit precision as a percentage from 0 to 100. */
    private int aPrecision;

    /**
     * Creates an attack with its display name, damage, and precision.
     *
     * @param pName the attack name
     * @param pDamage the damage dealt on a successful hit
     * @param pPrecision the chance to hit, expressed as a percentage
     */
    public Attack(final String pName, final int pDamage, final int pPrecision) {
        this.aName = pName;
        this.aDamage = pDamage;
        this.aPrecision = pPrecision;
    }

    /**
     * Calculates the damage for one use of the attack.
     *
     * @return the base damage when the attack hits, or {@code 0} when it misses
     */
    public int getDamage() {
        final Random vRandom = new Random();
        final int vRoll = vRandom.nextInt(101);
        final int vFactor;

        if (vRoll > this.aPrecision) {
            vFactor = 0;
        } else {
            vFactor = 1;
        }
        return this.aDamage * vFactor;
    }

    /**
     * Returns the attack display name.
     *
     * @return the attack name
     */
    public String getName() {
        return this.aName;
    }
}
