package pkg_entities;
import java.util.HashMap;

/**
 * Décrivez votre classe Pokemon ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Pokemon
{
    private String aName;
    private int aHp;
    private HashMap<String, Integer> aMoves;

    /**
     * Constructeur d'objets de classe Pokemon
     */
    public Pokemon(String pName, final int pHp, final Attack[] pMoves) {
        this.aName = pName;
        this.aHp = pHp;
        this.aMoves = new HashMap<>();
        for (Attack move : pMoves) {
            this.aMoves.put(move.getName(), move.getDamage());
        }
    }

    public int getHp() {
        return aHp;
    }

    public String getName() {
        return aName;
    }

    public void reduceHp(int pDamage) {
        this.aHp -= pDamage;
    }

    public HashMap<String, Integer> getMoves() {
        return aMoves;
    }
}