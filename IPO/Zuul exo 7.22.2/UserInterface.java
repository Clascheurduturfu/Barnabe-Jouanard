import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class implements a simple graphical user interface with a 
 * text entry area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2023)
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    private JButton    aButtonGoNorth;
    private JButton    aButtonGoSouth;
    private JButton    aButtonGoEast;
    private JButton    aButtonGoWest;
    private JButton    aButtonGoUp;
    private JButton    aButtonGoDown;
    private JButton    aButtonHelp;
    private JButton    aButtonQuit;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param gameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the entry field.
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( pOnOff ) { // enable
            this.aEntryField.getCaret().setBlinkRate( 500 ); // cursor blink
            this.aEntryField.addActionListener( this ); // reacts to entry
        }
        else { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "Pokemon Delta Emerald" );
        this.aEntryField = new JTextField( 34 );

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );

        this.aImage = new JLabel();

        this.aButtonGoNorth = new JButton( "go north" );
        this.aButtonGoEast = new JButton( "go east" );
        this.aButtonGoSouth = new JButton( "go south" );
        this.aButtonGoWest = new JButton( "go west" );
        this.aButtonGoUp = new JButton( "go up" );
        this.aButtonGoDown = new JButton( "go down" );
        this.aButtonQuit = new JButton( "quit" );
        this.aButtonHelp = new JButton( "help" );

        JPanel vButtonPanel = new JPanel();
        vButtonPanel.setLayout( new GridLayout( 4, 1 ) );
        vButtonPanel.add( this.aButtonGoNorth );
        vButtonPanel.add( this.aButtonGoSouth );
        vButtonPanel.add( this.aButtonGoEast );
        vButtonPanel.add( this.aButtonGoWest );
        vButtonPanel.add( this.aButtonGoUp );
        vButtonPanel.add( this.aButtonGoDown );
        vButtonPanel.add( this.aButtonQuit );
        vButtonPanel.add( this.aButtonHelp );

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );
        vPanel.add( vButtonPanel, BorderLayout.WEST );

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );

        // add some event listeners to some components
        this.aEntryField.addActionListener( this );
        this.aButtonGoNorth.addActionListener( this );
        this.aButtonGoSouth.addActionListener( this );
        this.aButtonGoEast.addActionListener( this );
        this.aButtonGoWest.addActionListener( this );
        this.aButtonGoUp.addActionListener( this );
        this.aButtonGoDown.addActionListener( this );
        this.aButtonQuit.addActionListener( this );
        // this.aButtonHelp.addAct65tg9 ,ionListener( this );

        // to end program when window is closed
        this.aMyFrame.addWindowListener(
            new WindowAdapter() { // anonymous class
                @Override public void windowClosing(final WindowEvent pE)
                {
                    System.exit(0);
                }
        } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     */
    @Override public void actionPerformed( final ActionEvent pE ) 
    {        
        if( this.aButtonGoNorth.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("go north");
        }
        else if( this.aButtonGoSouth.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("go south");
        }
        else if( this.aButtonGoEast.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("go east");
        }
        else if( this.aButtonGoWest.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("go west");
        }
        else if( this.aButtonGoUp.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("go up");
        }
        else if( this.aButtonGoDown.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("go down");
        }
        else if( this.aButtonQuit.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("quit");
        }
        else if( this.aButtonHelp.equals(pE.getSource()) )
        {
            this.aEngine.interpretCommand("help");
        }
        else {
            this.processCommand();
        }
    } // actionPerformed(.)

    /**
     * A command has been entered in the entry field.  
     * Read the command and do whatever is necessary to process it.
     */
    private void processCommand()
    {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText( "" );

        this.aEngine.interpretCommand( vInput );
    } // processCommand()
} // UserInterface 
