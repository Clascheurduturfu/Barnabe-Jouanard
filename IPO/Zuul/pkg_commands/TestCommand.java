package pkg_commands;

import pkg_engine.GameEngine;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Implements the {@code test} command, replaying commands from a text resource.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class TestCommand extends Command {
    /** Creates the command. */
    public TestCommand() {}

    /**
     * Runs each command from the named test file.
     *
     * @param pGameEngine the game engine to drive with scripted input
     * @return always {@code false}; this command does not end the game directly
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("You need a file name!");
            return false;
        }

        pGameEngine.setTest(true);

        String vFileName = this.getSecondWord() + ".txt";
        InputStream vInputStream = this.getClass().getClassLoader().getResourceAsStream(vFileName);

        if (vInputStream == null) {
            pGameEngine.getGui().println("File not found: " + vFileName);
            return false;
        }

        Scanner vScanner = new Scanner(vInputStream);
        Parser vParser = new Parser();

        while (vScanner.hasNextLine()) {
            String vLine = vScanner.nextLine();
            pGameEngine.getGui().println("\n\n> " + vLine + "\n");
            Command vCommand = vParser.getCommand(vLine);
            if (vCommand != null) {
                vCommand.execute(pGameEngine);
            }
        }

        vScanner.close();
        pGameEngine.setTest(false);
        return false;
    } // execute()
}
