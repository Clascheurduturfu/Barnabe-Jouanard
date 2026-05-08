import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Swing-based graphical shell for the text adventure: log area, text field,
 * directional shortcut buttons, and an optional room image.
 * <p>
 * Implements {@link ActionListener} so both the entry field and buttons forward
 * normalized commands to the {@link GameEngine}.
 * </p>
 *
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2023)
 */
public class UserInterface implements ActionListener {
    /** Game logic invoked when the user submits a command. */
    private GameEngine aEngine;
    /** Top-level window hosting all components. */
    private JFrame aMyFrame;
    /** Single-line input where the player types commands. */
    private JTextField aEntryField;
    /** Scrollable transcript of game output. */
    private JTextArea aLog;
    /** Displays the current room image when available. */
    private JLabel aImage;
    /** Displays a map image (if provided by the engine). */
    private JLabel aMap;
    /** Sends {@code go north} when clicked. */
    private JButton aButtonGoNorth;
    /** Sends {@code go south} when clicked. */
    private JButton aButtonGoSouth;
    /** Sends {@code go east} when clicked. */
    private JButton aButtonGoEast;
    /** Sends {@code go west} when clicked. */
    private JButton aButtonGoWest;
    /** Sends {@code go up} when clicked. */
    private JButton aButtonGoUp;
    /** Sends {@code go down} when clicked. */
    private JButton aButtonGoDown;
    /** Sends {@code help} when clicked. */
    private JButton aButtonHelp;
    /** Sends {@code back} when clicked. */
    private JButton aButtonBack;
    /** Sends {@code quit} when clicked. */
    private JButton aButtonQuit;
    /** Progress bar shown during asset download at startup. */
    private JProgressBar aProgressBar;

