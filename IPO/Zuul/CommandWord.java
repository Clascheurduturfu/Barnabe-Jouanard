/**
 * Enumerates the command words recognized by the game.
 * <p>
 * Each constant corresponds to a user command verb (for example {@code go} or
 * {@code help}). {@link #UNKNOWN} represents an unrecognized command word.
 * </p>
 *
 * @author Barnabe Jouanard
 * @version 2026.04.19
 */
public enum CommandWord {
    /** Moves the player to an adjacent room. */
    go,
    /** Prints help text and the list of available commands. */
    help,
    /** Quits the game. */
    quit,
    /** Reprints the current room description. */
    look,
    /** Exchanges certain items for money. */
    cashin,
    /** Returns to the previous room, when possible. */
    back,
    /** Runs commands from a packaged test file. */
    test,
    /** Changes the player's displayed name. */
    name,
    /** Takes an item from the current room. */
    take,
    /** Drops an item from the player's inventory into the room. */
    drop,
    /** Prints the player's inventory. */
    inventory,
    /** Fallback value used when the command word is not recognized. */
    UNKNOWN;
}