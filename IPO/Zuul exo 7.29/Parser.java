import java.util.StringTokenizer;

/**
 * Turns a single input line into a {@link Command} for the adventure game.
 * <p>
 * The first token becomes the command word; the second token (if any) is kept as
 * the command's second word. Additional tokens on the same line are ignored.
 * Unknown first words yield a command whose {@link Command#getCommandWord()} is
 * {@code null} so that {@link Command#isUnknown()} is {@code true}.
 * </p>
 *
 * @author Michael Kolling and David J. Barnes + D. Bureau
 * @version 2008.03.30 + 2013.09.15
 */
public class Parser
{
    /** Catalogue of valid primary command words. */
    private CommandWords aValidCW;

    /**
     * Prepares the internal {@link CommandWords} table used during parsing.
     */
    public Parser()
    {
        this.aValidCW = new CommandWords();
    } // Parser()

    /**
     * Tokenizes {@code pInputLine} and builds the corresponding {@link Command}.
     *
     * @param pInputLine raw user text; may be {@code null}, empty, or multi-word
     * @return a populated {@link Command}, possibly marked unknown
     */
    public Command getCommand( final String pInputLine )
    {
        String vWord1;
        String vWord2;

        StringTokenizer tokenizer = new StringTokenizer( pInputLine );

        if ( tokenizer.hasMoreTokens() )
            vWord1 = tokenizer.nextToken();
        else
            vWord1 = null;

        if ( tokenizer.hasMoreTokens() )
            vWord2 = tokenizer.nextToken();
        else
            vWord2 = null;

        if ( this.aValidCW.isCommand( vWord1 ) )
            return new Command( vWord1, vWord2 );
        else
            return new Command( null, vWord2 );
    } // getCommand()

    /**
     * Returns a space-separated list of all valid command words.
     *
     * @return human-readable command summary suitable for help text
     */
    public String getCommandString()
    {
        return this.aValidCW.getCommandList();
    } // getCommandString()
} // Parser
