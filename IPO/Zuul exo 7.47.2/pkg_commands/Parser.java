package pkg_commands;

import java.util.StringTokenizer;

/**
 * </p>
 *
 * @author Michael Kolling and David J. Barnes + D. Bureau
 * @version 2008.03.30 + 2013.09.15
 */


public class Parser {
    /** Catalogue of valid primary command words. */
    private CommandWords aCommand;

    /**
     * Prepares the internal {@link CommandWords} table used during parsing.
     */
    public Parser() {
        this.aCommand = new CommandWords();
    } // Parser()

    /**
     * Tokenizes {@code pInputLine} and builds the corresponding {@link Command}.
     *
     * @param pInputLine raw user text; may be {@code null}, empty, or multi-word
     * @return a populated {@link Command}, possibly marked unknown
     */
    public Command getCommand(final String vInput) {
        String vInputLine = vInput;
        String vWord1;
        String vWord2;
        
        StringTokenizer tokenizer = new StringTokenizer(vInputLine);
        
        
        if (tokenizer.hasMoreTokens()) {
            vWord1 = tokenizer.nextToken();
        } else {
            vWord1 = null;
        }

        if (tokenizer.hasMoreTokens()) {
            vWord2 = tokenizer.nextToken();
        } else {
            vWord2 = null;
        }
        
        if (aCommand.isCommand(vWord1) == false) {
            return null;
        }
        
        Command command = aCommand.get(vWord1);
        command.setSecondWord(null);
        if(command != null) {
            command.setSecondWord(vWord2);
        }

        return command;
    } // getCommand()

    /**
     * Returns a space-separated list of all valid command words.
     *
     * @return human-readable command summary suitable for help text
     */
    public String getCommandString() {
        return this.aCommand.getCommandList();
    } // getCommandString()
} // Parser
