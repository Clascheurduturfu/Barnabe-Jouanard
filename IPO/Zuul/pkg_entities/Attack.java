package pkg_entities;

import java.util.Random;

/**
 * Décrivez votre classe Attack ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Attack
{
    private String aName;
    private int aDamage;
    private int aPrecision;
    /**
     * Constructeur d'objets de classe Attack
     */
    public Attack(final String pName, final int pDamage, final int pPrecision)
    {
        this.aName = pName;
        this.aDamage = pDamage;
        this.aPrecision = pPrecision;
    }

    public int getDamage() {
        Random random = new Random();
        int roll = random.nextInt(101);

        int vFactor;

        if (roll > aPrecision) {
            vFactor = 0;
        } else {
            vFactor = 1;
        }
        return aDamage * vFactor;
    }

    public String getName() {
        return aName;
    }
}