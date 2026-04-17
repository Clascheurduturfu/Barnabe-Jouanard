import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Downloads game assets from the ESIEE web server and caches them locally.
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
    /** Base URL of the remote asset server. */
    private static final String BASE_URL = "https://perso.esiee.fr/~jouanarb/assets/";

    /** Local directory where downloaded assets are cached. */
    private static final String CACHE_DIR = "assets";

    /** Maximum number of download attempts per file before giving up. */
    private static final int MAX_RETRIES = 3;

    /** Size of the byte buffer used when reading download streams. */
    private static final int BUFFER_SIZE = 8192;

    /** HTTP connection timeout in milliseconds. */
    private static final int CONNECT_TIMEOUT = 10000;

    /** HTTP read timeout in milliseconds. */
    private static final int READ_TIMEOUT = 15000;

    /** Hardcoded list of all asset filenames to download. */
    private static final String[] ASSETS = {
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

    /**
     * Callback interface for reporting download progress to the UI.
     */
    @FunctionalInterface
    public interface ProgressListener {
        /**
         * Called after each file is processed (downloaded or skipped).
         *
         * @param pFileName the name of the file just processed
         * @param pCurrent  the number of files processed so far (1-indexed)
         * @param pTotal    the total number of files to process
         */
        void onProgress( String pFileName, int pCurrent, int pTotal );

        /**
         * Called when all downloads complete successfully.
         */
        default void onComplete() {}

        /**
         * Called when one or more downloads failed after all retries.
         * The game will continue in offline mode.
         *
         * @param pMessage a human-readable error summary
         */
        default void onError( String pMessage ) {}
    }

    /** Optional listener for progress updates. */
    private ProgressListener aListener;

    /**
     * Creates a new asset manager.
     */
    public AssetManager()
    {
        // ensure the cache directory exists
        File vCacheDir = new File( CACHE_DIR );
        if ( !vCacheDir.exists() ) {
            vCacheDir.mkdirs();
        }
    } // AssetManager()

    /**
     * Attaches a progress listener for download feedback.
     *
     * @param pListener the listener to notify (may be {@code null})
     */
    public void setProgressListener( final ProgressListener pListener )
    {
        this.aListener = pListener;
    } // setProgressListener()

    /**
     * Checks whether all assets are already present in the local cache.
     *
     * @return {@code true} if every file in {@link #ASSETS} exists locally
     */
    public boolean allAssetsCached()
    {
        for ( String vFileName : ASSETS ) {
            File vFile = new File( CACHE_DIR + File.separator + vFileName );
            if ( !vFile.exists() ) {
                return false;
            }
        }
        return true;
    } // allAssetsCached()

    /**
     * Returns the total number of assets that need to be downloaded.
     *
     * @return count of files not yet present in the local cache
     */
    public int countMissingAssets()
    {
        int vCount = 0;
        for ( String vFileName : ASSETS ) {
            File vFile = new File( CACHE_DIR + File.separator + vFileName );
            if ( !vFile.exists() ) {
                vCount++;
            }
        }
        return vCount;
    } // countMissingAssets()

    /**
     * Downloads all missing assets from the remote server.
     * <p>
     * Each file is attempted up to {@value #MAX_RETRIES} times. If a file
     * still fails after all retries, it is skipped and the game will run
     * in offline mode (missing images appear as blank panels).
     * </p>
     */
    public void downloadAssets()
    {
        int vTotal = ASSETS.length;
        int vFailCount = 0;

        for ( int vI = 0; vI < vTotal; vI++ ) {
            String vFileName = ASSETS[vI];
            File vLocalFile = new File( CACHE_DIR + File.separator + vFileName );

            if ( vLocalFile.exists() ) {
                // already cached, skip
                if ( this.aListener != null ) {
                    this.aListener.onProgress( vFileName, vI + 1, vTotal );
                }
                continue;
            }

            boolean vSuccess = false;
            for ( int vAttempt = 1; vAttempt <= MAX_RETRIES; vAttempt++ ) {
                vSuccess = this.downloadFile( vFileName, vLocalFile );
                if ( vSuccess ) {
                    break;
                }
                System.out.println( "Retry " + vAttempt + "/" + MAX_RETRIES + " for: " + vFileName );
            }

            if ( !vSuccess ) {
                vFailCount++;
                System.out.println( "Failed to download after " + MAX_RETRIES + " attempts: " + vFileName + " try to restart the game with wifi or consider downloading the offline version from the website");
            }

            if ( this.aListener != null ) {
                this.aListener.onProgress( vFileName, vI + 1, vTotal );
            }
        }

        if ( this.aListener != null ) {
            if ( vFailCount > 0 ) {
                this.aListener.onError( vFailCount + " file(s) could not be downloaded. Running in offline mode." );
            }
            else {
                this.aListener.onComplete();
            }
        }
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
            URL vUrl = new URL( BASE_URL + vEncodedName );

            vConnection = (HttpURLConnection) vUrl.openConnection();
            vConnection.setConnectTimeout( CONNECT_TIMEOUT );
            vConnection.setReadTimeout( READ_TIMEOUT );
            vConnection.setRequestMethod( "GET" );

            int vResponseCode = vConnection.getResponseCode();
            if ( vResponseCode != HttpURLConnection.HTTP_OK ) {
                System.out.println( "HTTP " + vResponseCode + " for: " + pFileName );
                return false;
            }

            // Use try‑with‑resources and transferTo (Java 9+)
            try ( InputStream vInput = vConnection.getInputStream();
                  FileOutputStream vOutput = new FileOutputStream( pLocalFile ) ) {
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

    /**
     * Resolves a relative asset path to an absolute {@link File} on disk.
     * <p>
     * This is the single point of access for all image loading in the game.
     * Both {@code UserInterface.showImage()} and {@code UserInterface.showMap()}
     * call this method to locate asset files.
     * </p>
     *
     * @param pRelativePath the path as stored in {@link Room}, e.g. {@code "assets/home.gif"}
     * @return a {@link File} pointing to the local cached asset
     */
    public static File resolveAsset( final String pRelativePath )
    {
        return new File( pRelativePath );
    } // resolveAsset()
} // AssetManager