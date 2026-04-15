import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class InventaireTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class InventaireHTest
{
    /**
     * Default constructor for test class InventaireTest
     */
    public InventaireHTest() { }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() { }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() { }

    @Test
    public void test_new()
    {
        InventaireH vInv1 = new InventaireH();
        // assertEquals("[] : 0", vInv1.toString());
    }

    @Test
    public void test_ajoute()
    {
        InventaireH vInv1 = new InventaireH();
        // vInv1.ajouteItem("clef", 42);
        // assertEquals("[clef(42)] : 42", vInv1.toString());
        // vInv1.ajouteItem("deux", 20);
        // assertTrue("Le prix total est mal calcule !?", vInv1.toString().endsWith(" : 62"));
    }

    @Test
    public void test_contient()
    {
        InventaireH vInv1 = new InventaireH();
        // vInv1.ajouteItem("clef", 42);
        // assertEquals(true, vInv1.contientItem("clef"));
        // assertEquals(false, vInv1.contientItem("cle"));
    }

    @Test
    public void test_get()
    {
        // InventaireH vInv1 = new InventaireH();
        // vInv1.ajouteItem("clef", 42);
        // Item vItem1 = vInv1.getItem("clef");
        // assertEquals("clef", vItem1.getNom());
        // assertEquals(42, vItem1.getPrix());
    }

    @Test
    public void test_enleve()
    {
        // InventaireH vInv1 = new InventaireH();
        // vInv1.ajouteItem("clef", 42);
        // vInv1.enleveItem("clef");
        // assertEquals("n'a pas enleve la clef !?", false, vInv1.contientItem("clef"));
        // vInv1.ajouteItem("un", 10);
        // vInv1.ajouteItem("deux", 20);
        // vInv1.ajouteItem("trois", 30);
        // vInv1.ajouteItem("quatre", 40);
        // vInv1.ajouteItem("cinq", 50);
        // vInv1.enleveItem("deux");
        // assertTrue("n'a pas enleve le deux !?", vInv1.toString().endsWith(" : 130"));
        // vInv1.enleveItem("deux");
        // assertTrue("peut enlever un element deja supprime !?", vInv1.toString().endsWith(" : 130"));
    }
}
