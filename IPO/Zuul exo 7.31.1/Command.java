/**
 * Immutable pair of tokens representing one player command.
 * <p>
 * The first token is the command verb; the second is an optional argument
 * (for example the direction in {@code go north}).
 * </p>
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Command
{
    /** Primary verb, or {@code null} when the input was not recognized. */
    private String aCommandWord;
    /** Optional second token; {@code null} if none was supplied. */
    private String aSecondWord;

    /**
     * Creates a command. Either argument may be {@code null} depending on user input.
     *
     * @param pCommandWord the recognized verb, or {@code null} if unknown
     * @param pSecondWord  the argument word, or {@code null} if absent
     */
    public Command( final String pCommandWord, final String pSecondWord )
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    } // Command()

    /**
     * Returns the command word (first token).
     *
     * @return the verb, or {@code null} if the command was not understood
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    } // getCommandWord()

    /**
     * Returns the second word of this command, if any.
     *
     * @return the second token, or {@code null}
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    } // getSecondWord()

    /**
     * Indicates whether a second token was present.
     *
     * @return {@code true} if {@link #getSecondWord()} is not {@code null}
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord != null;
    } // hasSecondWord()

    /**
     * Indicates whether the parser recognized the command word.
     *
     * @return {@code true} if {@link #getCommandWord()} is {@code null}
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    } // isUnknown()
} // Command
