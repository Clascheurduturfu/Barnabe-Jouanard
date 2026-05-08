/**
 * Abstract superclass for all command classes.
 * Each user command is implemented by a specific command subclass.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */
public abstract class Command
{
    private String secondWord;

    public Command()
    {
        secondWord = null;
    }

    public String getSecondWord()
    {
        return secondWord;
    }

    public boolean hasSecondWord()
    {
        return secondWord != null;
    }

    public void setSecondWord(String secondWord)
    {
        this.secondWord = secondWord;
    }

    public abstract boolean execute(Player player);
}
