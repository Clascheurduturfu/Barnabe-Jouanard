package pkg_commands;
import pkg_engine.GameEngine;
import pkg_entities.Item;
import pkg_entities.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementation of the 'load' user command.
 */
public class LoadCommand extends Command
{
    public LoadCommand()
    {
    }

    public boolean execute(GameEngine gameEngine)
    {
        if (!hasSecondWord()) {
            gameEngine.getGui().println("Load from what file? Usage: load <filename>");
            return false;
        }

        String vFileName = getSecondWord() + ".sav";

        try (BufferedReader vReader = new BufferedReader(new FileReader(vFileName))) {
            String vLine;

            // Clear player inventory first
            for (String vItemName : new java.util.ArrayList<>(gameEngine.getPlayer().getItems().keySet())) {
                gameEngine.getPlayer().removeItem(vItemName);
            }

            // Clear back stack
            gameEngine.getPlayer().getPreviousRooms().clear();

            while ((vLine = vReader.readLine()) != null) {
                if (vLine.startsWith("PLAYER_NAME:")) {
                    gameEngine.getPlayer().setName(vLine.substring(12));

                } else if (vLine.startsWith("CURRENT_ROOM:")) {
                    Room vRoom = gameEngine.getRoomById(vLine.substring(13));
                    if (vRoom != null) {
                        gameEngine.getPlayer().setCurrentRoom(vRoom);
                    }

                } else if (vLine.startsWith("MONEY:")) {
                    gameEngine.getPlayer().setMoney(Integer.parseInt(vLine.substring(6)));

                } else if (vLine.startsWith("INVENTORY:")) {
                    String vItems = vLine.substring(10);
                    if (!vItems.isEmpty()) {
                        for (String vPair : vItems.split(";")) {
                            if (!vPair.isEmpty()) {
                                String[] vParts = vPair.split("-");
                                if (vParts.length == 2) {
                                    gameEngine.getPlayer().addItem(vParts[0], new Item(vParts[0], Integer.parseInt(vParts[1])));
                                }
                            }
                        }
                    }

                } else if (vLine.startsWith("BACK_STACK:")) {
                    String vRooms = vLine.substring(11);
                    if (!vRooms.isEmpty()) {
                        for (String vRoomId : vRooms.split(",")) {
                            if (!vRoomId.isEmpty()) {
                                Room vRoom = gameEngine.getRoomById(vRoomId);
                                if (vRoom != null) {
                                    gameEngine.getPlayer().getPreviousRooms().push(vRoom);
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

                        for (pkg_entities.MovingTrainer vTrainer : gameEngine.getMovingTrainers()) {
                            if (vTrainer.getName().equals(vName)) {
                                vTrainer.setDefeated(vDefeated);

                                Room vOldRoom = vTrainer.getCurrentRoom();
                                Room vNewRoom = gameEngine.getRoomById(vRoomId);
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

                        Room vRoom = gameEngine.getRoomById(vRoomId);
                        if (vRoom != null) {
                            // Clear existing items
                            for (String vItemName : new java.util.ArrayList<>(vRoom.getItems().keySet())) {
                                vRoom.removeItem(vItemName);
                            }

                            // Add saved items
                            if (!vItemsData.isEmpty()) {
                                for (String vItemPair : vItemsData.split(";")) {
                                    if (!vItemPair.isEmpty()) {
                                        String[] vItemParts = vItemPair.split("=");
                                        if (vItemParts.length == 2) {
                                            vRoom.addItem(vItemParts[0], new Item(vItemParts[0], Integer.parseInt(vItemParts[1])));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            gameEngine.getGui().println("Game loaded from " + vFileName);
            gameEngine.getGui().println(gameEngine.getPlayer().getCurrentRoom().getLongDescription());
            if (gameEngine.getPlayer().getCurrentRoom().getImageName() != null) {
                gameEngine.getGui().showImage(gameEngine.getPlayer().getCurrentRoom().getImageName());
                if (gameEngine.getPlayer().getItem("map") != null) {
                    gameEngine.getGui().showMap(gameEngine.getPlayer().getCurrentRoom().getMapImageName());
                }
            }

            return false;

        } catch (IOException e) {
            gameEngine.getGui().println("Error loading game: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            gameEngine.getGui().println("Save file corrupted!");
            return false;
        }
    }
}
