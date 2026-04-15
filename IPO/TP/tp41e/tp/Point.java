package tp;


/**
 * Décrivez votre classe Point ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Point
{
    private int aX;
    private int aY;
    
    public Point(int pX,int pY)
    {
        this.aX = pX;
        this.aY = pY;
    }
    
    public Point()
    {
        this(10,10);
    }
    
    public void deplace(int pDeltaX,int pDeltaY)
    {
        aX += pDeltaX;
        aY += pDeltaY;
    }
    
    @Override
    public String toString()
    {
        return "(" + this.aX + "," + this.aY + ")";
    }
    
    public void affiche ()
    {
        System.out.println("<<"+this+">>");
    }
}