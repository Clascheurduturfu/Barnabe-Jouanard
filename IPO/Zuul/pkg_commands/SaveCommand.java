package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.Item;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Implements the {@code save} command, writing the current game state to a save file.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class SaveCommand extends Command {
    /** Creates the command. */
    public SaveCommand() {}

    /**
     * Saves player, room, item, and moving-trainer state.
     *
     * @param pGameEngine the game engine containing the state to save
     * @return {@code true} when saving succeeds
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("Save to what file? Usage: save <filename>");
            return false;
    }

        String vFileName = this.getSecondWord() + ".sav";

        try (FileWriter vWriter = new FileWriter(vFileName)) {
            // Player name
            vWriter.write("PLAYER_NAME:" + pGameEngine.getPlayer().getName() + "\n");

            // Current room
            vWriter.write("CURRENT_ROOM:" + pGameEngine.getPlayer().getCurrentRoom().getRoomId() + "\n");

            // Money
            vWriter.write("MONEY:" + pGameEngine.getPlayer().getMoney() + "\n");

            // Inventory
            vWriter.write("INVENTORY:");
            for (String vItemName : pGameEngine.getPlayer().getItems().keySet()) {
                Item vItem = pGameEngine.getPlayer().getItem(vItemName);
                vWriter.write(vItemName + "-" + vItem.getItemPrice() + ";");
            }
            vWriter.write("\n");

            // Back stack
            vWriter.write("BACK_STACK:");
            for (pkg_entities.Room vRoom : pGameEngine.getPlayer().getPreviousRooms()) {
                vWriter.write(vRoom.getRoomId() + ",");
            }
            vWriter.write("\n");

            // Moving trainers
            for (pkg_entities.MovingTrainer vTrainer : pGameEngine.getMovingTrainers()) {
                vWriter.write("MTRAINER:" + vTrainer.getName() + "-" + vTrainer.isDefeated() + "-" + vTrainer.getCurrentRoom().getRoomId() + "\n");
            }

            // Room states
            for (String vRoomId : pGameEngine.getAllRoomIds()) {
                pkg_entities.Room vRoom = pGameEngine.getRoomById(vRoomId);
                vWriter.write("ROOM:" + vRoomId + "-");
                for (String vItemName : vRoom.getItems().keySet()) {
                    vWriter.write(vItemName + "=" + vRoom.getItems().get(vItemName).getItemPrice() + ";");
                }
                vWriter.write("\n");
            }

            pGameEngine.getGui().println("Game saved to " + vFileName);
            pGameEngine.getGui().println("Goodbye!");

            return true;

        } catch (IOException e) {
            pGameEngine.getGui().println("Error saving game: " + e.getMessage());
            return false;
        }
    } // execute()
}
