package pkg_commands;

import pkg_engine.GameEngine;

import pkg_entities.Item;
import pkg_entities.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Implements the {@code load} command, restoring game state from a save file.
 *
 * @author Barnabe Jouanard
 * @version 2026.05.08
 */
public class LoadCommand extends Command {
    /** Creates the command. */
    public LoadCommand() {}

    /**
     * Loads player, room, item, and moving-trainer state.
     *
     * @param pGameEngine the game engine to restore into
     * @return always {@code false}; loading does not end the game
     */
    public boolean execute(final GameEngine pGameEngine) {
        if (!this.hasSecondWord()) {
            pGameEngine.getGui().println("Load from what file? Usage: load <filename>");
            return false;
        }

        String vFileName = this.getSecondWord() + ".sav";

        try (BufferedReader vReader = new BufferedReader(new FileReader(vFileName))) {
            String vLine;

            // Clear player inventory first
            for (String vItemName :new ArrayList<>(pGameEngine.getPlayer().getItems().keySet())) {
                pGameEngine.getPlayer().removeItem(vItemName);
            }

            // Clear back stack
            pGameEngine.getPlayer().getPreviousRooms().clear();

            while ((vLine = vReader.readLine()) != null) {
                if (vLine.startsWith("PLAYER_NAME:")) {
                    pGameEngine.getPlayer().setName(vLine.substring(12));

                } else if (vLine.startsWith("CURRENT_ROOM:")) {
                    Room vRoom = pGameEngine.getRoomById(vLine.substring(13));
                    if (vRoom != null) {
                        pGameEngine.getPlayer().setCurrentRoom(vRoom);
                    }

                } else if (vLine.startsWith("MONEY:")) {
                    pGameEngine.getPlayer().setMoney(Integer.parseInt(vLine.substring(6)));

                } else if (vLine.startsWith("INVENTORY:")) {
                    String vItems = vLine.substring(10);
                    if (!vItems.isEmpty()) {
                        for (String vPair : vItems.split(";")) {
                            if (!vPair.isEmpty()) {
                                String[] vParts = vPair.split("-");
                                if (vParts.length == 2) {
                                    pGameEngine.getPlayer().addItem(vParts[0],new Item(vParts[0],Integer.parseInt(vParts[1])));
                                }
                            }
                        }
                    }

                } else if (vLine.startsWith("BACK_STACK:")) {
                    String vRooms = vLine.substring(11);
                    if (!vRooms.isEmpty()) {
                        for (String vRoomId : vRooms.split(",")) {
                            if (!vRoomId.isEmpty()) {
                                Room vRoom = pGameEngine.getRoomById(vRoomId);
                                if (vRoom != null) {
                                    pGameEngine.getPlayer().getPreviousRooms().push(vRoom);
                                }
                            }
                        }
                    }

                } else if (vLine.startsWith("MTRAINER:")) {
                    String vData = vLine.substring(9);
                    String[] vParts = vData.split("-");
                    if (vParts.length == 3) {
                        String vName = vParts[0];
                        boolean vDefeated = Boolean.parseBoolean(vParts[1]);
                        String vRoomId = vParts[2];

                        for (pkg_entities.MovingTrainer vTrainer :
                                pGameEngine.getMovingTrainers()) {
                            if (vTrainer.getName().equals(vName)) {
                                vTrainer.setDefeated(vDefeated);

                                Room vOldRoom = vTrainer.getCurrentRoom();
                                Room vNewRoom = pGameEngine.getRoomById(vRoomId);
                                if (vOldRoom != null && vNewRoom != null && !vOldRoom.equals(vNewRoom)) {
                                    vOldRoom.removeTrainer(vName);
                                    vNewRoom.addTrainer(vTrainer);
                                    vTrainer.setCurrentRoom(vNewRoom);
                                }
                                break;
                            }
                        }
                    }

                } else if (vLine.startsWith("ROOM:")) {
                    String vData = vLine.substring(5);
                    String[] vParts = vData.split("-");
                    if (vParts.length == 2) {
                        String vRoomId = vParts[0];
                        String vItemsData = vParts[1];

                        Room vRoom = pGameEngine.getRoomById(vRoomId);
                        if (vRoom != null) {
                            // Clear existing items
                            for (String vItemName : new ArrayList<>(vRoom.getItems().keySet())) {
                                vRoom.removeItem(vItemName);
                            }

                            // Add saved items
                            if (!vItemsData.isEmpty()) {
                                for (String vItemPair : vItemsData.split(";")) {
                                    if (!vItemPair.isEmpty()) {
                                        String[] vItemParts = vItemPair.split("=");
                                        if (vItemParts.length == 2) {
                                            vRoom.addItem(vItemParts[0],new Item(vItemParts[0],Integer.parseInt(vItemParts[1])));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            pGameEngine.getGui().println("Game loaded from " + vFileName);
            pGameEngine.getGui().println(pGameEngine.getPlayer().getCurrentRoom().getLongDescription());
            if (pGameEngine.getPlayer().getCurrentRoom().getImageName() != null) {
                pGameEngine.getGui().showImage(pGameEngine.getPlayer().getCurrentRoom().getImageName());
                if (pGameEngine.getPlayer().getItem("map") != null) {
                    pGameEngine.getGui().showMap(pGameEngine.getPlayer().getCurrentRoom().getMapImageName());
                }
            }

            return false;

        } catch (IOException e) {
            pGameEngine.getGui().println("Error loading game: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            pGameEngine.getGui().println("Save file corrupted!");
            return false;
        }
    } // execute()
}
