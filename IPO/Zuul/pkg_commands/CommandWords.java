package pkg_commands;

import java.util.HashMap;

/**
 * Maintains the fixed vocabulary of primary commands understood by the game.
 *
 * <p>Each entry maps a command word to the concrete {@link Command} that executes it.
 *
 * @author Michael Kolling and David J. Barnes + D. Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords {
    /** All valid command objects mapped by their command word. */
    private HashMap<String, Command> aCommands;

    /** Creates a CommandWords object containing all valid commands. */
    public CommandWords() {
        this.aCommands = new HashMap<String, Command>();
        this.aCommands.put("go", new GoCommand());
        this.aCommands.put("help", new HelpCommand());
        this.aCommands.put("quit", new QuitCommand());
        this.aCommands.put("look", new LookCommand());
        this.aCommands.put("cashin", new CashinCommand());
        this.aCommands.put("back", new BackCommand());
        this.aCommands.put("test", new TestCommand());
        this.aCommands.put("alea", new AleaCommand());
        this.aCommands.put("battle", new BattleCommand());
        this.aCommands.put("name", new NameCommand());
        this.aCommands.put("take", new TakeCommand());
        this.aCommands.put("drop", new DropCommand());
        this.aCommands.put("inventory", new InventoryCommand());
        this.aCommands.put("save", new SaveCommand());
        this.aCommands.put("load", new LoadCommand());
    } // CommandWords()

    /**
     * Given a command word, finds and returns the matching command object.
     *
     * @param pWord the command word to look up
     * @return the matching command object, or {@code null}
     */
    public Command get(final String pWord) {
        return this.aCommands.get(pWord);
    } // get()

    /**
     * Tests membership of {@code pString} in the internal command table (case-sensitive).
     *
     * @param pString candidate verb, usually lower-case
     * @return {@code true} if {@code pString} is a known command word
     */
    public boolean isCommand(final String pString) {
        return this.aCommands.containsKey(pString);
    } // isCommand()

    /**
     * Builds a single string listing every valid command, separated by spaces.
     *
     * @return space-delimited command list for help output
     */
    public String getCommandList() {
        String vCommandList = "";
        for (String vCommand : this.aCommands.keySet()) {
            vCommandList += vCommand + " ";
    }
        return vCommandList;
    } // getCommandList()
} // CommandWords
