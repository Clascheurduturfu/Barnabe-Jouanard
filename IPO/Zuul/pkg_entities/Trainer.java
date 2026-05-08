package pkg_entities;

import java.util.HashMap;

/**
 * Represents a trainer who can appear in a room and battle the player.
 *
 * <p>Each trainer owns a numbered Pokemon team and keeps track of whether the player has already
 * defeated them.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class Trainer {
    /** Trainer name used as a key in room trainer lists. */
    private String aName;

    /** Narrative description shown in room listings. */
    private String aDialog;

    /** Pokemon team indexed from one for battle order. */
    private HashMap<Integer, Pokemon> aTeam;

    /** Whether this trainer has been defeated by the player. */
    private boolean aIsDefeated;

    /**
     * Creates a trainer with a display name and room description.
     *
     * @param pName the trainer name
     * @param pDialog the description shown when the trainer is in a room
     */
    public Trainer(final String pName, final String pDialog) {
        this.aName = pName;
        this.aDialog = pDialog;
        this.aTeam = new HashMap<>();
        this.aIsDefeated = false;
    }

    /**
     * Returns the trainer name.
     *
     * @return the trainer name
     */
    public String getName() {
        return this.aName;
    }

    /**
     * Indicates whether this trainer has already been defeated.
     *
     * @return {@code true} when the player has defeated this trainer
     */
    public boolean isDefeated() {
        return this.aIsDefeated;
    }

    /**
     * Sets this trainer's defeated state.
     *
     * @param pDefeated {@code true} after the trainer loses a battle
     */
    public void setDefeated(final boolean pDefeated) {
        this.aIsDefeated = pDefeated;
    }

    /**
     * Returns the number of Pokemon in this trainer's team.
     *
     * @return the configured team size
     */
    public int getTeamSize() {
        return this.aTeam.size();
    } // getTeamSize()

    /**
     * Adds or replaces a Pokemon at the given team position.
     *
     * @param pPosition the team slot
     * @param pPokemon the Pokemon to store
     */
    public void setPokemon(final Integer pPosition, final Pokemon pPokemon) {
        this.aTeam.put(pPosition, pPokemon);
    } // setPokemon()

    /**
     * Returns the Pokemon in the given team slot.
     *
     * @param pPosition the team slot
     * @return the matching Pokemon, or {@code null}
     */
    public Pokemon getPokemon(final Integer pPosition) {
        return this.aTeam.get(pPosition);
    }

    /**
     * Builds a short multi-line summary combining description and team size.
     *
     * @return two-line English text suitable for room descriptions
     */
    public String getTrainerDescription() {
        final String vDefeatedText;
        if (this.isDefeated()) {
            vDefeatedText = " (defeated)";
        } else {
            vDefeatedText = "";
        }
        return this.aDialog + vDefeatedText + '\n' + "Team size: " + this.aTeam.size();
    }
}
