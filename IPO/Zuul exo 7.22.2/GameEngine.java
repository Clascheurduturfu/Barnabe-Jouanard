/**
 * Class GameEngine - the main engine of the Zuul adventure game.
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class GameEngine
{
    private Room aCurrentRoom;
    private Parser aParser;
    private UserInterface aGui;
   
    /**
     * Default constructor: initializes the map and the parser.
     */
    public GameEngine()
    {
        this.createRooms();
        this.aParser = new Parser();
    } // GameEngine()
    
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }
    
    /**
     * Creates all the rooms and links their exits together.
     */
    private void createRooms()
    {
        Room house, littleroot, route101, oldale, route102, petalburg, petalburgWoods, rustboro, skyPillar;
        
        house      = new Room("in your house in Littleroot Town","images/home.jpg");
        littleroot = new Room("in your Littleroot, your Home Town","images/littleroot town.jpg");
        route101   = new Room("on Route 101, wild Pokemon might appear in the tall grass","images/route 101.jpg");
        oldale     = new Room("in Oldale Town, a small junction with a Pokemon Center","images/oldaletown.jpg");
        route102   = new Room("on Route 102, wild Pokemon might appear in the tall grass","images/route 102.jpg");
        petalburg  = new Room("in Petalburg City, where your father is the Gym Leader","images/petalburg city.jpg");
        petalburgWoods  = new Room("in Petalburg Woods","images/petalburg woods.jpg");
        rustboro   = new Room("in Rustboro City, where the Devon Corporation is located","images/rustboro city.jpg");
        skyPillar  = new Room("on the Sky Pilar, Rayquaza's home..","images/sky pillar.jpg");
        
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
        
        petalburgWoods.setExit("south", route102);
        petalburgWoods.setExit("west", rustboro);
        
        rustboro.setExit("up", skyPillar);
        rustboro.setExit("east", petalburgWoods);
        rustboro.setExit("south", oldale);
        
        skyPillar.setExit("down", rustboro);
        
        house.addItem("Map",new Item("The Map",0));
        house.addItem("Shoes",new Item("Your Shoes",0));
        skyPillar.addItem("Delta Orb",new Item("The Delta Orb, the orb that will allow you to summon Rayquazza..",100));
        
        this.aCurrentRoom = house;    
    } // createRooms()
    
    /**
     * Prints out the opening message for the player.
     */
    private void printWelcome()
    {
        this.aGui.print("\n");
        this.aGui.println("Welcome to the World of Pokemon!");
        this.aGui.println("A Wonderful World Where You Can Live An Adventure!");
        this.aGui.println("Type 'help' if you need help.");
        this.aGui.print("\n");
        this.printLocationInfo();
        if ( this.aCurrentRoom.getImageName() != null )
            this.aGui.showImage( this.aCurrentRoom.getImageName() );
    } // printWelcome()
    
    /**
     * Tries to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param pCommand The command containing the direction.
     */
    private void goRoom (final Command pCommand)
    {
        if ( ! pCommand.hasSecondWord() ) {
            // if there is no second word, we don't know where to go...
            this.aGui.println( "Go where?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();

        // Try to leave current room.
        Room vNextRoom = this.aCurrentRoom.getExit( vDirection );

        if ( vNextRoom == null )
            this.aGui.println( "There is no door!" );
        else {
            this.aCurrentRoom = vNextRoom;
            this.aGui.println( this.aCurrentRoom.getLongDescription() );
            if ( this.aCurrentRoom.getImageName() != null )
                this.aGui.showImage( this.aCurrentRoom.getImageName() );
        }
    } // goRoom()
   
    /**
     * Prints out some help information.
     */
    private void printHelp()
    {
        this.aGui.println("You are in the wonderful word of Pokemon.");
        this.aGui.println("You are trying to stop Rayquaza from destroying Hoenn.");
        this.aGui.println("Your command words are:");
        this.aGui.println(this.aParser.getCommandString());
    } // printHelp()
    
    /**
     * Prints information about the current location (description and exits).
     */
    private void printLocationInfo()
    {
        this.aGui.print(this.aCurrentRoom.getLongDescription());
    } // printLocationInfo()
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param pCommand The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public void interpretCommand (final String pCommand)
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
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
    } // interpretCommand()

    /**
     * Prints the long description of the current room.
     */
    private void look()
    {
       this.aGui.println(this.aCurrentRoom.getLongDescription());
    } //look()
   
    /**
     * Prints a message indicating the player has eaten.
     */
    private void eat()
    {
        this.aGui.println("You have eaten now and you are not hungry any more.");
    } //eat()
    
    private void endGame()
    {
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false );
    }
    
} // GameEngine
