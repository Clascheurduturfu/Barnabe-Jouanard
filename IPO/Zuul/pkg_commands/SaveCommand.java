package pkg_commands;
import pkg_engine.GameEngine;
import pkg_entities.Item;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implementation of the 'save' user command.
 */
public class SaveCommand extends Command
{
    public SaveCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("Save to what file? Usage: save <filename>");
            return false;
        }

        String vFileName = getSecondWord() + ".sav";

        try (FileWriter vWriter = new FileWriter(vFileName)) {
            // Player name
            vWriter.write("PLAYER_NAME:" + gameEngine.getPlayer().getName() + "\n");

            // Current room
            vWriter.write("CURRENT_ROOM:" + gameEngine.getPlayer().getCurrentRoom().getRoomId() + "\n");

            // Money
            vWriter.write("MONEY:" + gameEngine.getPlayer().getMoney() + "\n");

            // Inventory
            vWriter.write("INVENTORY:");
            for (String vItemName : gameEngine.getPlayer().getItems().keySet()) {
                Item vItem = gameEngine.getPlayer().getItem(vItemName);
                vWriter.write(vItemName + "-" + vItem.getItemPrice() + ";");
            }
            vWriter.write("\n");

            // Back stack
            vWriter.write("BACK_STACK:");
            for (pkg_entities.Room vRoom : gameEngine.getPlayer().getPreviousRooms()) {
                vWriter.write(vRoom.getRoomId() + ",");
            }
            vWriter.write("\n");

            // Moving trainers
            for (pkg_entities.MovingTrainer vTrainer : gameEngine.getMovingTrainers()) {
                vWriter.write("MTRAINER:" + vTrainer.getName() + "-" + vTrainer.isDefeated() + "-" + vTrainer.getCurrentRoom().getRoomId() + "\n");
            }

            // Room states
            for (String vRoomId : gameEngine.getAllRoomIds()) {
                pkg_entities.Room vRoom = gameEngine.getRoomById(vRoomId);
                vWriter.write("ROOM:" + vRoomId + "-");
                for (String vItemName : vRoom.getItems().keySet()) {
                    vWriter.write(vItemName + "=" + vRoom.getItems().get(vItemName).getItemPrice() + ";");
                }
                vWriter.write("\n");
            }

            gameEngine.getGui().println("Game saved to " + vFileName);
            gameEngine.getGui().println("Goodbye!");

            return true;

        } catch (IOException e) {
            gameEngine.getGui().println("Error saving game: " + e.getMessage());
            return false;
        }
    }
}
