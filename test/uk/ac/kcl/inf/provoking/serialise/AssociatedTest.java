package uk.ac.kcl.inf.provoking.serialise;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import org.junit.*;
import uk.ac.kcl.inf.provoking.builder.ProvBuilder;
import uk.ac.kcl.inf.provoking.model.ActedOnBehalfOf;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Agent;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.model.Organization;
import uk.ac.kcl.inf.provoking.model.WasAssociatedWith;
import uk.ac.kcl.inf.provoking.model.WasAttributedTo;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;
import uk.ac.kcl.inf.provoking.serialise.rdf.turtle.TurtlePrinter;

/**
 *
 * @author Simon Miles
 */
public class AssociatedTest {
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
    public void serialiseWasAssociatedWith () throws IOException, DeserialisationException, URISyntaxException {
        Entity entity = new Entity (new URI ("http://www.example.com/entity1"));
        Organization agent1 = new Organization (new URI ("http://www.example.com/agent1"));
        Organization agent2 = new Organization (new URI ("http://www.example.com/agent2"));
        ActedOnBehalfOf aobo = new ActedOnBehalfOf (agent1, agent2);
        Activity activity = new Activity (new URI ("http://www.example.com/activity1"), new Date (), new Date ());
        WasAssociatedWith waw = new WasAssociatedWith (activity, agent1);
        WasAttributedTo wat = new WasAttributedTo (entity, agent1);
        Document document = new Document ();
        JenaDeserialiser in = new JenaDeserialiser ();
        String original;
        
        document.addAll (activity, agent1, waw, agent2, wat, entity, aobo);
        original = document.toString ();
        System.out.println (original);
        
        in.readString (original, null, Language.turtle);
        document = in.build ();
        
        System.out.println (document);
    }
}
