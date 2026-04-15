package tp;


/**
 * Décrivez votre classe PointColore ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class PointColore extends Point
{
    private String aCouleur;
    
    public PointColore (int pX,int pY,String pCouleur)
    {
        super(pX, pY); 
        this.aCouleur = pCouleur;
    }
}