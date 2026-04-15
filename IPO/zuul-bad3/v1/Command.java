package v1;
/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 * Une commande est composée d'un mot de commande et d'un second mot (optionnel).
 *
 * @author Barnabe Jouanard
 * @version 2026.02.17
 */
public class Command
{ 
    private String aCommandWord;
    private String aSecondWord;
    
    /**
     * Crée un objet commande.
     * @param pCommandWord Le premier mot de la commande (null si inconnu).
     * @param pSecondWord Le second mot de la commande.
     */
    public Command (final String pCommandWord,final String pSecondWord)
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    } // Command()
    
    /**
     * Renvoie le premier mot de la commande.
     * @return Le mot de commande.
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    } // getCommandWord()
    
    /**
     * Renvoie le second mot de la commande.
     * @return Le second mot, ou null s'il n'y en a pas.
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    } // getSecondWord()
    
    /**
     * Vérifie si la commande possède un second mot.
     * @return true si la commande a un second mot.
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord!=null;
    } // hasSecondWord()
    
    /**
     * Vérifie si la commande est inconnue.
     * @return true si le premier mot est null.
     */
    public boolean isUnknown()
    {
        return this.aCommandWord==null;
    } // isUnknown()
} // Command
