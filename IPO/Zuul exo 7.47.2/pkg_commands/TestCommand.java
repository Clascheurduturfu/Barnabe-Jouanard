package pkg_commands;

import pkg_engine.GameEngine;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Implementation of the 'test' user command.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */


public class TestCommand extends Command
{
    public TestCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("You need a file name !");
            return false;
        }

        gameEngine.setTest(true);

        String vFileName = getSecondWord();
        vFileName = vFileName + ".txt";

        InputStream vIS = this.getClass().getClassLoader().getResourceAsStream(vFileName);

        if (vIS == null) {
            gameEngine.getGui().println("File not found: " + vFileName);
            return false;
        }

        Scanner vSC = new Scanner(vIS);

        Parser vParser = new Parser();

        while (vSC.hasNextLine()) {
            String vLigne = vSC.nextLine();
            gameEngine.getGui().println("\n\n> " + vLigne + "\n");
            Command vCommand = vParser.getCommand(vLigne);
            if (vCommand != null) {
                vCommand.execute(gameEngine);
            }
        }

        vSC.close();
        gameEngine.setTest(false);
        return false;
    }
}
