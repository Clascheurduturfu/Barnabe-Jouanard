/**
 * D�crivez votre classe CompteAnnuel ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public class CompteAnnuel extends CompteRemunere
{
    public CompteAnnuel(final double pSolde,final double pTaux)
    {
        super(pSolde,pTaux);
    }
    
    @Override
    public void capitaliseUnAn()
    {
        credite(getSolde()*(1+getTaux()/100));
    }
}
