
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Crypto
{
    //===========================================================
    //                METHODES UTILES
    //===========================================================

    /**
     * Convertir une chaine de caracteres en tableau de bytes
     */
    public static byte[] strToByte( final String pMsg )
    {
        return pMsg.getBytes();
    } // strToByte

    /**
     * Convertir un tableau de bytes en une chaine de caracteres
     */
    public static String byteToStr( final byte[] pByteArray )
    {
        return new String(pByteArray);
    } // byteToStr()

    /**
     * Ecrire un texte dans un fichier en conservant son contenu
     * si "pAjout=true", ou en l'ecrasant si "pAjout=false"
     */
    public static void writeFile( final String pContent, final String pFile, final boolean pAjout )
    {
        try {
            FileWriter vWriter = new FileWriter(pFile, pAjout);
            vWriter.write(pContent);
            vWriter.close();
        }
        catch( final IOException pE ) {
            pE.printStackTrace();
        }
    } // writeFile()

    /**
     * Lire le contenu d'un fichier de nom (pFile)
     * et le retourner dans une String
     */
    public static String readFile( final String pFile )
    {
        String vContent = "";
        try {
            vContent = new String( Files.readAllBytes( Paths.get(pFile) ) );
        }
        catch( final IOException pE ) {
            pE.printStackTrace();
        }
        return vContent;
    } // readFile()
    
    //===========================================================
    //                FIN METHODES UTILES
    //===========================================================

    /**
     * Calculer le nombre d'occurence de chaque lettre dans un fichier
     * texte
     */
    
    public static int[] frequences( final String pFile )
    {
        String vTxt = readFile(pFile);
        vTxt = vTxt.toUpperCase();

        /* Tableau de frequences (vOcc[k]:Nbr d'occurrences de la k-ieme
           lettre de l'alphabet dans la chaine vTxt) */
        int[] vOcc= new int[26];  
        //========== TODO Question-1
            for (int i = 0; i < vTxt.length(); i++) { 
                char c = vTxt.charAt(i); 
                if (c >= 'A' && c <= 'Z') { 
                    vOcc[c - 'A']++; 

                }
            }
            //========== Fin TODO Question-1
            System.out.println("Frequences des lettres dans le fichier " + pFile +":"); 
            for (int i = 0; i < vOcc.length; i++){ // On parcourt le tableau
                System.out.print((char) ('A' + i) + " : " + vOcc[i] + ", ");
            }
        return vOcc;
        }// frequences()

      
          /**
         * Déchiffrer le contenu d'un fichier en appliquant une substitution
         * mono-alphabétique basée sur une table de correspondance de lettres 
         */
    
    public static String subDecrypt( final String pFile, final char[] pKey )
        {                                                   
        String vChiffre = readFile(pFile);
        vChiffre = vChiffre.toUpperCase();
        String vClair = "";
        //=========== TODO Question-3
        for (int i = 0; i < vChiffre.length(); i++){ 
            char c = vChiffre.charAt(i); 
            if (c >= 'A' && c <= 'Z'){ 
                vClair += pKey[c - 'A']; 
            } 
            else{ 
                vClair += c; 
            }
        }
        //=========== Fin TODO Question-3
        return vClair;
    } // subDecrypt()

      /**
     * Chiffrer/Déchiffrer le contenu d'un fichier en opérant un XOR
     *  entre le clair et la clef 
     */
   
    public static void cryptXor( final String pInfile, final String pOutfile, final String pKey )
    {                                                          
        //=========== TODO Question-5
        byte[] vKey = strToByte(pKey); 
        String vCont = readFile(pInfile); 
        byte[] vFileCont = strToByte(vCont); 
        byte[] vFileOut = new byte[vFileCont.length]; 
        String vRes = ""; 
        for(int i = 0; i < vFileCont.length; i++) {
            vFileOut[i] = (byte) (vFileCont[i] ^ vKey[i % 4]); 
        }
        vRes += byteToStr(vFileOut); 
        writeFile(vRes, pOutfile, false);
        //=========== Fin TODO Question-5
    } // cryptXor()

    
    /*=============== Tests et invocations de méthodes ============================*/
    
    public static void main( final String[] pArgs )
    {                                
        
        
        //TODO Question-3
        Crypto vCryp = new Crypto();
        char[] vTabChar = {'V', 'K', 'Z', 'M', 'H', 'N', 'O', 'P', 'C', 'Q','R', 'S', 'T', 'Y', 'I', 'J', 'X', 'D', 'L', 'E', 'G', 'W', 'U', 'A', 'B','F'};
        System.out.println(vCryp.subDecrypt("out.TXT", vTabChar)); 
    
        //Fin TODO Question-3
        
        /*=============================================================*/
        
        //TODO Question-4
        char[] vKeyDec6 = {'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E','F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'};
        System.out.println(subDecrypt("out.txt", vKeyDec6));
        //Fin TODO Question-4
        
        /*=============================================================*/
        
        //TODO Question-6
        cryptXor("IN","OUT","ABCD");
        //Fin TODO Question-6
        
        /*=============================================================*/

        //TODO Question-8
        cryptXor("OUT","OUT2","ABCD");
        //Fin TODO Question-8
        
    } // main()
} // Crypto