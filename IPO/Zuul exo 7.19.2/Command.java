/**
 * Class Command - a command of the Zuul adventure game.
 * A command is composed of a command word and a second word(optional).
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;
    
    /**
     * Create a command object. First and second words must be supplied, but
     * either one (or both) can be null.
     * @param pCommandWord The first word of the command. Null if the command 
     * was not recognized.
     * @param pSecondWord The second word of the command.
     */
    public Command (final String pCommandWord,final String pSecondWord)
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    } // Command()
    
    /**
     * Return the command word (the first word) of this command. 
     * If the command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    } // getCommandWord()
    
    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    } // getSecondWord()
    
    /**
     * Check whether a second word was provided for this command.
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord!=null;
    } // hasSecondWord()
    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return this.aCommandWord==null;
    } // isUnknown()
} // Command
