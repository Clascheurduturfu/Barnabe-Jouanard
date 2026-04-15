/**
 * D�crivez votre classe CompteAnnuel ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public class CompteMensuel extends CompteRemunere
{
    public CompteMensuel(final double pSolde,final double pTaux)
    {
        super(pSolde,pTaux);
    }
    
    private void capitaleUnMois(final double pMois)
    {
        if (pMois>0)
        {
            credite(getSolde()*(getTaux()/100));
            this.capitaleUnMois(pMois-1);
        }
    }
    
    @Override
    public void capitaliseUnAn()
    {
        this.capitaleUnMois(12);
    }
}
