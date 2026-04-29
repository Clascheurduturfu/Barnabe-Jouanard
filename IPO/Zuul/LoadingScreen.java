import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * A splash screen displayed while the {@link AssetManager} downloads missing
 * game assets from the remote server.
 * <p>
 * Shows a progress bar with a status label indicating which file is being
 * downloaded and how many remain. When all downloads complete (or fail after
 * retries), the screen closes automatically and the game starts.
 * </p>
 *
 * @author Barnabe Jouanard
 * @version 2026.04.17
 */
public class LoadingScreen {
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

    /**
     * Creates a new loading screen linked to the given asset manager.
     *
     * @param pAssetManager the asset manager that will perform the downloads
     */
    public LoadingScreen(final AssetManager pAssetManager) {
        this.aAssetManager = pAssetManager;
        this.createGUI();
    } // LoadingScreen()

    /**
     * Builds the loading screen UI: title, progress bar, and status labels.
     */
    private void createGUI() {
        this.aFrame = new JFrame("Pokémon Delta Emerald — Loading");
        this.aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.aFrame.setResizable(false);

        JPanel vPanel = new JPanel();
        vPanel.setLayout(new BorderLayout(10, 10));
        vPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        vPanel.setBackground(new Color(30, 30, 40));

        // Title
        JLabel vTitle = new JLabel("Pokémon Delta Emerald", JLabel.CENTER);
        vTitle.setFont(new Font("SansSerif", Font.BOLD, 50));
        vTitle.setForeground(new Color(255, 215, 0));

        // Progress bar
        this.aProgressBar = new JProgressBar(0, 100);
        this.aProgressBar.setStringPainted(true);
        this.aProgressBar.setPreferredSize(new Dimension(400, 30));
        this.aProgressBar.setForeground(new Color(76, 175, 80));
        this.aProgressBar.setBackground(new Color(50, 50, 60));

        // Status label
        this.aStatusLabel = new JLabel("Checking assets...", JLabel.CENTER);
        this.aStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        this.aStatusLabel.setForeground(new Color(200, 200, 210));

        // Count label
        this.aCountLabel = new JLabel(" ", JLabel.CENTER);
        this.aCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        this.aCountLabel.setForeground(new Color(160, 160, 170));

        // Layout: south panel for labels
        JPanel vSouthPanel = new JPanel();
        vSouthPanel.setLayout(new BorderLayout(5, 5));
        vSouthPanel.setOpaque(false);
        vSouthPanel.add(this.aStatusLabel, BorderLayout.NORTH);
        vSouthPanel.add(this.aCountLabel, BorderLayout.SOUTH);

        vPanel.add(vTitle, BorderLayout.NORTH);
        vPanel.add(this.aProgressBar, BorderLayout.CENTER);
        vPanel.add(vSouthPanel, BorderLayout.SOUTH);

        this.aFrame.getContentPane().add(vPanel);
        this.aFrame.pack();
        this.aFrame.setLocationRelativeTo(null);
    } // createGUI()

    public void setProgressBar( )
    {
        int vProgress = (int) (((double) aAssetManager.getCurrentProgress() / aAssetManager.getTotalAssets()) * 100);
        this.aProgressBar.setValue(vProgress);
    }

    /**
     * Shows the loading screen.
     * This method blocks until the download finishes (success or offline fallback).
     */
    public void startAndWait() {
        this.aFrame.setVisible(true);
        aAssetManager.downloadAssets();
        while (aAssetManager.isComplete() == false) {
            int vProgress = (int) (((double) aAssetManager.getCurrentProgress() / aAssetManager.getTotalAssets()) * 100);
            this.aProgressBar.setValue(vProgress);
        }
        
        if (aAssetManager.getErrorMessage() != null) {
            aStatusLabel.setText(aAssetManager.getErrorMessage());
            aCountLabel.setText("Starting in offline mode...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignore) {
            }
            this.aFrame.setVisible(false);
            this.aFrame.dispose();
            return;
        }
        this.aProgressBar.setValue(100);
        aStatusLabel.setText("All assets downloaded! Starting game...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {
        }
        this.aFrame.setVisible(false);
        this.aFrame.dispose();
    }
} // LoadingScreen