    /**
     * Builds the frame, layout, and listeners, storing the engine for later
     * callbacks.
     *
     * @param pGameEngine the non-{@code null} game engine that will interpret
     *                    commands
     */
    public UserInterface(final GameEngine pGameEngine) {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Appends text to the log without a trailing newline and scrolls to the end.
     *
     * @param pText fragment to append (may be empty)
     */
    public void print(final String pText) {
        this.aLog.append(pText);
        this.aLog.setCaretPosition(this.aLog.getDocument().getLength());
    } // print(.)

    /**
     * Appends a line of text to the log, then scrolls to the end.
     *
     * @param pText full line content (a newline is added automatically)
     */
    public void println(final String pText) {
        this.print(pText + "\n");
    } // println(.)

    /**
     * Loads an image from the classpath (typically under {@code images/}) and sets
     * it on the north label, packing the frame to fit.
     *
     * @param pImageName resource path relative to the class loader root
     */
    public void showImage(final String pImageName) {
        String vImagePath = "Images/" + pImageName;
        URL vImageURL = this.getClass().getClassLoader().getResource(vImagePath);
        if (vImageURL == null) {
            System.out.println("Image not found: " + vImagePath);
        } else {
            ImageIcon vIcon = new ImageIcon(vImageURL);
            this.aImage.setIcon(vIcon);
        }
    } // showImage(.)

    /**
     * Loads an image from the classpath (typically under {@code images/}) and sets
     * it on the map label.
     *
     * @param pImageName resource path relative to the class loader root
     */
    public void showMap(final String pImageName) {
        String vImagePath = "Images/" + pImageName;
        URL vImageURL = this.getClass().getClassLoader().getResource(vImagePath);
        if (vImageURL == null) {
            System.out.println("Image not found: " + vImagePath);
        } else {
            ImageIcon vIcon = new ImageIcon(vImageURL);
            this.aMap.setIcon(new ImageIcon(vIcon.getImage().getScaledInstance(460, 345, java.awt.Image.SCALE_SMOOTH)));
        }
    } // showMap(.)

    /**
     * Enables or disables keyboard entry in the command field and toggles caret
     * blink.
     *
     * @param pOnOff {@code true} to allow typing; {@code false} to freeze input at
     *               game end
     */
    public void enable(final boolean pOnOff) {
        this.aEntryField.setEditable(pOnOff);
        if (pOnOff) {
            this.aEntryField.getCaret().setBlinkRate(500);
            this.aEntryField.addActionListener(this);
        } else {
            this.aEntryField.getCaret().setBlinkRate(0);
            this.aEntryField.removeActionListener(this);
        }
    } // enable(.)

    /**
     * Lays out components, registers listeners, and shows the window.
     */
    private void createGUI() {
        this.aMyFrame = new JFrame("Pokémon Delta Emerald");
        this.aEntryField = new JTextField(34);

        this.aLog = new JTextArea();
        this.aLog.setEditable(false);
        JScrollPane vListScroller = new JScrollPane(this.aLog);
        vListScroller.setPreferredSize(new Dimension(200, 200));
        vListScroller.setMinimumSize(new Dimension(100, 100));

        this.aImage = new JLabel();
        this.aImage.setHorizontalAlignment(JLabel.CENTER);
        this.aImage.setVerticalAlignment(JLabel.CENTER);

        this.aProgressBar = new JProgressBar(0, 100);
        this.aProgressBar.setStringPainted(true);
        this.aProgressBar.setPreferredSize(new Dimension(200, 50));
        this.aProgressBar.setForeground(new Color(76, 175, 80));
        this.aProgressBar.setBackground(new Color(50, 50, 60));
        this.aProgressBar.setValue(100);

        this.aMap = new JLabel();

        this.aButtonGoUp = new JButton("go up");
        this.aButtonGoNorth = new JButton("go north");
        this.aButtonGoDown = new JButton("go down");
        this.aButtonGoWest = new JButton("go west");
        this.aButtonGoEast = new JButton("go east");
        this.aButtonBack = new JButton("go back");
        this.aButtonHelp = new JButton("help");
        this.aButtonGoSouth = new JButton("go south");
        this.aButtonQuit = new JButton("quit");

        JPanel vButtonPanel = new JPanel();
        vButtonPanel.setLayout(new GridLayout(3, 3));
        vButtonPanel.add(this.aButtonGoUp);
        vButtonPanel.add(this.aButtonGoNorth);
        vButtonPanel.add(this.aButtonGoDown);
        vButtonPanel.add(this.aButtonGoWest);
        vButtonPanel.add(this.aButtonBack);
        vButtonPanel.add(this.aButtonGoEast);
        vButtonPanel.add(this.aButtonHelp);
        vButtonPanel.add(this.aButtonGoSouth);
        vButtonPanel.add(this.aButtonQuit);

        JPanel vPanel = new JPanel();
        vPanel.setLayout(new BorderLayout());
        vPanel.setBackground(new Color(30, 30, 40));
        vPanel.add(this.aImage, BorderLayout.CENTER);

        JPanel vSouthPanel = new JPanel();
        vSouthPanel.setLayout(new BorderLayout());
        vSouthPanel.setPreferredSize(new Dimension(0, 400));

        vSouthPanel.add(vListScroller, BorderLayout.CENTER);
        vSouthPanel.add(this.aEntryField, BorderLayout.SOUTH);
        vSouthPanel.add(vButtonPanel, BorderLayout.WEST);
        vSouthPanel.add(this.aMap, BorderLayout.EAST);

        vPanel.add(aProgressBar, BorderLayout.NORTH);
        vPanel.add(vSouthPanel, BorderLayout.SOUTH);

        this.aMyFrame.getContentPane().add(vPanel, BorderLayout.CENTER);

        this.aEntryField.addActionListener(this);
        this.aButtonGoNorth.addActionListener(this);
        this.aButtonGoSouth.addActionListener(this);
        this.aButtonGoEast.addActionListener(this);
        this.aButtonGoWest.addActionListener(this);
        this.aButtonGoUp.addActionListener(this);
        this.aButtonGoDown.addActionListener(this);
        this.aButtonBack.addActionListener(this);
        this.aButtonQuit.addActionListener(this);
        this.aButtonHelp.addActionListener(this);

        this.aMyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.aMyFrame.setVisible(true);
        this.aEntryField.requestFocus();
        this.aMyFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    } // createGUI()

    /**
     * Dispatches button clicks as canned commands, or reads the text field on
     * Enter.
     *
     * @param pE the originating AWT event (button or text field)
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        if (this.aButtonGoNorth.equals(pE.getSource())) {
            this.aEngine.interpretCommand("go north");
        } else if (this.aButtonGoSouth.equals(pE.getSource())) {
            this.aEngine.interpretCommand("go south");
        } else if (this.aButtonGoEast.equals(pE.getSource())) {
            this.aEngine.interpretCommand("go east");
        } else if (this.aButtonGoWest.equals(pE.getSource())) {
            this.aEngine.interpretCommand("go west");
        } else if (this.aButtonGoUp.equals(pE.getSource())) {
            this.aEngine.interpretCommand("go up");
        } else if (this.aButtonGoDown.equals(pE.getSource())) {
            this.aEngine.interpretCommand("go down");
        } else if (this.aButtonBack.equals(pE.getSource())) {
            this.aEngine.interpretCommand("back");
        } else if (this.aButtonQuit.equals(pE.getSource())) {
            this.aEngine.interpretCommand("quit");
        } else if (this.aButtonHelp.equals(pE.getSource())) {
            this.aEngine.interpretCommand("help");
        } else {
            this.processCommand();
        }
    } // actionPerformed(.)

    /**
     * Reads the entry field, clears it, and forwards the trimmed line to the
     * engine.
     */
    private void processCommand() {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText("");

        this.aEngine.interpretCommand(vInput);
    } // processCommand()

    /**
     * Updates the progress bar shown in the UI.
     *
     * @param pPercent progress percentage (typically 0-100)
     */
    public void setProgress(final int pPercent) {
        this.aProgressBar.setValue(pPercent);
    } // setProgress()

    /**
     * Returns the current value of the progress bar.
     *
     * @return progress value
     */
    public int getProgressBar() {
        return this.aProgressBar.getValue();
    } // getProgressBar()
} // UserInterface
