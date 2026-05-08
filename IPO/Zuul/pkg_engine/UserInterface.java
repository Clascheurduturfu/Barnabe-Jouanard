package pkg_engine;

import pkg_commands.Command;
import pkg_commands.Parser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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
 * Swing-based graphical shell for the text adventure: log area, text field, directional shortcut
 * buttons, and an optional room image.
 *
 * <p>Implements {@link ActionListener} so both the entry field and buttons forward normalized
 * commands to the {@link GameEngine}.
 *
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2023)
 */
public final class UserInterface implements ActionListener {
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

    /** Parser used to turn user input into commands. */
    private Parser aParser;

    private GameEngine aEngine;

    /** Main exploration UI panel */
    private JPanel aExplorationPanel;

    /** Battle UI panel */
    private JPanel aBattlePanel;

    /** Player Pokemon image (left side) */
    private JLabel aPlayerPokemonImage;

    /** Opponent Pokemon image (right side) */
    private JLabel aOpponentPokemonImage;

    /** Player Pokemon HP bar */
    private JProgressBar aPlayerHPBar;

    /** Opponent Pokemon HP bar */
    private JProgressBar aOpponentHPBar;

    /** Battle action buttons */
    private JButton aAttack1Button, aAttack2Button, aAttack3Button, aAttack4Button, aRunButton;

