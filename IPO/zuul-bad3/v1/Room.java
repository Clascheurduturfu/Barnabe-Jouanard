package v1;
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
    public Room aNorthExit;
    public Room aWestExit;
    public Room aEastExit;
    public Room aSouthExit;
    public Room aCurrentRoom;
    /**
     * Crée une pièce décrite par "pDescription". 
     * Au départ, il n'y a aucune sortie.
     * @param pDescription La description de la pièce.
     */
    public Room (final String pDescription)
    {
        this.aDescription = pDescription;
    } // Room()
    
    /**
     * Renvoie la description de la pièce.
     * @return La description de la pièce.
     */
    public String getDescription()
    {
        return this.aDescription;
    } // getDescription()
    
    public Room getExit(final String pDirection){
        if (pDirection.equals("North"))
        {
            return aNorthExit;
        }
        if (pDirection.equals("South"))
        {
            return aSouthExit;
        }
        if (pDirection.equals("East"))
        {
            return aEastExit;
        }
        if (pDirection.equals("West"))
        {
            return aWestExit;
        }
        return null;
    }

    public String getExitString()
    {
        String vReturnString = "Exits:";
        
        if (this.getExit("North") != null) {
        vReturnString += " North";
        }
        
        if (this.getExit("East") != null) {
        vReturnString += " East";
        }
        
        if (this.getExit("South") != null) {
        vReturnString += " South";
        }
        
        if (this.getExit("West") != null) {
        vReturnString += " West";
        }
        
        return vReturnString;
    } // getExitString()

    private void printLocationInfo()
    {
        System.out.println("You are " + this.aCurrentRoom.getDescription());
        System.out.print(this.aCurrentRoom.getExitString());
        System.out.println();
    }
    
    /**
     * Définit les sorties de cette pièce.
     * Chaque direction mène soit à une autre pièce, soit est null.
     * @param pNorthExit La sortie Nord.
     * @param pEastExit La sortie Est.
     * @param pSouthExit La sortie Sud.
     * @param pWestExit La sortie Ouest.
     */
    public void setExits (final Room pNorthExit,final Room pEastExit,final Room pSouthExit,final Room pWestExit)
    {
        this.aNorthExit=pNorthExit;
        this.aWestExit=pWestExit;
        this.aEastExit=pEastExit;
        this.aSouthExit=pSouthExit;
    } // setExits()
    private void printLocationInfo()
    {
        System.out.println("You are " + this.aCurrentRoom.getDescription());
        System.out.print("Exits : ");
        if (this.aCurrentRoom.aNorthExit != null){
            System.out.print("North ");
        }
        if (this.aCurrentRoom.aSouthExit != null){
            System.out.print("South ");
        }
        if (this.aCurrentRoom.aEastExit != null){
            System.out.print("East ");
        }
        if (this.aCurrentRoom.aWestExit != null){
            System.out.print("West ");
        }
    }
} // Room
