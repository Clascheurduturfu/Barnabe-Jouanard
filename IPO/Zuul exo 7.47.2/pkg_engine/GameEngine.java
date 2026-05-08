package pkg_engine;

import pkg_commands.Parser;
import pkg_entities.Item;
import pkg_entities.Player;
import pkg_entities.Room;
import pkg_entities.TransporterRoom;

import java.util.ArrayList;

/**
 * Central coordinator for game state, room navigation, and command handling.
 * <p>
 * Responsibilities include building the world graph, maintaining the current
 * room and a stack for the {@code back} command, delegating user input to the
 * {@link Parser}, and driving output through the {@link UserInterface}.
 * </p>
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */


public class GameEngine {
    /** User-facing output and optional room imagery. */
    private UserInterface aGui;
    /** Player state: current room, history, and inventory. */
    private Player aPlayer;
    /** Flag indicating whether the game is in test mode. */
    private boolean aIsTest;

    /**
     * Builds the default map and creates the command parser.
     * <p>
     * The GUI is not set here; call {@link #setGUI(UserInterface)} after the
     * interface exists so welcome text and images can be shown.
     * </p>
     */
    public GameEngine() {
        this.createRooms();
    } // GameEngine()

    /**
     * Attaches the user interface used for all player feedback.
     *
     * @param pUserInterface the Swing UI bound to this engine; must not be
     * {@code null} for normal play
     */
    public void setGUI(final UserInterface pUserInterface) {
        this.aGui = pUserInterface;
        this.printWelcome();
        this.losingTimer();
    }

    /**
     * Creates every {@link Room}, connects exits, places {@link Item}s, and sets
     * the player's starting location.
     */
    private void createRooms() {
        ArrayList<Room> vTeleportableRooms = new ArrayList<Room>();
        Room house, littleroot, route101, oldale, route102, petalburg, petalburgWoods, rustboro, skyPillar;
        TransporterRoom cascade;

        house = new Room("in your house in Littleroot Town.", "home.gif", "home_map.jpeg");
        vTeleportableRooms.add(house);
        littleroot = new Room("in Littleroot Town, your home town.", "littleroot town.gif", "littleroot_map.jpeg");
        vTeleportableRooms.add(littleroot);
        route101 = new Room("on Route 101; wild Pokémon might appear in the tall grass.", "route 101.gif", "route101_map.jpeg");
        vTeleportableRooms.add(route101);
        oldale = new Room("in Oldale Town, a small junction with a Pokémon Center.", "oldale town.gif", "oldale town_map.jpeg");
        vTeleportableRooms.add(oldale);
        route102 = new Room("on Route 102; wild Pokémon might appear in the tall grass.", "route 102.gif", "route102_map.jpeg");
        vTeleportableRooms.add(route102);
        petalburg = new Room("in Petalburg City, where your father is the Gym Leader.", "petalburg city.gif", "petalburg city_map.jpeg");
        vTeleportableRooms.add(petalburg);
        petalburgWoods = new Room("in Petalburg Woods.", "petalburg woods.gif", "petalburg woods_map.jpeg");
        vTeleportableRooms.add(petalburgWoods);
        rustboro = new Room("in Rustboro City, where the Devon Corporation is located.", "rustboro city.gif", "rustboro city_map.jpeg");
        vTeleportableRooms.add(rustboro);
        skyPillar = new Room("on the Sky Pillar, Rayquaza's home.", "sky pillar.gif", "sky pillar_map.jpeg");

        cascade = new TransporterRoom("in the cascade's vortex! Who knows where you'll end up?", "cascade.gif", "cascade_map.jpeg", vTeleportableRooms);

        house.setExit("west", littleroot);

        littleroot.setExit("north", route101);
        littleroot.setExit("east", house);

        route101.setExit("north", oldale);
        route101.setExit("south", littleroot);

        oldale.setExit("east", route102);
        oldale.setExit("south", route101);

        route102.setExit("north", petalburgWoods);
        route102.setExit("east", petalburg);
        route102.setExit("west", oldale);

        petalburg.setExit("north", petalburgWoods);
        petalburg.setExit("west", route102);
        petalburg.setExit("down", cascade);

        petalburgWoods.setExit("south", route102);
        petalburgWoods.setExit("west", rustboro);

        rustboro.setExit("up", skyPillar);
        rustboro.setExit("east", petalburgWoods);
        rustboro.setExit("south", oldale);

        skyPillar.setExit("down", rustboro);

        skyPillar.setAsWinningRoom();

        cascade.setAsTeleporterRoom();

        house.addItem("map", new Item("map: a map that helps you navigate.", 0));
        house.addItem("shoes", new Item("shoes: your shoes.", 100));
        littleroot.addItem("grant", new Item( "grant: a student grant you obtain thanks to your grades at school, to help you on your journey.", 0));
        rustboro.addItem("wallet", new Item("wallet: someone's wallet; they must have left it here. It probably contains some money.", 0));
        petalburg.addItem("delta-orb", new Item("delta-orb: the Delta Orb, which lets you summon Rayquaza.", 150));

        this.aPlayer = new Player("Luke", house);

        this.aIsTest = false;
    } // createRooms()

    /**
     * Disables input, pauses briefly, then exits the JVM.
     * <p>
     * The sleeps are used to give the player time to read the final messages.
     * </p>
     */
    public void endGame() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        this.aGui.enable(false);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {
        }
        System.exit(0);
    } // quit()

    /**
     * Prints the welcome banner, initial location text, and the starting room image
     * when one is configured.
     */
    public void printWelcome() {
        this.aGui.print("\n");
        this.aGui.println("Hello " + this.aPlayer.getName() + "!");
        this.aGui.println("You can change your name with the command: name <new name>.");
        this.aGui.println("Welcome to the world of Pokémon!");
        this.aGui.println("A wonderful world where you can live an adventure!");
        this.aGui.println("Type 'help' if you need help.");
        this.printLocationInfo();
        if (this.aPlayer.getCurrentRoom().getImageName() != null) {
            this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
        }
        if (this.aPlayer.getItem("map") == null) {
            this.aGui.showMap("no map.jpeg");
        }
    } // printWelcome()

    /**
     * Prints the long description of the current room (including exits and items).
     */
    private void printLocationInfo() {
        this.aGui.print(this.aPlayer.getCurrentRoom().getLongDescription());
    } // printLocationInfo()

    /**
     * Decreases the progress bar over time and ends the game when it reaches 0.
     * <p>
     * This method runs on the calling thread and sleeps one second between
     * updates.
     * </p>
     */
    private void losingTimer() {
        for (int vI = 100; vI >= 0; vI -= 1) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignore) {
            }
            this.aGui.setProgress(this.aGui.getProgressBar() - 1);
            if (this.aPlayer.hasWon()) {
                return;
            }
        }
        this.aGui.println("Time's up! You lose!");
        this.aGui.showImage("lose screen.png");
        this.endGame();
    } // losingTimer()

    public boolean isTest() {
        return this.aIsTest;
    }

    public void setTest(boolean pIsTest) {
        this.aIsTest = pIsTest;
    }
    
    public Player getPlayer() {
        return this.aPlayer;
    }

    public UserInterface getGui() {
        return this.aGui;
    }
} // GameEngine