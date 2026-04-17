import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * A splash screen displayed while the {@link AssetManager} downloads missing
 * game assets from the remote server.
 * <p>
 * Shows a progress bar with a status label indicating which file is being
 * downloaded and how many remain. When all downloads complete (or fail after
 * retries), the screen closes automatically and the game starts.
 * </p>
 *
 * @author Gemini
 * @version 2026.04.17
 */
public class LoadingScreen implements AssetManager.ProgressListener
{
    /** The loading window frame. */
    private JFrame aFrame;
    /** Progress bar tracking download completion. */
    private JProgressBar aProgressBar;
    /** Label showing the name of the file currently being downloaded. */
    private JLabel aStatusLabel;
    /** Label showing the overall download count (e.g. "3 / 19"). */
    private JLabel aCountLabel;
    /** Reference to the asset manager performing the downloads. */
    private AssetManager aAssetManager;
    /** Flag indicating whether the download phase is finished. */
    private boolean aFinished;

    /**
     * Creates a new loading screen linked to the given asset manager.
     *
     * @param pAssetManager the asset manager that will perform the downloads
     */
    public LoadingScreen( final AssetManager pAssetManager )
    {
        this.aAssetManager = pAssetManager;
        this.aAssetManager.setProgressListener( this );
        this.aFinished = false;
        this.createGUI();
    } // LoadingScreen()

    /**
     * Builds the loading screen UI: title, progress bar, and status labels.
     */
    private void createGUI()
    {
        this.aFrame = new JFrame( "Pokémon Delta Emerald — Loading" );
        this.aFrame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        this.aFrame.setResizable( false );

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout( 10, 10 ) );
        vPanel.setBorder( BorderFactory.createEmptyBorder( 30, 40, 30, 40 ) );
        vPanel.setBackground( new Color( 30, 30, 40 ) );

        // Title
        JLabel vTitle = new JLabel( "Pokémon Delta Emerald", SwingConstants.CENTER );
        vTitle.setFont( new Font( "SansSerif", Font.BOLD, 22 ) );
        vTitle.setForeground( new Color( 255, 215, 0 ) );

        // Progress bar
        this.aProgressBar = new JProgressBar( 0, 100 );
        this.aProgressBar.setStringPainted( true );
        this.aProgressBar.setPreferredSize( new Dimension( 400, 30 ) );
        this.aProgressBar.setForeground( new Color( 76, 175, 80 ) );
        this.aProgressBar.setBackground( new Color( 50, 50, 60 ) );

        // Status label (filename)
        this.aStatusLabel = new JLabel( "Checking assets...", SwingConstants.CENTER );
        this.aStatusLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ) );
        this.aStatusLabel.setForeground( new Color( 200, 200, 210 ) );

        // Count label
        this.aCountLabel = new JLabel( " ", SwingConstants.CENTER );
        this.aCountLabel.setFont( new Font( "SansSerif", Font.PLAIN, 13 ) );
        this.aCountLabel.setForeground( new Color( 160, 160, 170 ) );

        // Layout: south panel for labels
        JPanel vSouthPanel = new JPanel();
        vSouthPanel.setLayout( new BorderLayout( 5, 5 ) );
        vSouthPanel.setOpaque( false );
        vSouthPanel.add( this.aStatusLabel, BorderLayout.NORTH );
        vSouthPanel.add( this.aCountLabel, BorderLayout.SOUTH );

        vPanel.add( vTitle, BorderLayout.NORTH );
        vPanel.add( this.aProgressBar, BorderLayout.CENTER );
        vPanel.add( vSouthPanel, BorderLayout.SOUTH );

        this.aFrame.getContentPane().add( vPanel );
        this.aFrame.pack();
        this.aFrame.setLocationRelativeTo( null );
    } // createGUI()

    /**
     * Allows external code (e.g., Game) to provide a custom progress callback.
     * The callback will receive the same parameters as the original onProgress.
     */
    public void setProgressListener( AssetManager.ProgressListener pl )
    {
        this.aAssetManager.setProgressListener( pl );
    }

    /**
     * Shows the loading screen and starts the asset download on a background thread.
     * This method blocks until the download finishes (success or offline fallback).
     */
    public void startAndWait()
    {
        this.aFrame.setVisible( true );
        Thread vDownloadThread = new Thread( () -> aAssetManager.downloadAssets() );
        vDownloadThread.start();
        try {
            vDownloadThread.join();
        }
        catch ( InterruptedException vE ) {
            System.out.println( "Download interrupted: " + vE.getMessage() );
        }
        this.aFrame.setVisible( false );
        this.aFrame.dispose();
    }


    /**
     * Updates the progress bar and status labels when a file is processed.
     *
     * @param pFileName the filename just processed
     * @param pCurrent  1-indexed count of files processed so far
     * @param pTotal    total number of files
     */
    @Override public void onProgress( final String pFileName, final int pCurrent, final int pTotal )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override public void run()
            {
                int vPercent = (int) ( ( (double) pCurrent / pTotal ) * 100 );
                aProgressBar.setValue( vPercent );
                aStatusLabel.setText( "Downloading: " + pFileName );
                aCountLabel.setText( pCurrent + " / " + pTotal );
            }
        } );
    } // onProgress()

    /**
     * Called when all downloads complete successfully. Marks the screen as finished.
     */
    @Override public void onComplete()
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override public void run()
            {
                aProgressBar.setValue( 100 );
                aStatusLabel.setText( "All assets downloaded!" );
            }
        } );
        this.aFinished = true;
    } // onComplete()

    /**
     * Called when some downloads failed. Shows the error then proceeds in offline mode.
     *
     * @param pMessage error description
     */
    @Override public void onError( final String pMessage )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override public void run()
            {
                aStatusLabel.setText( pMessage );
                aCountLabel.setText( "Starting in offline mode..." );
            }
        } );

        // brief pause so the user can read the message
        try {
            Thread.sleep( 2500 );
        }
        catch ( InterruptedException vE ) { /* nothing to do */ }

        this.aFinished = true;
    } // onError()
} // LoadingScreen