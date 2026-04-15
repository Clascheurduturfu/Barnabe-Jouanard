/**
 * D�crivez votre classe CompteRemunere ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public abstract class CompteRemunere extends Compte
{
    private double aTaux;
    
    public CompteRemunere(final double pSolde,final double pTaux)
    {
        super(pSolde);
        this.aTaux = pTaux;
    }
    
    public double getTaux()
    {
        return aTaux;
    }
    
    @Override
    public void capitaliseUnAn()
    {
        
    }
}