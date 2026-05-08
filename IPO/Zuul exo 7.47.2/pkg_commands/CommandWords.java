package pkg_commands;

import pkg_engine.GameEngine;

import java.util.HashMap;

/**
 * Maintains the fixed vocabulary of primary commands understood by the game.
 * <p>
 * Each entry must stay synchronized with the branches handled in
 * {@link GameEngine#interpretCommand(String)}.
 * </p>
 *
 * @author Michael Kolling and David J. Barnes + D. Bureau
 * @version 2008.03.30 + 2019.09.25
 */


public class CommandWords {
    /** All valid command objects mapped by their command word. */
    private HashMap<String, Command> aCommands;

    /**
     * Creates a CommandWords object containing all valid commands.
     */
    public CommandWords() {
        aCommands = new HashMap<String, Command>();
        aCommands.put("go", new GoCommand());
        aCommands.put("help", new HelpCommand());
        aCommands.put("quit", new QuitCommand());
        aCommands.put("look", new LookCommand());
        aCommands.put("cashin", new CashinCommand());
        aCommands.put("back", new BackCommand());
        aCommands.put("test", new TestCommand());
        aCommands.put("alea", new AleaCommand());
        aCommands.put("name", new NameCommand());
        aCommands.put("take", new TakeCommand());
        aCommands.put("drop", new DropCommand());
        aCommands.put("inventory", new InventoryCommand());
    }

    /**
     * Given a command word, find and return the matching command object.
     * Return null if there is no command with this name.
     */
    public Command get(final String pWord) {
        return aCommands.get(pWord);
    }

    /**
     * Tests membership of {@code pString} in the internal command table
     * (case-sensitive).
     *
     * @param pString candidate verb, usually lower-case
     * @return {@code true} if {@code pString} is a known command word
     */
    public boolean isCommand(final String pString) {
        return aCommands.containsKey(pString);
    } // isCommand()

    /**
     * Builds a single string listing every valid command, separated by spaces.
     *
     * @return space-delimited command list for help output
     */
    public String getCommandList() {
        String vChaineDesCommandes = "";
        for (String vCommand : aCommands.keySet()) {
            vChaineDesCommandes += vCommand + " ";
        }
        return vChaineDesCommandes;
    } // getCommandList()
} // CommandWords
