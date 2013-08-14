package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.model.ActedOnBehalfOf;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.model.Organization;
import uk.ac.kcl.inf.provoking.model.WasAssociatedWith;
import uk.ac.kcl.inf.provoking.model.WasAttributedTo;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;

public class EquivalenceTest {
    @BeforeClass
    public static void setUpClass () {
    }
    
    @AfterClass
    public static void tearDownClass () {
    }
    
    @Before
    public void setUp () {
    }
    
    @After
    public void tearDown () {
    }

    @Test
    public void equivalenceTest1 () throws URISyntaxException {
        Entity entity = new Entity (new URI ("http://www.example.com/entity1"));
        Organization agent1 = new Organization (new URI ("http://www.example.com/agent1"));
        WasAttributedTo wat = new WasAttributedTo (entity, agent1);
        Document document1 = new Document (), document2;
        JenaDeserialiser in = new JenaDeserialiser ();
        String original;

        document1.addAll (entity, agent1, wat);
        
    }
}
