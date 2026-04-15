
/**
 * Décrivez votre classe Compte ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public abstract class Compte
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private double aSolde;
    public Compte(final double pSolde)
    {
        aSolde = pSolde;
    }
    
    public double getSolde()
    {
        return aSolde;
    }
    
    private void affecte(final double pSolde)
    {
        aSolde = arrondi2(pSolde);
    }
    
    private static double arrondi2( final double pR )
    {
    double vR = Math.abs( pR );
    int    vI = (int)(vR * 1000);
    if ( vI%10 >= 5 )  vR = vR + 0.01;
    return Math.copySign( ((int)(vR*100))/100.0, pR );
    } // arrondi2(.)
    
    public void debite(final double pDebite)
    {
        this.affecte(aSolde-pDebite);
    }
    
    public void credite(final double pCredite)
    {
        this.affecte(aSolde+pCredite);
    }
    
    public abstract void capitaliseUnAn();
    
    public void bilanAnnuel()
    {
        System.out.println("solde="+aSolde);
        capitaliseUnAn()    ;
        System.out.println(" / après capitalisation, solde= " + aSolde);
    }
}