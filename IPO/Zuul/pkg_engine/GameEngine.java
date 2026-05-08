package pkg_engine;

import pkg_commands.Parser;

import pkg_entities.Attack;
import pkg_entities.Item;
import pkg_entities.MovingTrainer;
import pkg_entities.Player;
import pkg_entities.Pokemon;
import pkg_entities.Room;
import pkg_entities.Trainer;
import pkg_entities.TransporterRoom;

import java.util.ArrayList;

/**
 * Central coordinator for game state, room navigation, and command handling.
 *
 * <p>Responsibilities include building the world graph, maintaining the current room and a stack
 * for the {@code back} command, delegating user input to the {@link Parser}, and driving output
 * through the {@link UserInterface}.
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

    private ArrayList<MovingTrainer> aMovingTrainers;
    private java.util.HashMap<String, Room> aRooms;
    private BattleManager aBattleManager;
    private MusicPlayer aMusicPlayer;

    /**
     * Builds the default map and creates the command parser.
     *
     * <p>The GUI is not set here; call {@link #setGUI(UserInterface)} after the interface exists so
     * welcome text and images can be shown.
     */
    public GameEngine() {
        this.createRooms();
    } // GameEngine()

    /**
     * Attaches the user interface used for all player feedback.
     *
     * @param pUserInterface the Swing UI bound to this engine; must not be {@code null} for normal
     *     play
     */
    public void setGUI(final UserInterface pUserInterface) {
        this.aGui = pUserInterface;
        this.aBattleManager = new BattleManager(this, pUserInterface);
        this.aMusicPlayer = new MusicPlayer("Images/Audio.mp3");
        this.aMusicPlayer.playLoop();
        this.printWelcome();
        this.losingTimer();
    }

    /**
     * Creates every {@link Room}, connects exits, places {@link Item}s, and sets the player's
     * starting location.
     */
    private void createRooms() {
        this.aRooms = new java.util.HashMap<>();
        ArrayList<Room> vTeleportableRooms = new ArrayList<Room>();
        Room house,
                littleroot,
                route101,
                oldale,
                route102,
                petalburg,
                petalburgWoods,
                rustboro,
                skyPillar;
        TransporterRoom cascade;

        house = new Room("in your house in Littleroot Town.", "home.gif", "home_map.jpeg");
        house.setRoomId("house");
        this.aRooms.put("house", house);
        vTeleportableRooms.add(house);
        littleroot =
                new Room(
                        "in Littleroot Town, your home town.",
                        "littleroot town.gif",
                        "littleroot_map.jpeg");
        littleroot.setRoomId("littleroot");
        this.aRooms.put("littleroot", littleroot);
        vTeleportableRooms.add(littleroot);
        route101 =
                new Room(
                        "on Route 101; wild Pokémon might appear in the tall grass.",
                        "route 101.gif",
                        "route101_map.jpeg");
        route101.setRoomId("route101");
        this.aRooms.put("route101", route101);
        vTeleportableRooms.add(route101);
        oldale =
                new Room(
                        "in Oldale Town, a small junction with a Pokémon Center.",
                        "oldale town.gif",
                        "oldale town_map.jpeg");
        oldale.setRoomId("oldale");
        this.aRooms.put("oldale", oldale);
        vTeleportableRooms.add(oldale);
        route102 =
                new Room(
                        "on Route 102; wild Pokémon might appear in the tall grass.",
                        "route 102.gif",
                        "route102_map.jpeg");
        route102.setRoomId("route102");
        this.aRooms.put("route102", route102);
        vTeleportableRooms.add(route102);
        petalburg =
                new Room(
                        "in Petalburg City, where your father is the Gym Leader.",
                        "petalburg city.gif",
                        "petalburg city_map.jpeg");
        petalburg.setRoomId("petalburg");
        this.aRooms.put("petalburg", petalburg);
        vTeleportableRooms.add(petalburg);
        petalburgWoods =
                new Room("in Petalburg Woods.", "petalburg woods.gif", "petalburg woods_map.jpeg");
        petalburgWoods.setRoomId("petalburgWoods");
        this.aRooms.put("petalburgWoods", petalburgWoods);
        vTeleportableRooms.add(petalburgWoods);
        rustboro =
                new Room(
                        "in Rustboro City, where the Devon Corporation is located.",
                        "rustboro city.gif",
                        "rustboro city_map.jpeg");
        rustboro.setRoomId("rustboro");
        this.aRooms.put("rustboro", rustboro);
        vTeleportableRooms.add(rustboro);
        skyPillar =
                new Room(
                        "on the Sky Pillar, Rayquaza's home.",
                        "sky pillar.gif",
                        "sky pillar_map.jpeg");
        skyPillar.setRoomId("skyPillar");
        this.aRooms.put("skyPillar", skyPillar);

        cascade =
                new TransporterRoom(
                        "in the cascade's vortex! Who knows where you'll end up?",
                        "cascade.gif",
                        "cascade_map.jpeg",
                        vTeleportableRooms);
        cascade.setRoomId("cascade");
        this.aRooms.put("cascade", cascade);

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
        littleroot.addItem(
                "grant",
                new Item(
                        "grant: a student grant you obtain thanks to your grades at school, to help"
                                + " you on your journey.",
                        0));
        rustboro.addItem(
                "wallet",
                new Item(
                        "wallet: someone's wallet; they must have left it here. It probably"
                                + " contains some money.",
                        0));
        petalburg.addItem(
                "delta-orb",
                new Item("delta-orb: the Delta Orb, which lets you summon Rayquaza.", 150));

        this.aMovingTrainers = new ArrayList<MovingTrainer>();
        Trainer Rayquaza;
        MovingTrainer Javier, Zucko, Ichigo;
        Javier =
                new MovingTrainer(
                        "Javier", "a young Pokémon trainer from Littleroot Town.", route101);
        this.aMovingTrainers.add(Javier);
        Zucko =
                new MovingTrainer(
                        "Zucko", "an experienced Pokémon trainer from Rustboro City.", route102);
        this.aMovingTrainers.add(Zucko);
        Ichigo =
                new MovingTrainer(
                        "Ichigo",
                        "a skilled Pokémon trainer who lives in Petalburg Woods.",
                        petalburgWoods);
        this.aMovingTrainers.add(Ichigo);
        Rayquaza = new Trainer("Rayquaza", "the legendary Rayquaza, who protects the skies.");

        route101.addTrainer(Javier);
        route102.addTrainer(Zucko);
        petalburgWoods.addTrainer(Ichigo);
        skyPillar.addTrainer(Rayquaza);

        // Attacks
        Attack tackle = new Attack("Tackle", 15, 100);
        Attack bite = new Attack("Bite", 25, 100);
        Attack waterGun = new Attack("Water Gun", 35, 100);
        Attack hydroPump = new Attack("Hydro Pump", 95, 75);
        Attack dragonClaw = new Attack("Dragon Claw", 50, 100);
        Attack fireBlast = new Attack("Fire Blast", 80, 85);
        Attack flamethrower = new Attack("Flamethrower", 60, 100);
        Attack iceBeam =
                new Attack("Ice Beam", 70, 100); // Made 100% accurate for reliable player damage
        Attack earthquake = new Attack("Earthquake", 60, 100);
        Attack outrage =
                new Attack("Outrage", 80, 70); // High damage, but bosses will miss 30% of the time
        Attack dragonPulse = new Attack("Dragon Pulse", 45, 100);
        Attack ironHead = new Attack("Iron Head", 45, 100);
        Attack hyperBeam =
                new Attack("Hyper Beam", 110, 50); // Rayquaza's nuke, but it misses half the time
        Attack extremeSpeed = new Attack("Extreme Speed", 40, 100);
        Attack darkPulse = new Attack("Dark Pulse", 55, 100);
        Attack waterShuriken = new Attack("Water Shuriken", 60, 100);

        // Javier's team
        Pokemon Poochyena = new Pokemon("Poochyena", 40, new Attack[] {tackle, bite});
        Pokemon Rattata = new Pokemon("Rattata", 40, new Attack[] {tackle, bite});

        // Zucko's team
        Pokemon Blastoise =
                new Pokemon("Blastoise", 120, new Attack[] {waterGun, tackle, bite, hydroPump});
        Pokemon Gyarados =
                new Pokemon("Gyarados", 130, new Attack[] {waterGun, bite, dragonClaw, hydroPump});
        Pokemon Metagross =
                new Pokemon("Metagross", 140, new Attack[] {ironHead, tackle, bite, earthquake});

        // Ichigo's team
        Pokemon Dragonite =
                new Pokemon(
                        "Dragonite", 140, new Attack[] {dragonClaw, bite, tackle, extremeSpeed});
        Pokemon Salamence =
                new Pokemon(
                        "Salamence", 150, new Attack[] {dragonClaw, flamethrower, tackle, bite});
        Pokemon Garchomp =
                new Pokemon("Garchomp", 160, new Attack[] {dragonClaw, earthquake, bite, tackle});
        Pokemon Hydreigon =
                new Pokemon("Hydreigon", 170, new Attack[] {darkPulse, dragonPulse, tackle, bite});

        // Rayquaza - boss fight.
        Pokemon RayquazaPokemon =
                new Pokemon(
                        "Rayquaza", 500, new Attack[] {outrage, hyperBeam, extremeSpeed, tackle});

        // Player team
        Pokemon Charizard =
                new Pokemon(
                        "Charizard", 400, new Attack[] {flamethrower, fireBlast, dragonClaw, bite});
        Pokemon Greninja =
                new Pokemon(
                        "Greninja",
                        380,
                        new Attack[] {waterShuriken, hydroPump, iceBeam, darkPulse});
        Pokemon Incineroar =
                new Pokemon(
                        "Incineroar",
                        420,
                        new Attack[] {flamethrower, fireBlast, bite, earthquake});

        // Setting Javier's team
        Javier.setPokemon(1, Poochyena);
        Javier.setPokemon(2, Rattata);

        // Setting Zucko's team
        Zucko.setPokemon(1, Blastoise);
        Zucko.setPokemon(2, Gyarados);
        Zucko.setPokemon(3, Metagross);

        // Setting Ichigo's team
        Ichigo.setPokemon(1, Dragonite);
        Ichigo.setPokemon(2, Salamence);
        Ichigo.setPokemon(3, Garchomp);
        Ichigo.setPokemon(4, Hydreigon);

        // Setting Rayquaza
        Rayquaza.setPokemon(1, RayquazaPokemon);

        // Setting Player team
        this.aPlayer = new Player("Luke", house);
        this.aPlayer.setPokemon(1, Charizard);
        this.aPlayer.setPokemon(2, Greninja);
        this.aPlayer.setPokemon(3, Incineroar);

        this.aIsTest = false;
    } // createRooms()

    /**
     * Disables input, pauses briefly, then exits the JVM.
     *
     * <p>The sleeps are used to give the player time to read the final messages.
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
     * Prints the welcome banner, initial location text, and the starting room image when one is
     * configured.
     */
    public void printWelcome() {
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

    /** Prints the long description of the current room (including exits and items). */
    private void printLocationInfo() {
        this.aGui.print(this.aPlayer.getCurrentRoom().getLongDescription());
    } // printLocationInfo()

    /**
     * Decreases the progress bar over time and ends the game when it reaches 0.
     *
     * <p>This method runs on the calling thread and sleeps one second between updates.
     */
    private void losingTimer() {
        for (int vI = 100; vI >= 0; vI -= 1) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignore) {
            }
            this.aGui.setProgress(this.aGui.getProgressBar() - 1);
            if (this.aPlayer.hasWon()) {
                this.aGui.setProgressString("You won!");
                return;
            }
        }
        this.aGui.setProgressString("You lost..");
        this.aGui.println("Time's up! You lose!");
        this.aGui.showImage("lose screen.png");
        this.endGame();
    } // losingTimer()

    /**
     * Indicates whether scripted test mode is active.
     *
     * @return {@code true} while commands are being read from a test file
     */
    public boolean isTest() {
        return this.aIsTest;
    }

    /**
     * Sets whether the engine is running scripted test commands.
     *
     * @param pIsTest {@code true} while a test file is being executed
     */
    public void setTest(final boolean pIsTest) {
        this.aIsTest = pIsTest;
    }

    /**
     * Returns the current player.
     *
     * @return the player state object
     */
    public Player getPlayer() {
        return this.aPlayer;
    }

    /**
     * Returns the active user interface.
     *
     * @return the GUI attached to this engine
     */
    public UserInterface getGui() {
        return this.aGui;
    }

    /**
     * Returns all trainers that can move between rooms.
     *
     * @return moving trainers in the world
     */
    public ArrayList<MovingTrainer> getMovingTrainers() {
        return this.aMovingTrainers;
    }

    /**
     * Looks up a room by its save/load id.
     *
     * @param pRoomId the room id
     * @return the matching room, or {@code null}
     */
    public Room getRoomById(final String pRoomId) {
        return this.aRooms.get(pRoomId);
    }

    /**
     * Returns all known room ids.
     *
     * @return a set of room ids
     */
    public java.util.Set<String> getAllRoomIds() {
        return this.aRooms.keySet();
    }

    /**
     * Returns the battle manager.
     *
     * @return the battle manager used for trainer fights
     */
    public BattleManager getBattleManager() {
        return this.aBattleManager;
    }
} // GameEngine
