import java.util.HashMap;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 * Cette classe fait partie du système Zuul.
 *
 * @author Barnabe Jouanarrd
 * @version 2026.02.17
 */
public class Room
{
    private String aDescription;
    private HashMap<String, Room> aExits;
    
    /**
     * Crée une pièce décrite par "pDescription". 
     * Au départ, il n'y a aucune sortie.
     * @param pDescription La description de la pièce.
     */
    public Room (final String pDescription)
    {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
    } // Room()
    
    /**
     * Renvoie la description de la pièce.
     * @return La description de la pièce.
     */
    public String getDescription()
    {
        return this.aDescription;
    } // getDescription()
    
    /**
     * Renvoie la pièce atteinte si nous allons dans la direction "pDirection".
     * Si aucune pièce n'existe dans cette direction, renvoie null.
     * @param pDirection La direction de la sortie demandée.
     * @return La pièce voisine ou null.
     */
    public Room getExit(final String pDirection)
    {
        return this.aExits.get(pDirection);
    } // getExit()
    
    /**
     * Define the exits of this room. Every direction either
     * leads to another room or is null (no exit there).
     */
    public void setExit (final String pDirection,final Room pExit)
    {
        this.aExits.put(pDirection, pExit);
        // this.aWestExit=pWestExit;
        // this.aEastExit=pEastExit;
        // this.aSouthExit=pSouthExit;
    } // setExits()
    
    /**
     * Affiche les informations sur la pièce actuelle (description et sorties).
     */
    public void printLocationInfo()
    {
        System.out.println("You are " + this.getDescription());
        System.out.print(this.getExitString());
        // if (this.getExit("North") != null) { System.out.print("North "); }
        // if (this.getExit("East") != null) { System.out.print("East "); }
        // if (this.getExit("South") != null) { System.out.print("South "); }
        // if (this.getExit("West") != null) { System.out.print("West "); }
        System.out.println();
    } // printLocationInfo()
    
    /**
     * Return the room that is reached if we go from this
     * room in direction "direction". If there is no room in
     * that direction, return null.
     */
    public String getExitString()
    {
        String vReturnString = "Exits:";
        
        if (this.aExits.get("North") != null) {
        vReturnString += " North";
        }
        
        if (this.aExits.get("East") != null) {
        vReturnString += " East";
        }
        
        if (this.aExits.get("South") != null) {
        vReturnString += " South";
        }
        
        if (this.aExits.get("West") != null) {
        vReturnString += " West";
        }
        
        if (this.aExits.get("Down") != null) {
        vReturnString += " Down";
        }
        
        if (this.aExits.get("Up") != null) {
        vReturnString += " Up";
        }
        
        return vReturnString;
    } // getExitString()
} // Room
