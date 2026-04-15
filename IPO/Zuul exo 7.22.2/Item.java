/**
 * Décrivez votre classe Items ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Item
{
    private String aDescription;
    private int aPrice;
    
    public Item (final String pDescription, final int pPrice)
    {
        this.aDescription = pDescription;
        this.aPrice = pPrice;
    }
    
    public int getItemPrice()
    {
        return this.aPrice;
    } // getItemPrice()
    
    public String getItemDescription()
    {
        return "There is " + this.aDescription + '\n' + "Item price : " + this.aPrice;
    }
}