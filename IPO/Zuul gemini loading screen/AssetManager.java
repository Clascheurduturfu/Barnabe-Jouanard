import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Downloads game assets from the ESIEE web server and stores them locally.
 * <p>
 * On each launch, checks which assets are missing from the local {@code assets/}
 * folder and downloads them from the remote server. Files already present are
 * skipped. Each download is attempted up to {@value #MAX_RETRIES} times before
 * being marked as failed. If any downloads fail, the game continues in offline
 * mode with whatever assets are available.
 * </p>
 *
 * @author Barnabe Jouanard
 * @version 2026.04.17
 */
public class AssetManager
{
    /** URL of the asset location. */
    private static final String aAssetsUrl = "https://perso.esiee.fr/~jouanarb/assets/";
    /** Maximum number of download attempts per file before giving up. */
    private static final int aMaxRetries = 3;
    /** Local directory where assets are stored. */
    private static final String aAssetsFolder = "assets";
    /** Current filename being downloaded. */
    private String aCurrentFile = "";
    /** Current progress count. */
    private int aCurrentProgress = 0;
    /** Flag indicating whether the download phase is complete. */
    private boolean aIsComplete = false;
    /** Error message if any downloads failed (null if all succeeded). */
    private String aErrorMessage = null;
    /** List of all asset filenames to download. */
    private String[] aAessetsList = {
        "home.gif",
        "home_map.jpeg",
        "littleroot town.gif",
        "littleroot_map.jpeg",
        "no map.jpeg",
        "oldale town.gif",
        "oldale town_map.jpeg",
        "petalburg city.gif",
        "petalburg city_map.jpeg",
        "petalburg woods.gif",
        "petalburg woods_map.jpeg",
        "route 101.gif",
        "route101_map.jpeg",
        "route 102.gif",
        "route102_map.jpeg",
        "rustboro city.gif",
        "rustboro city_map.jpeg",
        "sky pilar_map.jpeg",
        "sky pillar.gif"
    };
    /** Total number of assets to download. */
    private int aTotalAssets = aAessetsList.length;

    /**
     * Creates a new asset manager.
     */
    public AssetManager()
    {
        // ensure the assets directory exists
        File vAssetsFolder = new File( aAssetsFolder );
        if ( !vAssetsFolder.exists() ) {
            vAssetsFolder.mkdirs();
        }
    } // AssetManager()

    /**
     * Checks whether all assets are already present in the local folder.
     *
     * @return {@code true} if every file in {@link #ASSETS} exists locally
     */
    public boolean allAssetsDownloaded()
    {
        for ( String vFileName : aAessetsList ) {
            File vFile = new File( aAssetsFolder + File.separator + vFileName );
            if ( !vFile.exists() ) {
                return false;
            }
        }
        return true;
    } // allAssetsDownloaded()

    /**
     * Returns the total number of assets that need to be downloaded.
     *
     * @return count of files not yet present in the local folder
     */
    public int countMissingAssets()
    {
        int vCount = 0;
        for ( String vFileName : aAessetsList ) {
            File vFile = new File( aAssetsFolder + File.separator + vFileName );
            if ( !vFile.exists() ) {
                vCount++;
            }
        }
        return vCount;
    } // countMissingAssets()

    /**
     * Downloads all missing assets from the assets url.
     * <p>
     * Each file is attempted up to {@value #MAX_RETRIES} times. If a file
     * still fails after all retries, it is skipped and the game will run
     * in offline mode (missing images appear as blank panels).
     * </p>
     */
    public void downloadAssets()
    {
        int vFailCount = 0;

        for ( int vI = 0; vI < aTotalAssets; vI++ ) {
            String vFileName = aAessetsList[vI];
            File vLocalFile = new File( aAssetsFolder + File.separator + vFileName );

            if ( vLocalFile.exists() ) {
                // already stored locally, skip
                this.aCurrentFile = vFileName;
                this.aCurrentProgress = vI + 1;
                continue;
            }

            boolean vSuccess = false;
            for ( int vAttempt = 1; vAttempt <= aMaxRetries; vAttempt++ ) {
                vSuccess = this.downloadFile( vFileName, vLocalFile );
                if ( vSuccess ) {
                    break;
                }
            }

            if ( !vSuccess ) {
                vFailCount++;
                System.out.println( "Failed to download  " + vFileName + " after " +  aMaxRetries + " attempts." );
            }

            this.aCurrentFile = vFileName;
            this.aCurrentProgress = vI + 1;
        }

        if ( vFailCount > 0 ) {
            this.aErrorMessage = vFailCount + " file(s) could not be downloaded. Running in offline mode." + "\n" + "Try to restart the game with wifi or consider downloading the offline version from the website";
        }
        this.aIsComplete = true;
    } // downloadAssets()

    /**
     * Downloads a single file from the remote server to the local path.
     *
     * @param pFileName  the filename on the server (e.g. {@code "home.gif"})
     * @param pLocalFile the local destination file
     * @return {@code true} if the download succeeded; {@code false} otherwise
     */
    private boolean downloadFile( final String pFileName, final File pLocalFile )
    {
        HttpURLConnection vConnection = null;
        try {
            // URL‑encode the filename (handles spaces → %20)
            String vEncodedName = URLEncoder.encode( pFileName, "UTF-8" ).replace( "+", "%20" );
            URL vUrl = new URL( aAssetsUrl + vEncodedName );

            vConnection = (HttpURLConnection) vUrl.openConnection();
            vConnection.setConnectTimeout( 10000 );
            vConnection.setReadTimeout( 10000 );
            vConnection.setRequestMethod( "GET" );

            int vResponseCode = vConnection.getResponseCode();
            if ( vResponseCode != HttpURLConnection.HTTP_OK ) {
                System.out.println( "HTTP " + vResponseCode + " for: " + pFileName );
                return false;
            }

            try ( InputStream vInput = vConnection.getInputStream(); FileOutputStream vOutput = new FileOutputStream( pLocalFile ) ) {
                vInput.transferTo( vOutput );
            }

            return true;
        }
        catch ( IOException vException ) {
            System.out.println( "Download error for " + pFileName + ": " + vException.getMessage() );
            // clean up partial file
            if ( pLocalFile.exists() ) {
                pLocalFile.delete();
            }
            return false;
        }
        finally {
            if ( vConnection != null ) {
                vConnection.disconnect();
            }
        }
    } // downloadFile()

    public String getCurrentFile()
    {
        return aCurrentFile;
    }

    public int getCurrentProgress() 
    {
        return aCurrentProgress;
    }

    public int getTotalAssets()
    {
        return aTotalAssets;
    }

    public boolean isComplete()
    {
        return aIsComplete;
    }

    public String getErrorMessage()
    {
        return aErrorMessage;
    }

} // AssetManager