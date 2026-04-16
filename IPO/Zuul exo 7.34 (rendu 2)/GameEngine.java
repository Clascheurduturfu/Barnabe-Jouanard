import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

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
    /** Converts raw input lines into {@link Command} objects. */
    private Parser aParser;
    /** User-facing output and optional room imagery. */
    private UserInterface aGui;
    /** Player state: current room, history, and inventory. */
    private Player aPlayer;

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

        house      = new Room( "in your house in Littleroot Town", "images/home.gif", "images/home_map.jpeg" );
        littleroot = new Room( "in Littleroot Town, your home town", "images/littleroot town.gif", "images/littleroot_map.jpeg" );
        route101   = new Room( "on Route 101; wild Pokémon might appear in the tall grass", "images/route 101.gif", "images/route101_map.jpeg" );
        oldale     = new Room( "in Oldale Town, a small junction with a Pokémon Center", "images/oldale town.gif", "images/oldale town_map.jpeg" );
        route102   = new Room( "on Route 102; wild Pokémon might appear in the tall grass", "images/route 102.gif", "images/route102_map.jpeg" );
        petalburg  = new Room( "in Petalburg City, where your father is the Gym Leader", "images/petalburg city.gif", "images/petalburg city_map.jpeg" );
        petalburgWoods  = new Room( "in Petalburg Woods", "images/petalburg woods.gif", "images/petalburg woods_map.jpeg" );
        rustboro   = new Room( "in Rustboro City, where the Devon Corporation is located", "images/rustboro city.gif", "images/rustboro city_map.jpeg" );
        skyPillar  = new Room( "on the Sky Pillar, Rayquaza's home", "images/sky pillar.gif", "images/sky pilar_map.jpeg" );

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
        
        skyPillar.setAsWinningRoom();

        house.addItem( "map", new Item( "map: a map that helps you navigate.", 0 ) );
        house.addItem( "shoes", new Item( "shoes: your shoes.", 100 ) );
        littleroot.addItem( "grant", new Item( "grant: a student grant you obtain thanks to your grades at school, to help you on your journey.", 0 ) );
        rustboro.addItem( "wallet", new Item( "wallet: someone's wallet; they must have left it here. It probably contains some money.", 0 ) );
        petalburg.addItem( "delta-orb", new Item( "delta-orb: the Delta Orb, which lets you summon Rayquaza.", 150 ) );

        this.aPlayer = new Player( "Luke", house );
    } // createRooms()  

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

        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );

        if ( vNextRoom == null ) {
            this.aGui.println( "There is no door!" );
        }
        else {
            this.aPlayer.moveTo( vNextRoom );
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if ( this.aPlayer.getCurrentRoom().getImageName() != null ) {
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
                if ( this.aPlayer.getItem( "map" ) != null ) {
                    this.aGui.showMap( this.aPlayer.getCurrentRoom().getMapImageName() );
                }
            }

            if ( this.aPlayer.getItem( "delta-orb" ) != null ) {
                if ( this.aPlayer.getCurrentRoom().isWinningRoom() ) {
                    this.aGui.println( "\n" + "Congratulations! You just won! Thank you for playing the whole game!" );
                }
            }
        }
    } // goRoom()
    
    /**
     * Moves the player back to the previous room, when possible.
     * <p>
     * This command does not accept a second word. If the player has no navigation
     * history yet, an explanatory message is printed.
     * </p>
     *
     * @param pCommand the {@code back} command
     */
    private void goBack( final Command pCommand )
    {
        if ( pCommand.hasSecondWord() ) {
            // If there is a second word, we don't know where to go.
            this.aGui.println( "Back where?" );
            return;
        }

        if ( ! this.aPlayer.canGoBack() ) {
            this.aGui.println( "Can't go back, you just started!" );
        }
        else {
            this.aPlayer.goBack();
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if ( this.aPlayer.getCurrentRoom().getImageName() != null ) {
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
                if ( this.aPlayer.getItem( "map" ) != null ) {
                    this.aGui.showMap( this.aPlayer.getCurrentRoom().getMapImageName() );
                }
            }
            
            if ( this.aPlayer.getItem( "delta-orb" ) != null ) {
                if ( this.aPlayer.getCurrentRoom().isWinningRoom() ) {
                    this.aGui.println( "\n" + "You want to keep winning, don't you?" );
                }
            }
        }
    } // goBack()

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
        this.aGui.println( "\n\n> " + pCommand + "\n" );
        Command vCommand = this.aParser.getCommand( pCommand );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "I don't know what you mean..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) ) {
            this.printHelp();
        }
        else if ( vCommandWord.equals( "go" ) ) {
            this.goRoom( vCommand );
        }
        else if ( vCommandWord.equals( "cashin" ) ) {
            if ( vCommand.hasSecondWord() ) {
                this.cashin( vCommand );
            }
            else {
                this.aGui.println( "You need something to cash in!" );
            }
        }
        else if ( vCommandWord.equals( "look" ) ) {
            this.look();
        }
        else if ( vCommandWord.equals( "take" ) ) {
            this.take( vCommand );
        }
        else if ( vCommandWord.equals( "drop" ) ) {
            this.drop( vCommand );
        }
        else if ( vCommandWord.equals( "back" ) ) {
            if ( vCommand.hasSecondWord() ) {
                this.aGui.println( "Back what?" );
            }
            else {
                this.goBack( vCommand );
            }
        }
        else if ( vCommandWord.equals( "inventory" ) ) {
            if ( vCommand.hasSecondWord() ) {
                this.aGui.println( "Inventory what?" );
            }
            else {
                this.inventory();
            }
        }
        else if ( vCommandWord.equals( "test" ) ) {
            if ( ! vCommand.hasSecondWord() ) {
                this.aGui.println( "Need a file name!" );
            }
            else {
                this.test( vCommand );
            }
        }
        else if ( vCommandWord.equals( "name" ) ) {
            if ( ! vCommand.hasSecondWord() ) {
                this.aGui.println( "You need a name!" );
            }
            else {
                this.name( vCommand );
            }
        }
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() ) {
                this.aGui.println( "Quit what?" );
            }
            else {
                this.endGame();
            }
        }
    } // interpretCommand()

    /**
     * Re-prints the full description of the room the player is standing in.
     */
    private void look()
    {
        this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
    } // look()

    /**
     * Placeholder response for the {@code cashin} command.
     *
     * @param pCommand the {@code cashin} command whose second word is the item key
     */
    private void cashin( final Command pCommand )
    {
        String vItemName = pCommand.getSecondWord();
        HashMap<String, Integer> vCashableItem = new HashMap<String, Integer>();
        vCashableItem.put( "grant", 75 );
        vCashableItem.put( "wallet", 25 );
        
        if ( vCashableItem.containsKey( vItemName ) ) {
            Item vItem = this.aPlayer.getItem( vItemName );
            if ( vItem != null ) {
                int vReward = vCashableItem.get( vItemName );
                this.aPlayer.setMoney( this.aPlayer.getMoney() + vReward );
                this.aPlayer.removeItem( vItemName );
                this.aGui.println( "You have cashed in your " + vItemName + " and are now richer!" );
                this.aGui.println( "Your current money is: " + this.aPlayer.getMoney() );
            }
            else {
                this.aGui.println( "You don't have a " + vItemName + " to cash in." );
            }
        }
        else {
            this.aGui.println( "You can't cash that in." );
        }      
    } // cashin()
    
    /**
     * Changes the player's displayed name.
     *
     * @param pCommand the {@code name} command whose second word is the new name
     */
    private void name( final Command pCommand )
    {
        String vName = pCommand.getSecondWord();
        this.aPlayer.setName( vName );
        this.aGui.println( "Your new name is " + this.aPlayer.getName() + '!' );
        this.printWelcome();
    } // name()

    /**
     * Transfers an item from the current room into the player's inventory.
     *
     * @param pCommand the {@code take} command whose second word is the item key
     */
    private void take( final Command pCommand )
    {
        if ( ! pCommand.hasSecondWord() ) {
            this.aGui.println( "Take what?" );
            return;
        }
        String vItemName = pCommand.getSecondWord();
        Item vItem = this.aPlayer.getCurrentRoom().getItem( vItemName );

        if ( vItem == null ) {
            this.aGui.println( "I can't find any " + vItemName + "!" );
            return;
        }
        if ( this.aPlayer.getMoney() >= vItem.getItemPrice() )
        {
            this.aPlayer.setMoney( this.aPlayer.getMoney() - vItem.getItemPrice() );
            this.aPlayer.getCurrentRoom().removeItem( vItemName );
            this.aPlayer.addItem( vItemName, vItem );
            this.aGui.println( "Took " + vItemName + "!" );
            if ( this.aPlayer.getItem( "map" ) != null ) {
                this.aGui.showMap( this.aPlayer.getCurrentRoom().getMapImageName() );
            }
        }
        else
        {
            this.aGui.println( vItemName + " is too expensive for you right now!" );
        }
    } // take()

    /**
     * Transfers an item from the player's inventory back into the current room.
     *
     * @param pCommand the {@code drop} command whose second word is the item key
     */
    private void drop( final Command pCommand )
    {
        if ( ! pCommand.hasSecondWord() ) {
            this.aGui.println( "Drop what?" );
            return;
        }
        String vItemName = pCommand.getSecondWord();
        Item vItem = this.aPlayer.getItem( vItemName );
        if ( vItem == null ) {
            this.aGui.println( "I can't find any " + vItemName + "!" );
            return;
        }
        this.aPlayer.getCurrentRoom().addItem( vItemName, vItem );
        this.aPlayer.removeItem( vItemName );
        this.aGui.println( "Dropped " + vItemName + "!" );
        this.aPlayer.setMoney( this.aPlayer.getMoney() + vItem.getItemPrice() );
        if ( this.aPlayer.getItem( "map" ) == null ) {
            this.aGui.showMap( "images/no map.jpeg" );
        }
    } // drop()

    /**
     * Ends the session by printing a farewell line and disabling further input.
     */
    private void endGame()
    {
        this.aGui.println( "Thank you for playing! Goodbye." );
        this.aGui.enable( false );
        System.exit( 0 );
    }

    /**
     * Runs a sequence of commands from a text file packaged as a classpath resource.
     * <p>
     * The engine will interpret each line as if the player had typed it.
     * </p>
     *
     * @param pCommand the {@code test} command whose second word is the base filename
     */
    private void test( final Command pCommand )
    {
        String vFileName = pCommand.getSecondWord();
        vFileName = vFileName + ".txt";

        InputStream vIS = this.getClass().getClassLoader().getResourceAsStream( vFileName );

        if ( vIS == null ) {
            System.out.println( "File not found: " + vFileName );
            return;
        }

        Scanner vSC = new Scanner( vIS );

        while ( vSC.hasNextLine() ) {
            String vLigne = vSC.nextLine();
            this.interpretCommand( vLigne );
        }
        
        vSC.close();
    } // test()

    /**
     * Prints the welcome banner, initial location text, and the starting room image
     * when one is configured.
     */
    private void printWelcome()
    {
        this.aGui.print( "\n" );
        this.aGui.println( "Hello " + this.aPlayer.getName() + "!" );
        this.aGui.println( "You can change your name with the command: name <new name>." );
        this.aGui.println( "Welcome to the world of Pokémon!" );
        this.aGui.println( "A wonderful world where you can live an adventure!" );
        this.aGui.println( "Type 'help' if you need help." );
        this.printLocationInfo();
        if ( this.aPlayer.getCurrentRoom().getImageName() != null ) {
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
        if ( this.aPlayer.getItem( "map" ) == null ) {
            this.aGui.showMap( "images/no map.jpeg" );
        }
    } // printWelcome()

    /**
     * Prints short narrative context and the list of valid command words.
     */
    private void printHelp()
    {
        this.aGui.println( "Hello " + this.aPlayer.getName() + "!" );
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
        this.aGui.print( this.aPlayer.getCurrentRoom().getLongDescription() );
    } // printLocationInfo()

    /**
     * Prints the player's inventory summary (items and total value).
     */
    private void inventory()
    {
        this.aGui.println( this.aPlayer.getItemInventory() );
    } // inventory()
} // GameEngine