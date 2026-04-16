/**
 * Maintains the fixed vocabulary of primary commands understood by the {@link Parser}.
 * <p>
 * Each entry must stay synchronized with the branches handled in
 * {@link GameEngine#interpretCommand(String)}.
 * </p>
 *
 * @author Michael Kolling and David J. Barnes + D. Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    /** All valid first words, in display order for help text. */
    private String[] aValidCommands = { "go", "help", "quit", "look", "cashin", "back", "test", "name", "take", "drop", "inventory" };
    
    /**
     * Creates a CommandWords object containing all valid commands.
     */
    public CommandWords()
    {
    }
    
    /**
     * Tests membership of {@code pString} in the internal command table (case-sensitive).
     *
     * @param pString candidate verb, usually lower-case
     * @return {@code true} if {@code pString} is a known command word
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI = 0; vI < this.aValidCommands.length; vI++ ) {
            if ( this.aValidCommands[vI].equals( pString ) ) {
                return true;
            }
        }
        return false;
    } // isCommand()

    /**
     * Builds a single string listing every valid command, separated by spaces.
     *
     * @return space-delimited command list for help output
     */
    public String getCommandList()
    {
        StringBuilder vChaineDesCommandes = new StringBuilder();
        for ( String vCommand : this.aValidCommands ) {
            vChaineDesCommandes.append( vCommand ).append( " " );
        }
        return vChaineDesCommandes.toString();
    } // getCommandList()
} // CommandWords
