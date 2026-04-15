// import static org.junit.jupiter.api.Assertions.*; // pour les asserts JUnit
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class CompteMensuelTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CompteMensuelTest
{
    private static String                   sClassName;
    private static String                   sPkg;
    private static String                   sFil;
    private static veref.ClassContent       sCla;
    private static boolean                  sAbstract;
    private static String                   sAttName;
    private static String                   sAttType;
    private static veref.FieldContent       sAtt;
    private static String                   sProtoC;
    private static veref.ConstructorContent sCon;
    private static String                   sMetName;
    private static String                   sMetRT;
    private static String                   sProtoM;
    private static veref.MethodContent      sMet;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        sPkg       = "";
        sClassName = "CompteMensuel";
        sFil = sClassName + ".java";

        if (sPkg != "") {
            veref.ClassContent.setRefPkg(sPkg);
            sFil = sPkg + "/" + sFil;
        }
        
        sProtoC = "( double p1, double p2 )";
    }
    
    @Test
    public void testClasse_1()
    {
        sCla = veref.V.getVClaFName(sClassName);
        sAbstract = sCla.classType().equals("abstract class");
        veref.V.vrai(
            sAbstract,
            "Vous ne voulez pas avoir le droit de creer des objets de la classe " + sClassName + " ???"
        );
        veref.V.failIf();
    } // testClasse_1()
    
    @Test
    public void testConstructeur_3()
    {
        testClasse_1();
        sCon = veref.V.getVConFProto(sCla, sProtoC);
        veref.V.vrai(
            veref.V.nbCon(sCla) <= 1,
            "Il y a au moins un constructeur de trop ..."
        );
        veref.V.mesIfNot();
    } // testConstructeur_3()
    
    @Test
    public void testCapitalise_4()
    {
        testConstructeur_3();

        veref.V.verifMetThis(sFil, "void", "capitaliseUnMois", true); // this.capitaliseUnMois(
        veref.V.verifCount(sFil, "this.capitaliseUnMois\\(", 2);
        veref.V.verifCount(sFil, "for\\s*\\(", -1);
        veref.V.verifCount(sFil, "while\\s*\\(", -1);
    } // testCapitalise_4()

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
