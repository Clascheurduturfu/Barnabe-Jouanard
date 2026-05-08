package pkg_commands;

import java.util.StringTokenizer;

/**
 * Converts raw user input into executable command objects.
 *
 * <p>The parser reads at most two tokens: the command word and one optional argument.
 *
 * @author Michael Kolling and David J. Barnes + D. Bureau
 * @version 2008.03.30 + 2013.09.15
 */
public class Parser {
    /** Catalogue of valid primary command words. */
    private CommandWords aCommand;

    /** Prepares the internal {@link CommandWords} table used during parsing. */
    public Parser() {
        this.aCommand = new CommandWords();
    } // Parser()

    /**
     * Tokenizes {@code pInputLine} and builds the corresponding {@link Command}.
     *
     * @param pInput raw user text; may be {@code null}, empty, or multi-word
     * @return a populated {@link Command}, possibly marked unknown
     */
    public Command getCommand(final String pInput) {
        final String vInputLine = pInput;
        String vWord1;
        String vWord2;

        final StringTokenizer tokenizer = new StringTokenizer(vInputLine);

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

        if (this.aCommand.isCommand(vWord1) == false) {
            return null;
        }

        final Command vCommand = this.aCommand.get(vWord1);
        if (vCommand != null) {
            vCommand.setSecondWord(vWord2);
        }

        return vCommand;
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