    /**
     * Builds the frame, layout, and listeners, storing the engine for later callbacks.
     *
     * @param pGameEngine the non-{@code null} game engine that will interpret commands
     */
    public UserInterface(final GameEngine pGameEngine) {
        this.aEngine = pGameEngine;
        this.aParser = new Parser();
        this.createGUI();
        this.createBattlePanel();
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
     * Loads an image from the classpath (typically under {@code images/}) and sets it on the north
     * label, packing the frame to fit.
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
     * Loads an image from the classpath (typically under {@code images/}) and sets it on the map
     * label.
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
            this.aMap.setIcon(
                    new ImageIcon(
                            vIcon.getImage()
                                    .getScaledInstance(460, 345, java.awt.Image.SCALE_SMOOTH)));
        }
    } // showMap(.)

    /**
     * Enables or disables keyboard entry in the command field and toggles caret blink.
     *
     * @param pOnOff {@code true} to allow typing; {@code false} to freeze input at game end
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

    /** Lays out components, registers listeners, and shows the window. */
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

        // Create exploration panel
        this.aExplorationPanel = new JPanel();
        this.aExplorationPanel.setLayout(new BorderLayout());
        this.aExplorationPanel.setBackground(new Color(30, 30, 40));
        this.aExplorationPanel.add(this.aImage, BorderLayout.CENTER);

        JPanel vSouthPanel = new JPanel();
        vSouthPanel.setLayout(new BorderLayout());
        vSouthPanel.setPreferredSize(new Dimension(0, 400));

        vSouthPanel.add(vListScroller, BorderLayout.CENTER);
        vSouthPanel.add(this.aEntryField, BorderLayout.SOUTH);
        vSouthPanel.add(vButtonPanel, BorderLayout.WEST);
        vSouthPanel.add(this.aMap, BorderLayout.EAST);

        this.aExplorationPanel.add(aProgressBar, BorderLayout.NORTH);
        this.aExplorationPanel.add(vSouthPanel, BorderLayout.SOUTH);

        // Create battle panel
        this.createBattlePanel();

        // Add exploration panel first (visible by default)
        this.aMyFrame.getContentPane().add(this.aExplorationPanel, BorderLayout.CENTER);

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

    /** Creates the battle UI panel with HP bars, battle image, and attack buttons. */
    private void createBattlePanel() {
        this.aBattlePanel = new JPanel();
        this.aBattlePanel.setLayout(new BorderLayout());
        this.aBattlePanel.setBackground(new Color(30, 30, 40));

        // HP bars panel (top)
        JPanel vHPPanel = new JPanel();
        vHPPanel.setLayout(new GridLayout(2, 1, 10, 10));
        vHPPanel.setBackground(new Color(30, 30, 40));

        this.aOpponentHPBar = new JProgressBar(0, 100);
        this.aOpponentHPBar.setStringPainted(true);
        this.aOpponentHPBar.setString("Opponent: 100/100");
        this.aOpponentHPBar.setForeground(new Color(244, 67, 54));
        this.aOpponentHPBar.setValue(100);
        this.aOpponentHPBar.setPreferredSize(new Dimension(300, 40));

        this.aPlayerHPBar = new JProgressBar(0, 100);
        this.aPlayerHPBar.setStringPainted(true);
        this.aPlayerHPBar.setString("Your Pokemon: 100/100");
        this.aPlayerHPBar.setForeground(new Color(76, 175, 80));
        this.aPlayerHPBar.setValue(100);
        this.aPlayerHPBar.setPreferredSize(new Dimension(300, 40));

        vHPPanel.add(this.aOpponentHPBar);
        vHPPanel.add(this.aPlayerHPBar);

        // Pokemon images panel (center)
        JPanel vPokemonPanel = new JPanel();
        vPokemonPanel.setLayout(new GridLayout(1, 2, 20, 0));
        vPokemonPanel.setBackground(new Color(30, 30, 40));

        this.aPlayerPokemonImage = new JLabel();
        this.aPlayerPokemonImage.setHorizontalAlignment(JLabel.CENTER);
        this.aPlayerPokemonImage.setVerticalAlignment(JLabel.CENTER);

        this.aOpponentPokemonImage = new JLabel();
        this.aOpponentPokemonImage.setHorizontalAlignment(JLabel.CENTER);
        this.aOpponentPokemonImage.setVerticalAlignment(JLabel.CENTER);

        vPokemonPanel.add(this.aPlayerPokemonImage);
        vPokemonPanel.add(this.aOpponentPokemonImage);

        // Attack buttons panel (bottom)
        JPanel vAttackPanel = new JPanel();
        vAttackPanel.setLayout(new GridLayout(2, 3, 5, 5));
        vAttackPanel.setPreferredSize(new Dimension(0, 120));

        this.aAttack1Button = new JButton("Attack 1");
        this.aAttack2Button = new JButton("Attack 2");
        this.aAttack3Button = new JButton("Attack 3");
        this.aAttack4Button = new JButton("Attack 4");
        this.aRunButton = new JButton("Run Away");

        vAttackPanel.add(this.aAttack1Button);
        vAttackPanel.add(this.aAttack2Button);
        vAttackPanel.add(this.aAttack3Button);
        vAttackPanel.add(this.aAttack4Button);
        vAttackPanel.add(new JLabel()); // Empty space
        vAttackPanel.add(this.aRunButton);

        this.aBattlePanel.add(vHPPanel, BorderLayout.NORTH);
        this.aBattlePanel.add(vPokemonPanel, BorderLayout.CENTER);
        this.aBattlePanel.add(vAttackPanel, BorderLayout.SOUTH);

        this.aBattlePanel.setVisible(false);
    } // createBattlePanel()

    /** Switches from exploration mode to battle mode. */
    public void startBattle() {
        this.aMyFrame.getContentPane().remove(this.aExplorationPanel);
        this.aMyFrame.getContentPane().add(this.aBattlePanel, BorderLayout.CENTER);
        this.aBattlePanel.setVisible(true);
        this.aMyFrame.revalidate();
        this.aMyFrame.repaint();
    } // startBattle()

    /** Switches from battle mode back to exploration mode. */
    public void endBattle() {
        this.aMyFrame.getContentPane().remove(this.aBattlePanel);
        this.aMyFrame.getContentPane().add(this.aExplorationPanel, BorderLayout.CENTER);
        this.aExplorationPanel.setVisible(true);
        this.aMyFrame.revalidate();
        this.aMyFrame.repaint();
    } // endBattle()

    /**
     * Shows player's Pokemon image (left side). PNG files are scaled to 640x400, GIF files are
     * displayed raw.
     *
     * @param pImageName resource path for player pokemon
     */
    public void showPlayerPokemonImage(final String pImageName) {
        String vImagePath = "Images/" + pImageName;
        URL vImageURL = this.getClass().getClassLoader().getResource(vImagePath);
        if (vImageURL == null) {
            System.out.println("Player pokemon image not found: " + vImagePath);
        } else {
            if (pImageName.endsWith(".png")) {
                ImageIcon vIcon = new ImageIcon(vImageURL);
                this.aPlayerPokemonImage.setIcon(
                        new ImageIcon(
                                vIcon.getImage()
                                        .getScaledInstance(960, 904, java.awt.Image.SCALE_SMOOTH)));
            } else {
                ImageIcon vIcon = new ImageIcon(vImageURL);
                vIcon.getImage().flush();
                this.aPlayerPokemonImage.setIcon(vIcon);
            }
        }
    } // showPlayerPokemonImage()

    /**
     * Shows opponent's Pokemon image (right side). PNG files are scaled to 960x904, GIF files are
     * displayed raw with animation.
     *
     * @param pImageName resource path for opponent pokemon
     */
    public void showOpponentPokemonImage(final String pImageName) {
        String vImagePath = "Images/" + pImageName;
        URL vImageURL = this.getClass().getClassLoader().getResource(vImagePath);
        if (vImageURL == null) {
            System.out.println("Opponent pokemon image not found: " + vImagePath);
        } else {
            if (pImageName.endsWith(".png")) {
                ImageIcon vIcon = new ImageIcon(vImageURL);
                this.aOpponentPokemonImage.setIcon(
                        new ImageIcon(
                                vIcon.getImage()
                                        .getScaledInstance(960, 904, java.awt.Image.SCALE_SMOOTH)));
            } else {
                ImageIcon vIcon = new ImageIcon(vImageURL);
                vIcon.getImage().flush();
                this.aOpponentPokemonImage.setIcon(vIcon);
            }
        }
    } // showOpponentPokemonImage()

    /**
     * Updates player HP bar.
     *
     * @param pCurrent current HP
     * @param pMax max HP
     */
    public void setPlayerHP(final int pCurrent, final int pMax) {
        this.aPlayerHPBar.setMaximum(pMax);
        this.aPlayerHPBar.setValue(pCurrent);
        this.aPlayerHPBar.setString("Your Pokemon: " + pCurrent + "/" + pMax);
    } // setPlayerHP()

    /**
     * Updates opponent HP bar.
     *
     * @param pCurrent current HP
     * @param pMax max HP
     */
    public void setOpponentHP(final int pCurrent, final int pMax) {
        this.aOpponentHPBar.setMaximum(pMax);
        this.aOpponentHPBar.setValue(pCurrent);
        this.aOpponentHPBar.setString("Opponent: " + pCurrent + "/" + pMax);
    } // setOpponentHP()

    /**
     * Gets attack button 1 for adding listeners.
     *
     * @return the first attack button
     */
    public JButton getAttack1Button() {
        return this.aAttack1Button;
    }

    /**
     * Gets attack button 2 for adding listeners.
     *
     * @return the second attack button
     */
    public JButton getAttack2Button() {
        return this.aAttack2Button;
    }

    /**
     * Gets attack button 3 for adding listeners.
     *
     * @return the third attack button
     */
    public JButton getAttack3Button() {
        return this.aAttack3Button;
    }

    /**
     * Gets attack button 4 for adding listeners.
     *
     * @return the fourth attack button
     */
    public JButton getAttack4Button() {
        return this.aAttack4Button;
    }

    /**
     * Gets run button for adding listeners.
     *
     * @return the run button
     */
    public JButton getRunButton() {
        return this.aRunButton;
    }

    /**
     * Dispatches button clicks as canned commands, or reads the text field on Enter.
     *
     * @param pE the originating AWT event (button or text field)
     */
    @Override
    public void actionPerformed(final ActionEvent pE) {
        if (this.aButtonGoNorth.equals(pE.getSource())) {
            this.println("\n\n> go north\n");
            this.aParser.getCommand("go north").execute(this.aEngine);
        } else if (this.aButtonGoSouth.equals(pE.getSource())) {
            this.println("\n\n> go south\n");
            this.aParser.getCommand("go south").execute(this.aEngine);
        } else if (this.aButtonGoEast.equals(pE.getSource())) {
            this.println("\n\n> go east\n");
            this.aParser.getCommand("go east").execute(this.aEngine);
        } else if (this.aButtonGoWest.equals(pE.getSource())) {
            this.println("\n\n> go west\n");
            this.aParser.getCommand("go west").execute(this.aEngine);
        } else if (this.aButtonGoUp.equals(pE.getSource())) {
            this.println("\n\n> go up\n");
            this.aParser.getCommand("go up").execute(this.aEngine);
        } else if (this.aButtonGoDown.equals(pE.getSource())) {
            this.println("\n\n> go down\n");
            this.aParser.getCommand("go down").execute(this.aEngine);
        } else if (this.aButtonBack.equals(pE.getSource())) {
            this.println("\n\n> back\n");
            this.aParser.getCommand("back").execute(this.aEngine);
        } else if (this.aButtonQuit.equals(pE.getSource())) {
            this.println("\n\n> quit\n");
            this.aParser.getCommand("quit").execute(this.aEngine);
        } else if (this.aButtonHelp.equals(pE.getSource())) {
            this.println("\n\n> help\n");
            this.aParser.getCommand("help").execute(this.aEngine);
        } else {
            final String vInput = this.aEntryField.getText();
            this.println("\n\n> " + vInput + "\n");
            final Command vCommand = this.aParser.getCommand(vInput);
            if (vCommand == null) {
                this.println("I don't know what you mean...");
            } else {
                vCommand.execute(this.aEngine);
            }
            this.aEntryField.setText("");
        }
    } // actionPerformed(.)

    /**
     * Updates the progress bar shown in the UI.
     *
     * @param pPercent progress percentage (typically 0-100)
     */
    public void setProgress(final int pPercent) {
        this.aProgressBar.setValue(pPercent);
    } // setProgress()

    /**
     * Updates the progress bar label.
     *
     * @param pText the text to display inside the progress bar
     */
    public void setProgressString(final String pText) {
        this.aProgressBar.setString(pText);
    } // setProgress()

    /**
     * Returns the current value of the progress bar.
     *
     * @return progress value
     */
    public int getProgressBar() {
        return this.aProgressBar.getValue();
    } // getProgressBar()

    /**
     * Returns the parser used by this interface.
     *
     * @return the parser instance
     */
    public Parser getParser() {
        return this.aParser;
    } // getParser()
} // UserInterface
