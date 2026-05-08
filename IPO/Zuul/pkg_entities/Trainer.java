package pkg_entities;
import java.util.HashMap;

/**
 * Décrivez votre classe Trainer ici.
 *
 * @author 
 * @version 
 */
public class Trainer
{
    /** Trainer's name used as key in room trainer lists. */
    private String aName;
    /** Narrative name or sentence shown in room listings. */
    private String aDialog;
    /** Numeric attribute (treated as price in user-facing strings). */
    private HashMap<Integer, Pokemon> aTeam;
    /** Whether this trainer has been defeated by the player. */
    private boolean aIsDefeated;

    public Trainer(final String pName, final String pDialog) {
        this.aName = pName;
        this.aDialog = pDialog;
        this.aTeam = new HashMap<>();
        this.aIsDefeated = false;
    }

    public String getName() {
        return this.aName;
    }

    public boolean isDefeated() {
        return this.aIsDefeated;
    }

    public void setDefeated(boolean pDefeated) {
        this.aIsDefeated = pDefeated;
    }

    /**
     * Returns the numeric team size associated with this trainer.
     *
     * @return the configured team size
     */
    public int getTeamSize() {
        return this.aTeam.size();
    } // getTeamSize()

    /**
     * Adds or replaces a pokemon under the given key.
     *
     * @param pPokemon the pokemon name used for later retrieval
     * @param pPosition the numeric team size to store
     */
    public void setPokemon(final Integer pPosition, final Pokemon pPokemon) {
        this.aTeam.put(pPosition, pPokemon);
    } // setPokemon()

    public Pokemon getPokemon(final Integer pPosition) {
        return this.aTeam.get(pPosition);
    }

    /**
     * Builds a short multi-line summary combining description and team size.
     *
     * @return two-line English text suitable for room descriptions
     */
    public String getTrainerDescription() {
        return this.aDialog + '\n' + "Team size: " + this.aTeam.size();
    }
}