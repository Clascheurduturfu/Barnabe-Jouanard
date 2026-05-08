package pkg_commands;

import pkg_engine.GameEngine;

/**
 * Base type for all executable player commands.
 *
 * <p>The parser creates a concrete command object and stores the optional second token here before
 * the command is executed.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public abstract class Command {
    /** Optional second token; {@code null} if none was supplied. */
    private String aSecondWord;

    /** Creates a command with no second word. */
    public Command() {
        this.aSecondWord = null;
    } // Command()

    /**
     * Returns the second word of this command, if any.
     *
     * @return the second token, or {@code null}
     */
    public String getSecondWord() {
        return this.aSecondWord;
    } // getSecondWord()

    /**
     * Replaces the optional second word before execution.
     *
     * @param pSecondWord the new second word, or {@code null}
     */
    public void setSecondWord(final String pSecondWord) {
        this.aSecondWord = pSecondWord;
    }

    /**
     * Indicates whether a second token was present.
     *
     * @return {@code true} if {@link #getSecondWord()} is not {@code null}
     */
    public boolean hasSecondWord() {
        return this.aSecondWord != null;
    } // hasSecondWord()

    /**
     * Indicates whether this command is unknown.
     *
     * @return always {@code false}; unknown input is represented by {@code null} commands
     */
    public boolean isUnknown() {
        return false;
    } // isUnknown()

    /**
     * Executes the command against the current game engine.
     *
     * @param pGameEngine the game engine to update
     * @return {@code true} when the command requests that the game ends
     */
    public abstract boolean execute(final GameEngine pGameEngine);
} // Command
