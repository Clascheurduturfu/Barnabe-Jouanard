import java.util.Stack;

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
public class GameEngine
{
    /** The room the player currently occupies. */
    private Room aCurrentRoom;
    /** Rooms visited along the current path; used to support {@code back}. */
    private Stack<Room> aPreviousRooms = new Stack<Room>();
    /** Converts raw input lines into {@link Command} objects. */
    private Parser aParser;
    /** User-facing output and optional room imagery. */
    private UserInterface aGui;

    /**
     * Builds the default map and creates the command parser.
     * <p>
     * The GUI is not set here; call {@link #setGUI(UserInterface)} after the
     * interface exists so welcome text and images can be shown.
     * </p>
     */
    public GameEngine()
    {
        this.createRooms();
        this.aParser = new Parser();
    } // GameEngine()

    /**
     * Attaches the user interface used for all player feedback.
     *
     * @param pUserInterface the Swing UI bound to this engine; must not be {@code null}
     *                         for normal play
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Creates every {@link Room}, connects exits, places {@link Item}s, and sets
     * the player's starting location.
     */
    private void createRooms()
    {
        Room house, littleroot, route101, oldale, route102, petalburg, petalburgWoods, rustboro, skyPillar;

        house      = new Room( "in your house in Littleroot Town", "images/home.jpg" );
        littleroot = new Room( "in Littleroot Town, your home town", "images/littleroot town.jpg" );
        route101   = new Room( "on Route 101; wild Pokémon might appear in the tall grass", "images/route 101.jpg" );
        oldale     = new Room( "in Oldale Town, a small junction with a Pokémon Center", "images/oldaletown.jpg" );
        route102   = new Room( "on Route 102; wild Pokémon might appear in the tall grass", "images/route 102.jpg" );
        petalburg  = new Room( "in Petalburg City, where your father is the Gym Leader", "images/petalburg city.jpg" );
        petalburgWoods  = new Room( "in Petalburg Woods", "images/petalburg woods.jpg" );
        rustboro   = new Room( "in Rustboro City, where the Devon Corporation is located", "images/rustboro city.jpg" );
        skyPillar  = new Room( "at the Sky Pillar, Rayquaza's home", "images/sky pillar.jpg" );

        house.setExit( "west", littleroot );

        littleroot.setExit( "north", route101 );
        littleroot.setExit( "east", house );

        route101.setExit( "north", oldale );
        route101.setExit( "south", littleroot );

        oldale.setExit( "east", route102 );
        oldale.setExit( "south", route101 );

        route102.setExit( "north", petalburgWoods );
        route102.setExit( "east", petalburg );
        route102.setExit( "west", oldale );

        petalburg.setExit( "north", petalburgWoods );
        petalburg.setExit( "west", route102 );

        petalburgWoods.setExit( "south", route102 );
        petalburgWoods.setExit( "west", rustboro );

        rustboro.setExit( "up", skyPillar );
        rustboro.setExit( "east", petalburgWoods );
        rustboro.setExit( "south", oldale );

        skyPillar.setExit( "down", rustboro );

        house.addItem( "Map", new Item( "The map", 0 ) );
        house.addItem( "Shoes", new Item( "Your shoes", 0 ) );
        skyPillar.addItem( "Delta Orb", new Item( "The Delta Orb, which lets you summon Rayquaza", 100 ) );

        this.aCurrentRoom = house;
    } // createRooms()

    /**
     * Prints the welcome banner, initial location text, and the starting room image
     * when one is configured.
     */
    private void printWelcome()
    {
        this.aGui.print( "\n" );
        this.aGui.println( "Welcome to the world of Pokémon!" );
        this.aGui.println( "A wonderful world where you can live an adventure!" );
        this.aGui.println( "Type 'help' if you need help." );
        this.aGui.print( "\n" );
        this.printLocationInfo();
        if ( this.aCurrentRoom.getImageName() != null )
            this.aGui.showImage( this.aCurrentRoom.getImageName() );
    } // printWelcome()

    /**
     * Moves the player in the direction given as the command's second word.
     * <p>
     * If the direction is missing, or there is no exit, an explanatory message is
     * printed. Otherwise the current room is pushed onto the history stack before
     * changing rooms, then the new description and image are shown.
     * </p>
     *
     * @param pCommand a {@code go} command whose second token is the direction
     */
    private void goRoom( final Command pCommand )
    {
        if ( ! pCommand.hasSecondWord() ) {
            // If there is no second word, we don't know where to go.
            this.aGui.println( "Go where?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();

        Room vNextRoom = this.aCurrentRoom.getExit( vDirection );

        if ( vNextRoom == null )
            this.aGui.println( "There is no door!" );
        else {
            this.aPreviousRooms.push( this.aCurrentRoom );
            this.aCurrentRoom = vNextRoom;
            this.aGui.println( this.aCurrentRoom.getLongDescription() );
            if ( this.aCurrentRoom.getImageName() != null )
                this.aGui.showImage( this.aCurrentRoom.getImageName() );
        }
    } // goRoom()

    /**
     * Prints short narrative context and the list of valid command words.
     */
    private void printHelp()
    {
        this.aGui.println( "You are in the wonderful world of Pokémon." );
        this.aGui.println( "You are trying to stop Rayquaza from destroying Hoenn." );
        this.aGui.println( "Your command words are:" );
        this.aGui.println( this.aParser.getCommandString() );
    } // printHelp()

    /**
     * Prints the long description of the current room (including exits and items).
     */
    private void printLocationInfo()
    {
        this.aGui.print( this.aCurrentRoom.getLongDescription() );
    } // printLocationInfo()

    /**
     * Parses and executes one player command line.
     * <p>
     * Recognized verbs include {@code help}, {@code go}, {@code eat}, {@code look},
     * {@code back}, and {@code quit}. Unknown input produces a generic error message.
     * </p>
     *
     * @param pCommand the raw text entered by the player (may be empty)
     */
    public void interpretCommand( final String pCommand )
    {
        this.aGui.println( "> " + pCommand );
        Command vCommand = this.aParser.getCommand( pCommand );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "I don't know what you mean..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) )
            this.printHelp();
        else if ( vCommandWord.equals( "go" ) )
            this.goRoom( vCommand );
        else if ( vCommandWord.equals( "eat" ) )
            this.eat();
        else if ( vCommandWord.equals( "look" ) )
            this.look();
        else if ( vCommandWord.equals( "back" ) )
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Back what?" );
            else
                if ( ! this.aPreviousRooms.isEmpty() )
                    this.goBack();
                else
                    this.aGui.println( "Back where? You just started!" );
        else if ( vCommandWord.equals( "quit" ) )
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
    } // interpretCommand()

    /**
     * Re-prints the full description of the room the player is standing in.
     */
    private void look()
    {
        this.aGui.println( this.aCurrentRoom.getLongDescription() );
    } // look()

    /**
     * Placeholder response for the {@code eat} command.
     */
    private void eat()
    {
        this.aGui.println( "You have eaten now and you are not hungry any more." );
    } // eat()

    /**
     * Ends the session by printing a farewell line and disabling further input.
     */
    private void endGame()
    {
        this.aGui.println( "Thank you for playing. Good bye." );
        this.aGui.enable( false );
    }

    /**
     * Pops the most recent room from the history stack and restores it as current,
     * then refreshes the textual and graphical view.
     */
    private void goBack()
    {
        this.aCurrentRoom = this.aPreviousRooms.pop();
        this.printLocationInfo();
        this.aGui.showImage( this.aCurrentRoom.getImageName() );
    }
} // GameEngine
