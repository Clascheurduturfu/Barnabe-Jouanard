/**
 * Cette classe represente un simple dessin. Vous pouvez l'afficher en appelant
 * la methode dessine(). Mais il y a mieux : comme c'est un dessin electronique,
 * vous pouvez facilement le modifier. Par exemple, le passer en noir et blanc,
 * puis le remettre en couleurs (seulement apres l'avoir dessine, bien sur).
 *
 * (ecrite pour servir d'exemple au debut de l'apprentissage de Java avec BlueJ)
 * 
 * @author  Michael Kolling and David J. Barnes
 * @author  mod.by Denis BUREAU
 * @version 2006.03.30/2012.02.06
 */
public class Maison
{
    private Carre    aMur;
    private Carre    aFenetre;
    private Triangle aToit;
    private Cercle   aSoleil1;
    private Cercle   aSoleil2;
    private boolean  aDejaPlace;

    /**
     * Constructeur par defaut
     */
    public Maison()
    {
        this.aDejaPlace= false; // place() is needed
        
        this.aMur= new Carre();
        this.aMur.changeTaille( 100 );
        
        this.aFenetre= new Carre();
        this.aFenetre.changeCouleur( "black" );

        this.aToit= new Triangle();  
        this.aToit.changeTaille( 140, 50 );

        this.aSoleil1= new Cercle();
        this.aSoleil1.changeCouleur( "yellow" );
        this.aSoleil1.changeTaille( 60 );
        this.metNoirEtBlanc();
        this.aSoleil2= new Cercle();
        this.aSoleil2.changeCouleur( "blue" );
        
        this.aSoleil2.changeTaille( 60 );
        this.metNoirEtBlanc();
        this.dessine();
        this.place();
    } // Picture()
    
    private String getPositionSoleil (Cercle pSoleil)
    {
        int pos = pSoleil.getPosition();
        int x = pos/1000;
        int y = pos %1000;
        
        return "x="+x+"; y"+y;
    
    }
    
    public String getPositionDeuxSoleils ()
    {
        return this.getPositionSoleil(this.aSoleil1) + " | " + this.getPositionSoleil(this.aSoleil2) ;
    
    }    
    

    /**
     * Dessine la maison.
     */
    public void dessine()
    {
        this.aMur.rendVisible();
        this.aFenetre.rendVisible();
        this.aToit.rendVisible();
        this.aSoleil1.rendVisible();
    } // dessine()

    /**
     * Deplace lentement les elements a leur place.
     */
    public void place()
    {
        if ( ! aDejaPlace ) {
            this.aMur.depLentVertical(80);
            this.aFenetre.depLentHorizontal(20);
            this.aFenetre.depLentVertical(100);
            this.aToit.depLentHorizontal(60);
            this.aToit.depLentVertical(70);
            this.aSoleil1.depLentHorizontal(180);
            this.aSoleil1.depLentVertical(-10);
            this.aSoleil2.depLentVertical(-10);
            this.aDejaPlace= true;
        }
    } // place()

    /**
     * Efface la maison.
     */
    public void effaceMaison()
    {
        this.aMur.rendInvisible();
        this.aFenetre.rendInvisible();
        this.aToit.rendInvisible();
    } // effaceMaison()

    /**
     * Passe le dessin en Noir et Blanc
     */
    public void metNoirEtBlanc()
    {
        if (this.aMur != null) { // only if it's painted already...
            this.aMur.changeCouleur(  "black");
            this.aFenetre.changeCouleur("white");
            this.aToit.changeCouleur(  "black");
            this.aSoleil1.changeCouleur(   "black");
            
        } // if
        else {}
    } // metNoirEtBlanc()

    /**
     * Remet le dessin en couleurs
     */
    public void metCouleurs()
    {
        if (this.aMur != null) { // only if it's painted already...
            this.aMur.changeCouleur(  "red"   );
            this.aFenetre.changeCouleur("black" );
            this.aToit.changeCouleur(  "green" );
            this.aSoleil1.changeCouleur(   "yellow");
            this.aSoleil2.changeCouleur(   "red");
        } // if
        else {}
    } // metCouleurs()
    
} // Maison
